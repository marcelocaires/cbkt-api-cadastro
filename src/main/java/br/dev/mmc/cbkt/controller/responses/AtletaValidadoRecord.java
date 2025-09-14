package br.dev.mmc.cbkt.controller.responses;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public record AtletaValidadoRecord(
    Long id,   
    String nome,
    String email,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    Date dtNascimento,
    String cpf,
    String graduacao
) {}
