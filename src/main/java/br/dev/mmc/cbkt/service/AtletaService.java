package br.dev.mmc.cbkt.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.dev.mmc.cbkt.config.exceptions.CustomBadRequestException;
import br.dev.mmc.cbkt.config.exceptions.ResourceNotFoundException;
import br.dev.mmc.cbkt.controller.forms.AtletaClubeForm;
import br.dev.mmc.cbkt.controller.forms.AtletaClubeTransferirForm;
import br.dev.mmc.cbkt.controller.forms.AtletaValidarForm;
import br.dev.mmc.cbkt.controller.responses.AtletaDTO;
import br.dev.mmc.cbkt.controller.responses.AtletaValidadoRecord;
import br.dev.mmc.cbkt.domain.Atleta;
import br.dev.mmc.cbkt.domain.AtletaClube;
import br.dev.mmc.cbkt.domain.AtletaGraduacao;
import br.dev.mmc.cbkt.domain.Clube;
import br.dev.mmc.cbkt.repository.AtletaClubeRepository;
import br.dev.mmc.cbkt.repository.AtletaGraduacaoRepository;
import br.dev.mmc.cbkt.repository.AtletaRepository;
import br.dev.mmc.cbkt.repository.ClubeRepository;
import br.dev.mmc.cbkt.util.JodaTimeUtil;
import jakarta.transaction.Transactional;

@Service
public class AtletaService extends CrudServiceImpl<Atleta, Long> {

    private final AtletaRepository atletaRepository;
    private final ClubeRepository clubeRepository;
    private final AtletaClubeRepository atletaClubeRepository;
    private final AtletaGraduacaoRepository atletaGraduacaoRepository;

    public AtletaService(AtletaRepository repo, AtletaRepository atletaRepository, ClubeRepository clubeRepository, AtletaClubeRepository atletaClubeRepository, AtletaGraduacaoRepository atletaGraduacaoRepository) {
        super(repo);
        this.atletaRepository = atletaRepository;
        this.clubeRepository = clubeRepository;
        this.atletaClubeRepository = atletaClubeRepository;
        this.atletaGraduacaoRepository = atletaGraduacaoRepository;
    }

    public Page<Atleta> getAllPage(Pageable pageable, String filtro) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 10, Sort.by("nomeAtleta").ascending());
        }
        if(filtro != null && !filtro.isBlank()) {
            return atletaRepository.findPageByFiltroNome(filtro, pageable);
        }
        return atletaRepository.findAll(pageable);
    }

    // Novo método para filtro por nome, graduação e clube
    public Page<Atleta> filterPageByNGC(Pageable pageable, String filtro) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 10, Sort.by("nomeAtleta").ascending());
        }
        return atletaRepository.findAll(AtletaRepository.filtroByNGC(filtro), pageable);
    }

    public AtletaDTO findById(Long id) {
        Atleta atleta = atletaRepository.findAtletaComGraduacoes(id)
            .orElseThrow(() -> new ResourceNotFoundException("Atleta não encontrado."));
        List<AtletaClube> clubes = atletaRepository.findAtletaComClubes(id)
            .orElseThrow(() -> new ResourceNotFoundException("Atleta não encontrado."))
            .getClubes();
        return new AtletaDTO(atleta, clubes);
    }

    public List<Atleta> findByNome(String nome) {
        return atletaRepository.findGraduacoesByFiltro(convertNome(nome),null);
    }

    public List<Atleta> findByCpf(String cpf) {
        return atletaRepository.findGraduacoesByFiltro(null, cpf);
    }

    public List<AtletaGraduacao> findGraduacoesById(Long id) {
        Atleta atleta = atletaRepository.findAtletaComGraduacoes(id)
            .orElseThrow(() -> new ResourceNotFoundException("Atleta não encontrado."));
        return atleta.getGraduacoes();
    }

    public AtletaValidadoRecord validarAtleta(AtletaValidarForm form) {
            Date dtNascimento = null;
            try {
                dtNascimento = JodaTimeUtil.parseStringDateBRtoDate(form.getDtNascimento());
            } catch (Exception e) {
                throw new CustomBadRequestException("Data de nascimento inválida. Use o formato dd/MM/yyyy.");
            }
            Atleta atleta = atletaRepository.findAtleta(dtNascimento, form.getCpf(), form.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Atleta não encontrado."));
            return new AtletaValidadoRecord(atleta.getId(), atleta.getNomeAtleta(), atleta.getContato().getEmail(), dtNascimento, atleta.getDocumentos().getCpf(), atleta.getGraduacao().getDescricaoGraduacao());
    }

    public List<AtletaClube> adicionarClube(AtletaClubeForm form) {
        // Implementação do método para adicionar clube ao atleta
        // Exemplo:
        // 1. Buscar o atleta pelo ID
        // 2. Buscar o clube pelo ID
        // 3. Adicionar o clube à lista de clubes do atleta
        // 4. Salvar o atleta atualizado no repositório
        // 5. Retornar o atleta atualizado ou algum DTO relevante

        Atleta atleta = atletaRepository.findById(form.getAtletaId())
            .orElseThrow(() -> new ResourceNotFoundException("Atleta não encontrado."));
        
        // Lógica para adicionar clube ao atleta aqui
        Clube clube = clubeRepository.findById(form.getClubeId())
            .orElseThrow(() -> new ResourceNotFoundException("Clube não encontrado."));

        AtletaClube atletaClube = new AtletaClube();
        atletaClube.setAtleta(atleta);
        atletaClube.setClube(clube);
        atletaClube.setDataAdmissao(form.getDtAdmissao());
        atletaClube.setTransferido(false);

        atleta.getClubes().add(atletaClube);
        Atleta atletaSave = atletaRepository.save(atleta);
        return atletaRepository.findAtletaComClubes(atletaSave.getId())
            .get().getClubes()
            .stream()
            .sorted((ac1, ac2) -> ac2.getDataAdmissao().compareTo(ac1.getDataAdmissao()))
            .toList();
    }

    public List<AtletaClube> removerClube(Long atletaId, Long clubeId) {
        atletaClubeRepository.deleteById(clubeId);
        return atletaRepository.findAtletaComClubes(atletaId)
            .get().getClubes()
            .stream()
            .sorted((ac1, ac2) -> ac2.getDataAdmissao().compareTo(ac1.getDataAdmissao()))
            .toList();
    }

    @Transactional(rollbackOn = Exception.class)
    public List<AtletaClube> transferirClube(AtletaClubeTransferirForm form){
        AtletaClube atletaClubeOrigem = atletaClubeRepository.findById(form.getAtletaClubeOrigemId())
            .orElseThrow(() -> new ResourceNotFoundException("Clube de origem não encontrado."));
        Clube clubeDestino = clubeRepository.findById(form.getClubeDestinoId())
            .orElseThrow(() -> new ResourceNotFoundException("Clube de destino não encontrado."));
        Atleta atleta = atletaRepository.findById(form.getAtletaId())
            .orElseThrow(() -> new ResourceNotFoundException("Atleta não encontrado."));
        
        atletaClubeOrigem.setDataSaida(form.getDtTransferencia());
        atletaClubeRepository.save(atletaClubeOrigem);

        Clube clubeOrigem = clubeRepository.findById(atletaClubeOrigem.getClube().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Clube de origem não encontrado."));

        AtletaClube destino = new AtletaClube();
        destino.setAtleta(atleta);
        destino.setClube(clubeDestino);
        destino.setClubeOrigem(clubeOrigem);
        destino.setDataAdmissao(form.getDtTransferencia());
        destino.setTransferido(true);
        atletaClubeRepository.save(destino);

        return atletaRepository.findAtletaComClubes(atleta.getId())
            .get().getClubes()
            .stream()
            .sorted((ac1, ac2) -> ac2.getDataAdmissao().compareTo(ac1.getDataAdmissao()))
            .toList();
    }
    private String convertNome(String nome) {
        return "%" + nome
            .replace("\"", "\\\"")
            .replace("%", "\\%")
            .replace("_", "\\_")
            .toUpperCase()
            + "%";
    }

}
