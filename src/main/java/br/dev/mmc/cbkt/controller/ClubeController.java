package br.dev.mmc.cbkt.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.mmc.cbkt.domain.Clube;
import br.dev.mmc.cbkt.domain.record.ClubeDetalheRecord;
import br.dev.mmc.cbkt.service.ClubeService;

@RestController
@RequestMapping("/api/clube")
public class ClubeController extends CrudController<Clube, Long> {
    private final ClubeService service;
    ClubeController(ClubeService service) {
        super(service);
        this.service = service;
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ClubeDetalheRecord>> buscarDetalheByNome(@PathVariable String nome) {
        var detalhe = service.findDetalheByNome(nome);
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
}