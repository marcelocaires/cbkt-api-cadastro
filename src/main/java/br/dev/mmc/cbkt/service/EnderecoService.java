package br.dev.mmc.cbkt.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.dev.mmc.cbkt.controller.record.EnderecoRecord;
import reactor.core.publisher.Mono;

@Service
public class EnderecoService{

    private WebClient webClientViaCEP;

    public EnderecoService(WebClient WebClientViaCEP) {
        this.webClientViaCEP = WebClientViaCEP;
    }

    public Mono<EnderecoRecord> consultarCep(String cep){
        cep=cep.replaceAll("[^0-9]", "");
        try{
            return webClientViaCEP.get()
                .uri("/{cep}/json/", cep)
                .retrieve()
                .bodyToMono(EnderecoRecord.class);
        } catch (Exception e) {
            return Mono.error(new RuntimeException("Erro ao consultar CEP: " + e.getMessage()));
        }
    }
}
