package br.dev.mmc.cbkt.service;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import br.dev.mmc.cbkt.config.exceptions.CustomBadRequestException;
import br.dev.mmc.cbkt.domain.record.EnderecoRecord;

@Service
public class EnderecoService{

    private final WebClient viaCepClient;

    public EnderecoService(WebClient viaCepClient) {
        this.viaCepClient = viaCepClient;
    }

    public EnderecoRecord consultarCep(String cepNumerico) throws CustomBadRequestException {
        // Normaliza: remove não-dígitos (permite 99999-999 ou 99999999)
        String cep = cepNumerico.replaceAll("\\D", "");
        if (!cep.matches("\\d{8}")) {
            throw new CustomBadRequestException("CEP inválido. Use 8 dígitos numéricos, nos seguintes formatos (ex: 01001000, 01.001-000, ou 01001-000).");
        }

        EnderecoRecord dto = viaCepClient.get()
            .uri(uriBuilder -> uriBuilder.path("/ws/{cep}/json/").build(cep))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, resp ->
                resp.bodyToMono(String.class).map(body ->
                    new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "CEP não encontrado")
                )
            )
            .onStatus(HttpStatusCode::is5xxServerError, resp ->
                resp.bodyToMono(String.class).map(body ->
                    new ResponseStatusException(org.springframework.http.HttpStatus.BAD_GATEWAY, "Aconteceu uma falha no serviço, tente novamente mais tarde.")
                )
            )
            .bodyToMono(EnderecoRecord.class)
            .block();

        if (dto == null) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_GATEWAY, "Resposta vazia do serviço.");
        }
        if (Boolean.TRUE.equals(dto.erro())) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "CEP não encontrado");
        }
        return dto;
    }
}
