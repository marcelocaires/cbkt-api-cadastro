package br.dev.mmc.cbkt.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import br.dev.mmc.cbkt.domain.enums.TipoEntidade;
import br.dev.mmc.cbkt.domain.support.AuditoriaContext;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
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
@Table(name = "MANDATO")
public class Mandato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_ENTIDADE", nullable = false, length = 20)
    private TipoEntidade tipoEntidade;

    @Column(name = "ENTIDADE_ID", nullable = false)
    private Long entidadeId;

    @Column(name = "DATA_INICIO", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "DATA_FIM")
    private LocalDate dataFim;

    @Column(name = "ATIVO")
    @Builder.Default
    private Boolean ativo = true;

    @Column(name = "DESCRICAO", length = 100)
    private String descricao;

    // Auditoria de tempo
    @Column(name = "CRIADO_EM", nullable = false)
    private LocalDateTime criadoEm;

    @Column(name = "ATUALIZADO_EM")
    private LocalDateTime atualizadoEm;

    // Auditoria de autor (snapshot, sem FK)
    @Column(name = "CRIADO_POR_CPF", length = 11, nullable = false)
    private String criadoPorCpf;

    @Column(name = "CRIADO_POR_NOME", length = 120, nullable = false)
    private String criadoPorNome;

    @Column(name = "ATUALIZADO_POR_CPF", length = 11)
    private String atualizadoPorCpf;

    @Column(name = "ATUALIZADO_POR_NOME", length = 120)
    private String atualizadoPorNome;

    @OneToMany(
        mappedBy = "mandato", 
        orphanRemoval = true, 
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER
    )
    private List<MandatoCargo> cargos;

    // Concorrência otimista (opcional)
    @Version
    @Column(name = "VERSAO")
    private Long versao;

    @PrePersist
    void onCreate() {
        LocalDateTime nowUtc = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        this.criadoEm = nowUtc;
        this.atualizadoEm = nowUtc;
        String cpfAtual = AuditoriaContext.getCpfAtual();
        String nomeAtual = AuditoriaContext.getNomeAtual();
        this.criadoPorCpf = cpfAtual;
        this.criadoPorNome = nomeAtual;
        this.atualizadoPorCpf = cpfAtual;
        this.atualizadoPorNome = nomeAtual;
    }

    @PreUpdate
    void onUpdate() {
        this.atualizadoEm = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        this.atualizadoPorCpf = AuditoriaContext.getCpfAtual();
        this.atualizadoPorNome = AuditoriaContext.getNomeAtual();
    }

    public boolean foiAlterado() {
        return atualizadoEm != null && criadoEm != null && atualizadoEm.isAfter(criadoEm);
    }
}
