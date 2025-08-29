package br.dev.mmc.cbkt.domain;


import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@Entity @Table(name = "CONTRATOS")
public class Contrato {
    @Id
    @Column(name = "COD_CONTRATO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODIGOCLUBE")
    private Clube clube;

    @Column(name = "NRO_CONTRATO", length = 30)
    private String numero;

    @Column(name = "OBS_CONTRATO", length = 255)
    private String observacao;

    @Column(name = "DAT_CONTRATO")
    private LocalDate dataContrato;

    @Column(name = "DAT_INICIO")
    private LocalDate dataInicio;

    @Column(name = "DAT_TERMINO")
    private LocalDate dataTermino;

    @Column(name = "VR_CONTRATO", precision = 15, scale = 2)
    private BigDecimal valor;
}
