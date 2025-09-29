package br.dev.mmc.cbkt.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import br.dev.mmc.cbkt.config.exceptions.CustomBadRequestException;
import br.dev.mmc.cbkt.config.exceptions.ResourceNotFoundException;
import br.dev.mmc.cbkt.controller.forms.AtletaValidarForm;
import br.dev.mmc.cbkt.controller.responses.AtletaValidadoRecord;
import br.dev.mmc.cbkt.domain.Atleta;
import br.dev.mmc.cbkt.domain.record.AtletaGraduacoesRecord;
import br.dev.mmc.cbkt.repository.AtletaRepository;
import br.dev.mmc.cbkt.util.JodaTimeUtil;

@Service
public class AtletaService extends CrudServiceImpl<Atleta, Long> {

    private final AtletaRepository atletaRepository;

    public AtletaService(AtletaRepository repo, AtletaRepository atletaRepository) {
        super(repo);
        this.atletaRepository = atletaRepository;
    }

    public Atleta findById(Long id) {
        return atletaRepository.getAtletaById(id).orElse(null);
    }

    public List<Atleta> findByNome(String nome) {
        return atletaRepository.findGraduacoesByFiltro(convertNome(nome),null);
    }

    public List<Atleta> findByCpf(String cpf) {
        return atletaRepository.findGraduacoesByFiltro(null, cpf);
    }

    public List<AtletaGraduacoesRecord> findGraduacoesByNome(String nome) {
        List<Atleta> atletas = atletaRepository.findGraduacoesByFiltro(nome,null);
        return atletas.stream()
            .map(atleta -> new AtletaGraduacoesRecord(atleta.getGraduacoes()))
            .toList();
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
        return new AtletaValidadoRecord(atleta.getId(), atleta.getNomeAtleta(), atleta.getContato().getEmail(), dtNascimento, atleta.getDocumentos().getCpf(), atleta.getDescricaoGraduacao());
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
