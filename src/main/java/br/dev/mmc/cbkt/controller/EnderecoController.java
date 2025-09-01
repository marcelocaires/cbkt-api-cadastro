package br.dev.mmc.cbkt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.mmc.cbkt.controller.record.EnderecoRecord;
import br.dev.mmc.cbkt.service.EnderecoService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/endereco")
public class EnderecoController{
    private EnderecoService service;

    EnderecoController(EnderecoService service) {
        this.service = service;
    }

    @GetMapping("/{cep}")
    public Mono<EnderecoRecord> consultarCep(@PathVariable String cep) {
        return service.consultarCep(cep);
    }

}   