package br.dev.mmc.cbkt.domain;


import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@Entity @Table(name = "CLUBE_SUSPENS")
public class ClubeSuspens {
    @Id
    @Column(name = "SEQUENCIAL")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODIGOCLUBE")
    private Clube clube;

    @Column(name = "DATAINICIO")
    private LocalDate dataInicio;

    @Column(name = "DATATERMINO")
    private LocalDate dataTermino;

    @Column(name = "MOTIVO", length = 120)
    private String motivo;

    @Column(name = "DESCRICAO", length = 255)
    private String descricao;
}
