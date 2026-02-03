package br.dev.mmc.cbkt.controller.forms;

import java.time.LocalDate;

import br.dev.mmc.cbkt.domain.enums.MotivoSaida;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EncerrarOcupacaoRequest {

    @NotNull
    private LocalDate dataFim;

    private MotivoSaida motivoSaida;

    private String observacao;
}
