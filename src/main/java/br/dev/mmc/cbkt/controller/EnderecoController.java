package br.dev.mmc.cbkt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.mmc.cbkt.domain.record.EnderecoRecord;
import br.dev.mmc.cbkt.service.EnderecoService;

@RestController
@RequestMapping("/api/endereco")
public class EnderecoController{
    private EnderecoService service;

    EnderecoController(EnderecoService service) {
        this.service = service;
    }  
    // Aceita 8 dígitos ou 99999-999
    @GetMapping("/{cep}")
    public ResponseEntity<EnderecoRecord> consultarCep(@PathVariable String cep){
        return ResponseEntity.ok(service.consultarCep(cep));
    }
}   