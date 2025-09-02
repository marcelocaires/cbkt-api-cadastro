package br.dev.mmc.cbkt.domain;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@Entity @Table(name = "ATLETA_GRADUACAO")
public class AtletaGraduacao {

    @Id
    @Column(name = "SEQUENCIAL")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODIGOATLETA", nullable = false)
    private Atleta atleta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODIGOGRADUACAO", nullable = false)
    private Graduacao graduacao;

    @Column(name = "DATAGRADUACAO")
    private LocalDate dataGraduacao;

    @Column(name = "NOTAGRADUACAO")
    private Double notaGraduacao;
}
