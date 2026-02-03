package br.dev.mmc.cbkt.controller.forms;

import java.time.LocalDate;
import java.util.List;

import br.dev.mmc.cbkt.domain.enums.TipoEntidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
public class MandatoComCargosRequest {

    @NotNull(message = "Tipo de entidade é obrigatório")
    private TipoEntidade tipoEntidade;

    @NotNull(message = "ID da entidade é obrigatório")
    private Long entidadeId;

    @NotNull(message = "Data de início é obrigatória")
    private LocalDate dataInicio;

    private LocalDate dataFim;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @Valid
    private List<MandatoCargoRequest> cargos;
}
