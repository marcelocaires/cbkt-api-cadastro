package br.dev.mmc.cbkt.domain;


import lombok.*;
import jakarta.persistence.*;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
@Embeddable
public class AnuidadeClubeId {
    @Column(name = "SEQUENCIALANUIDADE")
    private Long sequencialAnuidade;

    @Column(name = "CODIGOCLUBE")
    private Long codigoClube;
}
