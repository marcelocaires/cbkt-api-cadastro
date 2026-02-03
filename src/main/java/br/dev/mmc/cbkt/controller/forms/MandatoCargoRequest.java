package br.dev.mmc.cbkt.controller.forms;

import java.time.LocalDate;

import br.dev.mmc.cbkt.domain.Cargo;
import br.dev.mmc.cbkt.domain.Pessoa;
import br.dev.mmc.cbkt.domain.enums.MotivoSaida;
import br.dev.mmc.cbkt.domain.enums.TipoVinculo;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MandatoCargoRequest {

    @NotNull(message = "Pessoa é obrigatório")
    private PessoaFormRequest pessoa;

    @NotNull(message = "Cargo é obrigatório")
    private Cargo cargo;

    @NotNull(message = "Tipo de vínculo é obrigatório")
    private TipoVinculo tipoVinculo;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    private Boolean ativo;

    private Long substituiuId;

    private MotivoSaida motivoSaida;

    private String observacao;
}
