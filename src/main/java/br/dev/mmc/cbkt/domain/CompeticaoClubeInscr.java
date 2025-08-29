package br.dev.mmc.cbkt.domain;


import lombok.*;
import jakarta.persistence.*;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@Entity @Table(name = "COMPETICAO_CLUBE_INSCR")
public class CompeticaoClubeInscr {
    @EmbeddedId
    private CompeticaoClubeInscrId id;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("codigoCompeticao")
    @JoinColumn(name = "CODIGOCOMPETICAO")
    private Competicao competicao;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("codigoClube")
    @JoinColumn(name = "CODIGOCLUBE")
    private Clube clube;
}
