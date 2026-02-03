package br.dev.mmc.cbkt.controller.forms;

import br.dev.mmc.cbkt.domain.Pessoa;
import br.dev.mmc.cbkt.domain.enums.TipoPessoa;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaFormRequest {

    private Long id;
    private String nome;
    private String cpf; // armazenar sem máscara
    private String dataNascimento;
    private String email;
    private String telefone;
    private TipoPessoa tipo;
    private Boolean ativo;

    public Pessoa convertToPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(this.id);
        pessoa.setNome(this.nome);
        pessoa.setCpf(this.cpf != null ? this.cpf.replaceAll("[^\\d]", "") : null); // remover máscara
        pessoa.setDataNascimento(this.dataNascimento != null ? java.time.LocalDate.parse(this.dataNascimento) : null);
        pessoa.setEmail(this.email);
        pessoa.setTelefone(this.telefone);
        pessoa.setTipo(this.tipo);
        pessoa.setAtivo(this.ativo != null ? this.ativo : true);
        return pessoa;
    }
}