package br.dev.mmc.cbkt.controller.responses;

import java.time.LocalDate;

import br.dev.mmc.cbkt.domain.Cargo;
import br.dev.mmc.cbkt.domain.enums.MotivoSaida;
import br.dev.mmc.cbkt.domain.enums.TipoOcupacao;
import br.dev.mmc.cbkt.domain.enums.TipoVinculo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class MandatoCargoDto {

    private Long id;

    private PessoaDto pessoa;

    private TipoOcupacao tipoOcupacao;

    private Cargo cargo;

    private TipoVinculo tipoVinculo;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    private Boolean ativo;

    private Long substituiuId;

    private MotivoSaida motivoSaida;

    private String observacao;
}
