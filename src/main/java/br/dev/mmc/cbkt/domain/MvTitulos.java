package br.dev.mmc.cbkt.domain;


import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@Entity @Table(name = "MVTITULOS")
public class MvTitulos {
    @Id
    @Column(name = "COD_LANCTO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODIGOCLUBE")
    private Clube clube;

    @Column(name = "TIP_LANCTO", length = 10)
    private String tipo;

    @Column(name = "DAT_LANCTO")
    private LocalDate dataLancamento;

    @Column(name = "VLR_LANCTO", precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(name = "OBSERVACAO", length = 255)
    private String observacao;
}
