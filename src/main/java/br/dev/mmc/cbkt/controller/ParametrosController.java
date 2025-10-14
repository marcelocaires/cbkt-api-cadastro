package br.dev.mmc.cbkt.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.mmc.cbkt.domain.enums.GraduacaoCorEnum;
import br.dev.mmc.cbkt.domain.enums.GraduacaoGrauEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/parametros")
@Tag(name = "Parâmetros", description = "Endpoints para consultar enums e parâmetros do sistema")
public class ParametrosController {

    @GetMapping("/graduacao/cores")
    @Operation(
        summary = "Lista todas as cores de graduação disponíveis",
        description = "Retorna uma lista com todas as cores de graduação disponíveis no sistema"
    )
    public ResponseEntity<List<Map<String, Object>>> getCoresGraduacao() {
        List<Map<String, Object>> cores = GraduacaoCorEnum.listarTodos();
        return ResponseEntity.ok(cores.stream().map(c-> {
            return c;
        }).collect(Collectors.toList()));
    }

    @GetMapping("/graduacao/graus")
    @Operation(
        summary = "Lista todos os graus de graduação disponíveis",
        description = "Retorna uma lista com todos os graus de graduação (KYU e DAN) disponíveis no sistema"
    )
    public ResponseEntity<List<Map<String, String>>> getGrausGraduacao() {
        List<Map<String, String>> graus = Arrays.stream(GraduacaoGrauEnum.values())
            .map(grau -> Map.of(
                "codigo", grau.name(),
                "grau", grau.getTitulo()
            ))
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(graus);
    }
}