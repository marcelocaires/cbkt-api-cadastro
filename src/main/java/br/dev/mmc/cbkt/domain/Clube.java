package br.dev.mmc.cbkt.domain;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "CLUBE")
public class Clube {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODIGOCLUBE")
    private Long id;

    // Dados gerais
    @Column(name = "NOMECLUBE", nullable = false, length = 120)
    private String nome;

    @Column(name = "ABREVIATURA", length = 30)
    private String abreviatura;

    @Column(name = "CLASSIFICACAO", length = 30)
    private String classificacao;

    @Column(name = "CNPJ", length = 18)
    private String cnpj;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "DATAFUNDACAO")
    private LocalDate dataFundacao;

    @Embedded
    private Contato contato;

    @Embedded
    private Endereco endereco;

    @Embedded
    private ClubeDiretoria diretoria;

    // Relacionamento com AtletaClube
    @JsonIgnore
    @OneToMany(mappedBy = "clube", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<AtletaClube> atletas = new LinkedHashSet<>();

    // Relacionamento com Mandato
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Mandato> mandatos = new LinkedHashSet<>();

    // Relacionamento com instrutores do clube
    @JsonIgnore
    @OneToMany(mappedBy = "clube", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<ClubeInstrutor> instrutores = new LinkedHashSet<>();

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Transient
    private List<MandatoCargo> diretoriaAtiva;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Transient
    private Mandato getMandatoAtivo;

    public Mandato getMandatoAtivo() {
        if(mandatos==null || mandatos.isEmpty()) {
            return null;
        }
        return mandatos.stream()
            .filter(m->m.getDataFim()==null || m.getDataFim().isAfter(LocalDate.now()))
            .findFirst()
            .orElse(null);
    }

    public List<MandatoCargo> getDiretoriaAtiva() {
        Mandato mandatoAtivo = getMandatoAtivo();
        if(mandatoAtivo==null) {
            return null;
        }
        return mandatoAtivo.getCargos();
    }
}
