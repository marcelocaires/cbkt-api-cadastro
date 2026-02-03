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
import br.dev.mmc.cbkt.domain.Atleta;
import br.dev.mmc.cbkt.domain.Clube;
import br.dev.mmc.cbkt.domain.ClubeInstrutor;
import br.dev.mmc.cbkt.domain.enums.TipoEntidade;
import br.dev.mmc.cbkt.domain.record.AtletaResumoRecord;
import br.dev.mmc.cbkt.domain.record.ClubeDetalheRecord;
import br.dev.mmc.cbkt.repository.AtletaRepository;
import br.dev.mmc.cbkt.repository.ClubeInstrutorRepository;
import br.dev.mmc.cbkt.repository.ClubeRepository;

@Service
public class ClubeService extends CrudServiceImpl<Clube, Long> {
    private final ClubeRepository repo;
    private final ClubeInstrutorRepository clubeInstrutorRepository;
    private final AtletaRepository atletaRepository;
    private final MandatoService mandatoService;

    public ClubeService(ClubeRepository repo,
        ClubeInstrutorRepository clubeInstrutorRepository,
        AtletaRepository atletaRepository,
        MandatoService mandatoService) {
        super(repo);
        this.repo = repo;
        this.clubeInstrutorRepository = clubeInstrutorRepository;
        this.atletaRepository = atletaRepository;
        this.mandatoService = mandatoService;
    }

    @Override
    public List<Clube> read() {
        Sort sort = Sort.by(Sort.Direction.ASC, "nome");
        return repo.findAll(sort);
    }

    public ClubeComMandatosDto getClubeCompletoById(Long id) {
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
        
        // Buscar instrutores do clube
        var instrutores = clubeInstrutorRepository.findByClubeId(id).stream()
            .map(ci -> ClubeComMandatosDto.ClubeInstrutorDto.builder()
                .id(ci.getId())
                .atletaId(ci.getAtleta().getId())
                .atletaNome(ci.getAtleta().getNomeAtleta())
                .dataInicio(ci.getDataInicio())
                .dataFim(ci.getDataFim())
                .ativo(ci.getAtivo())
                .observacao(ci.getObservacao())
                .build())
            .sorted((i1, i2) -> i1.getAtletaNome().compareToIgnoreCase(i2.getAtletaNome()))
            .collect(Collectors.toList());
        dto.setInstrutores(instrutores);
        
        // Buscar atletas do clube
        var atletas = clube.getAtletas().stream()
            .map(ac -> ClubeComMandatosDto.AtletaClubeDto.builder()
                .id(ac.getId())
                .atletaId(ac.getAtleta().getId())
                .atletaNome(ac.getAtleta().getNomeAtleta())
                .dataAdmissao(ac.getDataAdmissao())
                .dataSaida(ac.getDataSaida())
                .transferido(ac.getTransferido())
                .clubeOrigemId(ac.getClubeOrigem() != null ? ac.getClubeOrigem().getId() : null)
                .clubeOrigemNome(ac.getClubeOrigem() != null ? ac.getClubeOrigem().getNome() : null)
                .build())
            .sorted((a1, a2) -> a1.getAtletaNome().compareToIgnoreCase(a2.getAtletaNome()))
            .collect(Collectors.toList());
        dto.setAtletas(atletas);
        
        // Calcular estatísticas
        dto.setTotalAtletas(atletas.size());
        dto.setTotalInstrutores(instrutores.size());
        dto.setTotalMandatos(mandatos.size());
        dto.setTotalAtletasTransferidos((int) clube.getAtletas().stream()
            .filter(ac -> Boolean.TRUE.equals(ac.getTransferido()))
            .count());
        dto.setTotalAtletasArbitros((int) clube.getAtletas().stream()
            .filter(ac -> Boolean.TRUE.equals(ac.getAtleta().getChkArbitro()))
            .count());
        dto.setTotalAtletasAvaliadores((int) clube.getAtletas().stream()
            .filter(ac -> Boolean.TRUE.equals(ac.getAtleta().getChkAvaliador()))
            .count());
        
        return dto;
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

    
    public Page<Clube> getPageByFilter(Pageable pageable, String filtro) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 10, Sort.by("nome").ascending());
        }
        return repo.findAll(ClubeRepository.clubeFiltro(filtro), pageable);
    }

    // === Instrutores do Clube ===
    public List<ClubeInstrutor> listarInstrutoresAtivos(Long clubeId) {
        return clubeInstrutorRepository.findByClubeIdAndAtivoTrue(clubeId);
    }

    public ClubeInstrutor adicionarInstrutor(Long clubeId, Long atletaId, LocalDate dataInicio, String observacao) {
        Clube clube = repo.findById(clubeId)
            .orElseThrow(() -> new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Clube não encontrado"));
        Atleta atleta = atletaRepository.findById(atletaId)
            .orElseThrow(() -> new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Atleta não encontrado"));

        // Desativa vínculo ativo anterior, se existir
        clubeInstrutorRepository.findByClubeIdAndAtivoTrue(clubeId).stream()
            .filter(ci -> ci.getAtleta().getId().equals(atletaId))
            .forEach(ci -> {
                ci.setAtivo(false);
                ci.setDataFim(LocalDate.now());
                clubeInstrutorRepository.save(ci);
            });

        ClubeInstrutor novo = ClubeInstrutor.builder()
            .clube(clube)
            .atleta(atleta)
            .dataInicio(dataInicio != null ? dataInicio : LocalDate.now())
            .ativo(true)
            .observacao(observacao)
            .build();
        return clubeInstrutorRepository.save(novo);
    }

    public void removerInstrutor(Long clubeInstrutorId) {
        ClubeInstrutor ci = clubeInstrutorRepository.findById(clubeInstrutorId)
            .orElseThrow(() -> new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Instrutor não encontrado"));
        ci.setAtivo(false);
        ci.setDataFim(LocalDate.now());
        clubeInstrutorRepository.save(ci);
    }
}
