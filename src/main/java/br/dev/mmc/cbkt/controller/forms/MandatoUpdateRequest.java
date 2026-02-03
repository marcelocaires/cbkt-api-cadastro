package br.dev.mmc.cbkt.controller.forms;

import java.time.LocalDate;

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
public class MandatoUpdateRequest {

    @NotNull(message = "Data de início é obrigatória")
    private LocalDate dataInicio;

    private LocalDate dataFim;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    private String documentoPath;

    @NotNull(message = "Ativo é obrigatório")
    private Boolean ativo;
}
