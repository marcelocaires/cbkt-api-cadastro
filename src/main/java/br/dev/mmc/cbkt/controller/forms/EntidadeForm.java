package br.dev.mmc.cbkt.controller.forms;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.dev.mmc.cbkt.domain.Clube;
import br.dev.mmc.cbkt.domain.Contato;
import br.dev.mmc.cbkt.domain.Endereco;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class EntidadeForm {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 120, message = "Nome deve ter entre 3 e 120 caracteres")
    private String nome;

    @Size(max = 30, message = "Abreviatura deve ter no máximo 30 caracteres")
    private String abreviatura;

    @NotNull(message = "Classificação é obrigatória")
    @Size(max = 30, message = "Classificação deve ter no máximo 30 caracteres")
    private String classificacao;

    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$", 
        message = "CNPJ deve estar no formato XX.XXX.XXX/XXXX-XX")
    private String cnpj;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataFundacao;

    // Contato
    @Email(message = "Email inválido")
    @Size(max = 120, message = "Email deve ter no máximo 120 caracteres")
    private String email;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String telefone;

    // Endereço
    @Size(max = 120, message = "Logradouro deve ter no máximo 120 caracteres")
    private String logradouro;

    @Size(max = 15, message = "Número deve ter no máximo 15 caracteres")
    private String numero;

    @Size(max = 80, message = "Bairro deve ter no máximo 80 caracteres")
    private String bairro;

    @Size(max = 80, message = "Complemento deve ter no máximo 80 caracteres")
    private String complemento;

    @Size(max = 80, message = "Cidade deve ter no máximo 80 caracteres")
    private String cidade;

    @Size(max = 2, message = "Estado deve ter 2 caracteres")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Estado deve conter apenas 2 letras maiúsculas")
    private String estado;

    @Size(max = 10, message = "CEP deve ter no máximo 10 caracteres")
    @Pattern(regexp = "^\\d{5}-\\d{3}$|^\\d{8}$", message = "CEP deve estar no formato XXXXX-XXX ou XXXXXXXX")
    private String cep;

    public Clube toEntity() {
        Contato contato = Contato.builder()
            .email(this.email)
            .telefone(this.telefone)
            .build();

        Endereco endereco = Endereco.builder()
            .logradouro(this.logradouro)
            .numero(this.numero)
            .bairro(this.bairro)
            .complemento(this.complemento)
            .cidade(this.cidade)
            .estado(this.estado)
            .cep(this.cep)
            .uf(this.estado)
            .build();

        return Clube.builder()
            .nome(this.nome)
            .abreviatura(this.abreviatura)
            .classificacao(this.classificacao)
            .cnpj(this.cnpj)
            .dataFundacao(this.dataFundacao)
            .contato(contato)
            .endereco(endereco)
            .build();
    }
}
