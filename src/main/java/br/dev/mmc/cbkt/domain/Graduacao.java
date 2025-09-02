package br.dev.mmc.cbkt.domain;

import java.math.BigDecimal;

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
@Entity @Table(name = "GRADUACAO")
public class Graduacao {

    @Id
    @Column(name = "CODIGOGRADUACAO")
    private Long id;

    @Column(name = "DESCRICAOGRADUACAO", length = 100)
    private String descricaoGraduacao;

    @Column(name = "CARENCIA")
    private Integer carencia;

    @Column(name = "CARENCIAMENOR")
    private Integer carenciaMenor;

    @Column(name = "VALOR", precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(name = "IDADEMINIMA")
    private Integer idadeMinima;

    @Column(name = "ANUIDADE_ATE")
    private Integer anuidadeAte;

    @Column(name = "ANUIDADE_APOS")
    private Integer anuidadeApos;

    @Column(name = "ANUIDADE", length = 20)
    private String anuidade;
}
