package br.dev.mmc.cbkt.domain;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.dev.mmc.cbkt.domain.enums.MotivoSaida;
import br.dev.mmc.cbkt.domain.enums.TipoOcupacao;
import br.dev.mmc.cbkt.domain.enums.TipoVinculo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "MANDATO_CARGO")
public class MandatoCargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "MANDATO_ID", nullable = false)
    private Mandato mandato;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "PESSOA_ID", nullable = false)
    private Pessoa pessoa;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "CARGO_ID", nullable = false)
    private Cargo cargo;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_VINCULO", nullable = false, length = 20)
    private TipoVinculo tipoVinculo; // ELEITO, NOMEADO, CONTRATADO, PRO_TEMPORE

    @Column(name = "DATA_INICIO", nullable = true)
    private LocalDate dataInicio;

    @Column(name = "DATA_FIM", nullable = true)
    private LocalDate dataFim;

    @Column(name = "ATIVO")
    private Boolean ativo;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "SUBSTITUIU_ID")
    private MandatoCargo substituiu; // referência à ocupação anterior

    @Enumerated(EnumType.STRING)
    @Column(name = "MOTIVO_SAIDA", length = 30)
    private MotivoSaida motivoSaida;

    @Column(name = "OBSERVACAO")
    private String observacao;
}
