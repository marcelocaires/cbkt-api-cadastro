package br.dev.mmc.cbkt.domain;


import lombok.*;
import jakarta.persistence.*;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@Entity @Table(name = "ANUIDADE_CLUBE")
public class AnuidadeClube {
    @EmbeddedId
    private AnuidadeClubeId id;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("sequencialAnuidade")
    @JoinColumn(name = "SEQUENCIALANUIDADE")
    private Anuidade anuidade;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("codigoClube")
    @JoinColumn(name = "CODIGOCLUBE")
    private Clube clube;
}
