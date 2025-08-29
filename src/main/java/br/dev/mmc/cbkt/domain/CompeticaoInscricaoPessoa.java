package br.dev.mmc.cbkt.domain;


import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@Entity @Table(name = "COMPETICAO_INSCRICAO_P")
public class CompeticaoInscricaoPessoa {
    @Id
    @Column(name = "SEQUENCIALATLETA")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODIGOCOMPETICAO")
    private Competicao competicao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODIGOCLUBE")
    private Clube clube;

    @Column(name = "NUMERODOCUMENTO", length = 30)
    private String numeroDocumento;

    @Column(name = "NOMEATLETA", length = 120)
    private String nomeAtleta;

    @Column(name = "SEXO", length = 1)
    private String sexo;

    @Column(name = "DATANASCIMENTO")
    private LocalDate dataNascimento;

    @Column(name = "NOMEPAI", length = 120)
    private String nomePai;

    @Column(name = "NOMEMAE", length = 120)
    private String nomeMae;
}
