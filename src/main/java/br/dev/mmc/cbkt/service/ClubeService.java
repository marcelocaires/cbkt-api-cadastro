package br.dev.mmc.cbkt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.dev.mmc.cbkt.domain.Clube;
import br.dev.mmc.cbkt.domain.record.AtletaResumoRecord;
import br.dev.mmc.cbkt.domain.record.ClubeDetalheRecord;
import br.dev.mmc.cbkt.repository.ClubeRepository;

@Service
public class ClubeService extends CrudServiceImpl<Clube, Long> {
    private final ClubeRepository repo;

    public ClubeService(ClubeRepository repo) {
        super(repo);
        this.repo = repo;
    }

    public ClubeDetalheRecord findDetalheById(Long id) {
        var c = repo.findDetalheById(id)
            .orElseThrow(() -> new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Clube não encontrado"));
        var atletas = c.getAtletas().stream()
            .map(v -> new AtletaResumoRecord(v.getAtleta().getId(), v.getAtleta().getNomeAtleta()))
            .toList();
        return new ClubeDetalheRecord(c.getId(), c.getNome(), atletas);
    }

    public List<ClubeDetalheRecord> findDetalheByNome(String nome) {
        List<Clube> clubes = repo.findDetalheByNome(nome);
        if (clubes.isEmpty()) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Clube não encontrado");
        }
        List<ClubeDetalheRecord> result = new ArrayList<>();
        clubes.stream().forEach(c -> {
            var atletas = c.getAtletas().stream()
                .map(v -> new AtletaResumoRecord(v.getAtleta().getId(), v.getAtleta().getNomeAtleta()))
                .toList();
            result.add(new ClubeDetalheRecord(c.getId(), c.getNome(), atletas));
        });
        return result;
    }

    public List<ClubeDetalheRecord> searchDetalheAll() {
        var clubes = repo.searchDetalheAll();
        return clubes.stream()
            .map(c -> {
                var atletas = c.getAtletas().stream()
                    .map(v -> new AtletaResumoRecord(v.getAtleta().getId(), v.getAtleta().getNomeAtleta()))
                    .toList();
                return new ClubeDetalheRecord(c.getId(), c.getNome(), atletas);
            })
            .toList();
    }
}
