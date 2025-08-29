package br.dev.mmc.cbkt.domain;


import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@Entity @Table(name = "ATLETA_CLUBE")
public class AtletaClube {
    @Id
    @Column(name = "SEQUENCIAL")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODIGOATLETA")
    private Atleta atleta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODIGOCLUBE")
    private Clube clube;

    @Column(name = "DATAADMISSAO")
    private LocalDate dataAdmissao;

    @Column(name = "TRANSFERIDO")
    private Boolean transferido;
}
