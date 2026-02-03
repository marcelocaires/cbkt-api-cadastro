package br.dev.mmc.cbkt.controller;

import java.util.List;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import br.dev.mmc.cbkt.controller.responses.ClubeComMandatosDto;
import br.dev.mmc.cbkt.domain.Clube;
import br.dev.mmc.cbkt.domain.ClubeInstrutor;
import br.dev.mmc.cbkt.domain.record.ClubeDetalheRecord;
import br.dev.mmc.cbkt.service.ClubeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/clube")
@Tag(name = "Clube")
public class ClubeController extends CrudController<Clube, Long> {
    private final ClubeService service;

    ClubeController(ClubeService service) {
        super(service);
        this.service = service;
    }

    @GetMapping("/page")
    @Operation(
        summary = "Retorna clubes com paginação.",
        description = """
            Retorna uma lista paginada de clubes.
            - Parâmetros opcionais: page (número da página, padrão 0), size (tamanho da página, padrão 10), sort (campo de ordenação, padrão nome).
            - Filtro opcional via parâmetro 'filter'.
            - Exemplo: /api/clube/page?page=0&size=10&sort=nome,asc
            - Esse endpoint exige autenticação.
        """
    )
    public Page<Clube> getAllPage(
        @RequestParam(required = false) String filter,
        Pageable pageable){
        return service.getPageByFilter(pageable, filter);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ClubeDetalheRecord>> buscarDetalheByNome(@PathVariable String nome) {
        var detalhe = service.findDetalheByNome(nome);
        return ResponseEntity.ok(detalhe);
    }

    @GetMapping("/full/{id}")
    public ResponseEntity<ClubeComMandatosDto> buscarClubeComMandatosById(@PathVariable Long id) {
        var detalhe = service.getClubeCompletoById(id);
        return ResponseEntity.ok(detalhe);
    }    

    @GetMapping("/id/{id}")
    public ResponseEntity<ClubeDetalheRecord> buscarDetalheById(@PathVariable Long id) {
        var detalhe = service.findDetalheById(id);
        return ResponseEntity.ok(detalhe);
    } 


    @GetMapping("/detalhe/all")
    public ResponseEntity<java.util.List<ClubeDetalheRecord>> listarDetalhes() {
        var detalhes = service.searchDetalheAll();
        return ResponseEntity.ok(detalhes);
    }

    // === Instrutores ===
    @GetMapping("/{clubeId}/instrutores")
    @Operation(summary = "Lista instrutores ativos do clube")
    public ResponseEntity<List<ClubeInstrutor>> listarInstrutores(@PathVariable Long clubeId) {
        return ResponseEntity.ok(service.listarInstrutoresAtivos(clubeId));
    }

    @PostMapping("/{clubeId}/instrutor/{atletaId}")
    @Operation(summary = "Adiciona ou reativa um instrutor no clube")
    public ResponseEntity<ClubeInstrutor> adicionarInstrutor(
            @PathVariable Long clubeId,
            @PathVariable Long atletaId,
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestBody(required = false) String observacao) {
        return ResponseEntity.ok(service.adicionarInstrutor(clubeId, atletaId, dataInicio, observacao));
    }

    @DeleteMapping("/instrutor/{id}")
    @Operation(summary = "Remove (desativa) um instrutor do clube")
    public ResponseEntity<Void> removerInstrutor(@PathVariable Long id) {
        service.removerInstrutor(id);
        return ResponseEntity.noContent().build();
    }
}