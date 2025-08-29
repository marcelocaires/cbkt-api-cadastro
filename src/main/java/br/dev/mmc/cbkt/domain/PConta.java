package br.dev.mmc.cbkt.domain;


import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@Entity @Table(name = "PCONTA")
public class PConta {
    @EmbeddedId
    private PContaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("codigoClube")
    @JoinColumn(name = "CODIGOCLUBE")
    private Clube clube;

    @Column(name = "DES_PCONTA", length = 120)
    private String descricao;

    @Column(name = "TIP_PCONTA", length = 10)
    private String tipo;

    @Column(name = "NAT_SALDO", length = 1)
    private String naturezaSaldo;

    @Column(name = "SALDO", precision = 15, scale = 2)
    private BigDecimal saldo;

    @Column(name = "DAT_CADASTRO")
    private LocalDate dataCadastro;
}
