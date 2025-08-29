package br.dev.mmc.cbkt.domain;


import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@Entity @Table(name = "COMPETICAO")
public class Competicao {
    @Id
    @Column(name = "CODIGOCOMPETICAO")
    private Long id;

    @Column(name = "DESCRICAOCOMPETICAO", length = 150)
    private String descricao;

    @Column(name = "DATA_INICIO")
    private LocalDate dataInicio;

    @Column(name = "DATA_FIM")
    private LocalDate dataFim;

    @Column(name = "TIPOCOMPETICAO", length = 30)
    private String tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODIGOCLUBE")
    private Clube clube;

    @Column(name = "FORMARINSCRICAOAVALOR", length = 30)
    private String formaInscricaoAValor;

    @Column(name = "VALOR", precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(name = "FASE", length = 30)
    private String fase;

    @Column(name = "LOCAL", length = 120)
    private String local;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODIGOCLUBEREALIZACAO")
    private Clube clubeRealizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODIGOCLUBECHANCELA")
    private Clube clubeChancela;

    @Column(name = "OBSERVACOES", length = 255)
    private String observacoes;
}
