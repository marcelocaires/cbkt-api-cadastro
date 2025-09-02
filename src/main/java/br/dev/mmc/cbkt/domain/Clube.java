package br.dev.mmc.cbkt.domain;


import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    // Contato
    @Column(name = "TELEFONE", length = 30)
    private String telefone;

    @Column(name = "EMAIL", length = 120)
    private String email;

    @Embedded
    private Endereco endereco;

    // Relacionamento com AtletaClube
    @OneToMany(mappedBy = "clube", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<AtletaClube> atletas = new LinkedHashSet<>();
}
