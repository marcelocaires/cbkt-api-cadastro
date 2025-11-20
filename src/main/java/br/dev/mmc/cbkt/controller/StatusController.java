package br.dev.mmc.cbkt.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller para verificação de status da API
 * 
 * @author Marcelo Moura Caires
 */
@RestController
@RequestMapping("/status")
@Tag(name = "Status", description = "Endpoints para verificação de status da API")
public class StatusController {

    private final BuildProperties buildProperties;

    public StatusController(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }
    
    @GetMapping
    @Operation(
        summary = "Verificar status da API",
        description = "Retorna informações sobre o status atual da API, incluindo nome, versão e timestamp"
    )
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> status = new HashMap<>();
        
        status.put("status", "UP");
        status.put("application", buildProperties.getName());
        status.put("version", buildProperties.getVersion());
        status.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        status.put("message", "API está funcionando corretamente");
        
        return ResponseEntity.ok(status);
    }

    /**
     * Endpoint simples para health check
     * 
     * @return Status UP
     */
    @GetMapping("/health")
    @Operation(
        summary = "Health check simplificado",
        description = "Endpoint simples para verificação rápida de disponibilidade"
    )
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        
        return ResponseEntity.ok(health);
    }
}
