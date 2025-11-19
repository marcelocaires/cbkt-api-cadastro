package br.dev.mmc.cbkt.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("${info.app.name}")
    private String applicationName;

    @Value("${info.app.version}")
    private String applicationVersion;

    /**
     * Endpoint para verificar se a API está ativa e funcionando
     * 
     * @return Informações de status da API
     */
    @GetMapping
    @Operation(
        summary = "Verificar status da API",
        description = "Retorna informações sobre o status atual da API, incluindo nome, versão e timestamp"
    )
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> status = new HashMap<>();
        
        status.put("status", "UP");
        status.put("application", applicationName);
        status.put("version", applicationVersion);
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
