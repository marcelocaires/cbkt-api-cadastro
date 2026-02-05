package br.dev.mmc.cbkt.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.dev.mmc.cbkt.controller.responses.ClubeComMandatosDto;
import br.dev.mmc.cbkt.domain.Clube;
import br.dev.mmc.cbkt.domain.enums.TipoEntidade;
import br.dev.mmc.cbkt.domain.record.AtletaResumoRecord;
import br.dev.mmc.cbkt.domain.record.ClubeDetalheRecord;
import br.dev.mmc.cbkt.repository.ClubeRepository;

@Service
public class EntidadeService extends CrudServiceImpl<Clube, Long> {
    private final ClubeRepository repo;
    private final MandatoService mandatoService;

    public EntidadeService(ClubeRepository repo,
        MandatoService mandatoService) {
        super(repo);
        this.repo = repo;
        this.mandatoService = mandatoService;
    }

    @Override
    public List<Clube> read() {
        Sort sort = Sort.by(Sort.Direction.ASC, "nome");
        return repo.findAll(sort);
    }

    public ClubeComMandatosDto getEntidadeCompletaById(Long id) {
        Clube clube = repo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clube não encontrado"));
        var mandatos = mandatoService.listarMandatosEntidade(TipoEntidade.CLUBE, clube.getId()).stream()
            .collect(Collectors.toSet());
        
        ClubeComMandatosDto dto = new ClubeComMandatosDto(clube, mandatos);
        
        // Buscar a diretoria ativa (cargos do mandato ativo)
        var mandatoAtivo = mandatos.stream()
            .filter(m -> m.getDataFim() == null || m.getDataFim().isAfter(LocalDate.now()))
            .findFirst();
        
        if (mandatoAtivo.isPresent()) {
            var cargosAtivos = mandatoAtivo.get().getCargos();
            if (cargosAtivos != null) {
                dto.setDiretoriaAtiva(new ArrayList<>(cargosAtivos));
            }
        }
        
        dto.setTotalMandatos(mandatos.size());        
        return dto;
    }

    public ClubeDetalheRecord findDetalheById(Long id) {
        var c = repo.findDetalheById(id)
            .orElseThrow(() -> new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Entidade não encontrada"));
        var atletas = c.getAtletas().stream()
            .map(v -> new AtletaResumoRecord(v.getAtleta().getId(), v.getAtleta().getNomeAtleta()))
            .toList();
        return new ClubeDetalheRecord(c.getId(), c.getNome(), atletas);
    }

    public List<ClubeDetalheRecord> findDetalheByNome(String nome) {
        List<Clube> clubes = repo.findDetalheByNome(nome);
        if (clubes.isEmpty()) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Entidade não encontrada");
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

    
    public Page<Clube> getPageByFilter(Pageable pageable, String filtro) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 10, Sort.by("nome").ascending());
        }
        return repo.findAll(ClubeRepository.clubeFiltro(filtro), pageable);
    }
    public List<Clube> getFederacoesFilter(String filtro) {
        return repo.findAll(ClubeRepository.federacaoFiltro(filtro));
    }
    public Page<Clube> getFederacoesPageFilter(Pageable pageable, String filtro) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 10, Sort.by("nome").ascending());
        }
        return repo.findAll(ClubeRepository.federacaoFiltro(filtro), pageable);
    }
    public List<Clube> getConfederacao() {
        return repo.findAll(ClubeRepository.confederacaoFiltro(null));
    }
    public Page<Clube> getConfederacaoPage() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.by("nome").ascending());
        return repo.findAll(ClubeRepository.confederacaoFiltro(null),pageable);
    }
}
