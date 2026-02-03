package br.dev.mmc.cbkt.controller.forms;

import jakarta.validation.constraints.NotNull;

public record UpdateAtletaPcdForm(
    @NotNull Long id,
    @NotNull Boolean isPcd,
    String deficienciaTipo,
    String deficienciaDescricao,
    String deficienciaCID,
    String urlLaudoMedico) {}

