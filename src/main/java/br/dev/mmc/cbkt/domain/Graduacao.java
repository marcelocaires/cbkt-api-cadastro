package br.dev.mmc.cbkt.domain;

import java.math.BigDecimal;

import br.dev.mmc.cbkt.domain.enums.GraduacaoCorEnum;
import br.dev.mmc.cbkt.domain.enums.GraduacaoGrauEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
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
@Table(name = "GRADUACAO")
public class Graduacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "graduacao_seq")
    @SequenceGenerator(name = "graduacao_seq", sequenceName = "graduacao_sequence", allocationSize = 1)
    @Column(name = "CODIGOGRADUACAO")
    private Long id;

    @Column(name = "DESCRICAOGRADUACAO", length = 100)
    private String descricaoGraduacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, length = 10)
    private GraduacaoGrauEnum grau;

    @Setter(AccessLevel.NONE)
    @Transient
    private String grauNome;

    @Enumerated(EnumType.STRING)
    @Column(name = "COR",length = 25)
    private GraduacaoCorEnum cor;

    @Setter(AccessLevel.NONE)
    @Transient
    private String corNome;

    @Setter(AccessLevel.NONE)
    @Transient
    private String corHex;

    @Column(name = "CARENCIA")
    private Integer carencia;

    @Column(name = "CARENCIAMENOR")
    private Integer carenciaMenor;

    @Column(name = "CARENCIA_AULAS")
    private Integer carenciaAulas;

    @Column(name = "VALOR", precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(name = "IDADEMINIMA")
    private Integer idadeMinima;

    @Column(name = "ANUIDADE_ATE")
    private Integer anuidadeAte;

    @Column(name = "ANUIDADE_APOS")
    private Integer anuidadeApos;

    @Column(name = "ANUIDADE", length = 20)
    private String anuidade;

    public String getGrauNome() {
        return grau != null ? grau.getTitulo() : null;
    }

    public String getCorNome() {
        return cor != null ? cor.getNome() : null;
    }

    public String getCorHex() {
        return cor != null ? cor.getHex() : null;
    }
}
