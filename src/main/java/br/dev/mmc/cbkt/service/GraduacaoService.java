package br.dev.mmc.cbkt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.dev.mmc.cbkt.domain.Graduacao;
import br.dev.mmc.cbkt.domain.comparators.GraduacaoComparators;
import br.dev.mmc.cbkt.repository.GraduacaoRepository;

@Service
public class GraduacaoService extends CrudServiceImpl<Graduacao, Long> {

    private final GraduacaoRepository repo;

    public GraduacaoService(GraduacaoRepository repo) {
        super(repo);
        this.repo = repo;
    }

    @Override
    public List<Graduacao> read() {
        return repo.findAll().stream()
            .sorted(GraduacaoComparators.BY_GRAU)
            .toList();
    }
}
