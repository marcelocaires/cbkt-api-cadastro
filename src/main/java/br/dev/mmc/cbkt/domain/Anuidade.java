package br.dev.mmc.cbkt.domain;


import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@Entity @Table(name = "ANUIDADE")
public class Anuidade {
    @Id
    @Column(name = "SEQUENCIALANUIDADE")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODIGOCLUBE")
    private Clube clube;

    @Column(name = "ANOFISCAL", length = 9)
    private String anoFiscal;

    @Column(name = "DATALANCAMENTO")
    private LocalDate dataLancamento;

    @Column(name = "SITUACAO", length = 20)
    private String situacao;

    @Column(name = "TIPO", length = 20)
    private String tipo;
}
