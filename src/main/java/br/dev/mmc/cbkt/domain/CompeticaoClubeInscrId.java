package br.dev.mmc.cbkt.domain;


import lombok.*;
import jakarta.persistence.*;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
@Embeddable
public class CompeticaoClubeInscrId {
    @Column(name = "CODIGOCOMPETICAO")
    private Long codigoCompeticao;
    @Column(name = "CODIGOCLUBE")
    private Long codigoClube;
}
