package br.dev.mmc.cbkt.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "ATLETA_CLUBE")
public class AtletaClube {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "atleta_clube_sequence")
    @SequenceGenerator(name = "atleta_clube_sequence", sequenceName = "atleta_clube_sequence", allocationSize = 1)
    @Column(name = "SEQUENCIAL")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODIGOATLETA", nullable = false)
    private Atleta atleta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CODIGOCLUBE", nullable = false)
    private Clube clube;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "DATAADMISSAO")
    private Date dataAdmissao;

    @Column(name = "TRANSFERIDO")
    private Boolean transferido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CODIGO_CLUBE_ORIGEM", nullable = true)
    private Clube clubeOrigem;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "DATA_SAIDA")
    private Date dataSaida;
}
