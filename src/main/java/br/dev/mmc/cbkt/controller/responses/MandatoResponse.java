package br.dev.mmc.cbkt.controller.responses;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.dev.mmc.cbkt.domain.enums.TipoEntidade;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MandatoResponse {
    Long id;
    TipoEntidade tipoEntidade;
    Long entidadeId;
    LocalDate dataInicio;
    LocalDate dataFim;
    Boolean ativo;
    String descricao;
    String documentoPath;
    LocalDateTime criadoEm;
    LocalDateTime atualizadoEm;
}
