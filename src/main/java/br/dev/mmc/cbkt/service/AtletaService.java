package br.dev.mmc.cbkt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.dev.mmc.cbkt.domain.Atleta;
import br.dev.mmc.cbkt.domain.record.AtletaGraduacoesRecord;
import br.dev.mmc.cbkt.repository.AtletaRepository;

@Service
public class AtletaService extends CrudServiceImpl<Atleta, Long> {

    private final AtletaRepository atletaRepository;

    public AtletaService(AtletaRepository repo, AtletaRepository atletaRepository) {
        super(repo);
        this.atletaRepository = atletaRepository;
    }

    public List<Atleta> findByNome(String nome) {
        return atletaRepository.searchByNome(nome).isEmpty() ? null : atletaRepository.searchByNome(nome);
    }

    public List<AtletaGraduacoesRecord> findGraduacoesByNome(String nome) {
        List<Atleta> atletas = atletaRepository.findGraduacoesByFiltro(nome,null);
        return atletas.stream()
            .map(atleta -> new AtletaGraduacoesRecord(atleta.getGraduacoes()))
            .toList();
   }
}
