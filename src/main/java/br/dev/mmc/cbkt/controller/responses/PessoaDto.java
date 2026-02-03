package br.dev.mmc.cbkt.controller.responses;

import java.time.LocalDate;

import br.dev.mmc.cbkt.domain.enums.TipoPessoa;
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
public class PessoaDto {

    private Long id;

    private String nome;

    private String cpf;

    private String rg;

    private LocalDate dataNascimento;

    private String email;

    private String telefone;

    private TipoPessoa tipo;

    private Boolean ativo;
}
