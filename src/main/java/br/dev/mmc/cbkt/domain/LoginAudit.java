package br.dev.mmc.cbkt.domain;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="LOGIN_AUDIT", indexes=@Index(name="IDX_LOGIN_AUDIT_USER", columnList="USUARIO_ID"),schema ="auth")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class LoginAudit {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="USUARIO_ID", nullable=false, foreignKey=@ForeignKey(name="FK_AUDIT_USUARIO"))
  private Usuario usuario;

  @Column(nullable=false) private Instant loginAt;
  @Column(nullable=false) private Boolean success;
  @Column(length=80) private String failureReason;

  @Column(length=50)  private String ip;
  @Column(length=200) private String userAgent;
}
