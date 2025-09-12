package br.dev.mmc.cbkt.domain;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import br.dev.mmc.cbkt.domain.enums.StatusUsuarioEnum;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USUARIO", uniqueConstraints = @UniqueConstraint(name="UK_USUARIO_EMAIL", columnNames="EMAIL"),schema ="auth")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Usuario {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false, length=150)
  private String nome;

  @Column(nullable=false, length=180)
  private String email;

  @Column(name="HASH_SENHA", length=100)
  private String senha;

  @Enumerated(EnumType.STRING)
  @Column(nullable=false, length=30)
  @Builder.Default
  private StatusUsuarioEnum status = StatusUsuarioEnum.PENDING_PASSWORD;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name="USUARIO_ROLE", joinColumns=@JoinColumn(name="USUARIO_ID"))
  @Column(name="ROLE", length=50)
  @Builder.Default
  private Set<String> roles = new HashSet<>(Set.of("ROLE_ATLETA"));

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="ATLETA_ID")
  private Atleta atleta;

  private Instant lastLoginAt;
  private Instant passwordChangedAt;

  @Column(nullable=false)
  @Builder.Default
  private Boolean ativo = true;
}
