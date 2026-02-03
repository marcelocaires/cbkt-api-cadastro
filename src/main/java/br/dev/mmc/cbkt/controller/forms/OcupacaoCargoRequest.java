package br.dev.mmc.cbkt.controller.forms;

import java.time.LocalDate;

import br.dev.mmc.cbkt.domain.enums.TipoOcupacao;
import br.dev.mmc.cbkt.domain.enums.TipoVinculo;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OcupacaoCargoRequest {

    @NotNull
    private Long composicaoMandatoId;

    @NotNull
    private Long pessoaId;

    @NotNull
    private TipoOcupacao tipoOcupacao;

    @NotNull
    private TipoVinculo tipoVinculo;

    @NotNull
    private LocalDate dataInicio;
}
