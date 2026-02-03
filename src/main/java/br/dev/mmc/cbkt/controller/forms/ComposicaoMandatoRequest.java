package br.dev.mmc.cbkt.controller.forms;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComposicaoMandatoRequest {

    @NotNull
    private Long cargoId;

    private Boolean obrigatorio;

    private Boolean permiteSuplente;

    private Integer ordem;
}
