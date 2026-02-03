package br.dev.mmc.cbkt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.mmc.cbkt.domain.Pessoa;
import br.dev.mmc.cbkt.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pessoa")
@Validated
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Obtém pessoa por CPF")
    public ResponseEntity<Pessoa> obterPessoaPorCpf(@PathVariable String cpf) {
        Pessoa pessoa = pessoaService.obterPessoaPorCpf(cpf);
        if (pessoa != null) {
            return ResponseEntity.ok(pessoa);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
        }
    }
}