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
import br.dev.mmc.cbkt.controller.forms.AtletaValidarForm;
import br.dev.mmc.cbkt.controller.responses.AtletaDTO;
import br.dev.mmc.cbkt.controller.responses.AtletaValidadoRecord;
import br.dev.mmc.cbkt.domain.Atleta;
import br.dev.mmc.cbkt.domain.AtletaClube;
import br.dev.mmc.cbkt.domain.AtletaGraduacao;
import br.dev.mmc.cbkt.repository.AtletaRepository;
import br.dev.mmc.cbkt.util.JodaTimeUtil;

@Service
public class AtletaService extends CrudServiceImpl<Atleta, Long> {

    private final AtletaRepository atletaRepository;

    public AtletaService(AtletaRepository repo, AtletaRepository atletaRepository) {
        super(repo);
        this.atletaRepository = atletaRepository;
    }

    public Page<Atleta> getAllPage(Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 10, Sort.by("nomeAtleta").ascending());
        }
        return atletaRepository.findAll(pageable);
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

   private String convertNome(String nome) {
        return "%" + nome
            .replace("\\", "\\\\")
            .replace("%", "\\%")
            .replace("_", "\\_")
            .toUpperCase()
            + "%";
   }
}
