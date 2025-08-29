package br.dev.mmc.cbkt.domain;


import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@Entity @Table(name = "EXAME")
public class Exame {
    @Id
    @Column(name = "CODIGOEXAME")
    private Long id;

    @Column(name = "DESCRICAOEXAME", length = 150)
    private String descricao;

    @Column(name = "DATAEXAME")
    private LocalDate data;

    @Column(name = "HORAEXAME")
    private LocalTime hora;

    @Column(name = "LOCALEXAME", length = 120)
    private String local;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODIGOCLUBE")
    private Clube clube;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODIGOCLUBERESPONSAVEL")
    private Clube clubeResponsavel;

    @Column(name = "EXAMINADOR_01", length = 120) private String examinador01;
    @Column(name = "EXAMINADOR_02", length = 120) private String examinador02;
    @Column(name = "EXAMINADOR_03", length = 120) private String examinador03;
    @Column(name = "EXAMINADOR_04", length = 120) private String examinador04;
    @Column(name = "EXAMINADOR_05", length = 120) private String examinador05;

    @Column(name = "ETAPAEXAME", length = 30)
    private String etapa;
}
