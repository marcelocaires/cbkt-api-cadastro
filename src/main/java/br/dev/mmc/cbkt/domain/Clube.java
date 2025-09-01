package br.dev.mmc.cbkt.domain;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@Entity @Table(name = "CLUBE")
public class Clube {

    @Id
    @Column(name = "CODIGOCLUBE")
    private Long id;

    // Dados gerais
    @Column(name = "NOMECLUBE", nullable = false, length = 120)
    private String nome;

    @Column(name = "ABREVIATURA", length = 30)
    private String abreviatura;

    @Column(name = "CLASSIFICACAO", length = 30)
    private String classificacao;

    @Column(name = "CNPJ", length = 18)
    private String cnpj;

    @Column(name = "RESPONSAVEL", length = 120)
    private String responsavel;

    @Column(name = "PRESIDENTE", length = 120)
    private String presidente;

    @Column(name = "DIRETORTECNICO", length = 120)
    private String diretorTecnico;

    @Column(name = "DATAFUNDACAO")
    private LocalDate dataFundacao;

    // Endereço
    @Column(name = "CIDADE", length = 80)
    private String cidade;

    @Column(name = "CEP", length = 10)
    private String cep;

    @Column(name = "LOGRADOURO", length = 120)
    private String logradouro;

    @Column(name = "NUMERO", length = 15)
    private String numero;

    @Column(name = "BAIRRO", length = 80)
    private String bairro;

    @Column(name = "COMPLEMENTO", length = 80)
    private String complemento;

    // Contato
    @Column(name = "TELEFONE", length = 30)
    private String telefone;

    @Column(name = "EMAIL", length = 120)
    private String email;
}
