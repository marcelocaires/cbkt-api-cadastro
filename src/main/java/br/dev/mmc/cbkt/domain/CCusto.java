package br.dev.mmc.cbkt.domain;


import lombok.*;
import jakarta.persistence.*;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@Entity @Table(name = "CCUSTO")
public class CCusto {
    @EmbeddedId
    private CCustoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("codigoClube")
    @JoinColumn(name = "CODIGOCLUBE")
    private Clube clube;

    @Column(name = "DES_CCUSTO", length = 120)
    private String descricao;

    @Column(name = "TIP_CCUSTO", length = 10)
    private String tipo;

    @Column(name = "NIV_CCUSTO", length = 10)
    private String nivel;
}
