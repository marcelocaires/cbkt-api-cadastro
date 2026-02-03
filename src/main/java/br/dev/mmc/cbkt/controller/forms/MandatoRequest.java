package br.dev.mmc.cbkt.controller.forms;

import java.time.LocalDate;

import br.dev.mmc.cbkt.domain.enums.TipoEntidade;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MandatoRequest {

    @NotNull
    private TipoEntidade tipoEntidade;

    @NotNull
    private Long entidadeId;

    @NotNull
    private LocalDate dataInicio;

    private LocalDate dataFim;

    private String descricao;

    private String documentoPath;
}
