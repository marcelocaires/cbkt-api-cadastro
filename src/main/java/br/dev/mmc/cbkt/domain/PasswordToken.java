package br.dev.mmc.cbkt.domain;

import java.time.Instant;

import br.dev.mmc.cbkt.domain.enums.TokenTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="PASSWORD_TOKEN", indexes = @Index(name="IDX_TOKEN", columnList="TOKEN", unique=true))
@Getter 
@Setter 
@Builder 
@NoArgsConstructor 
@AllArgsConstructor
public class PasswordToken {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="USUARIO_ID", nullable=false, foreignKey=@ForeignKey(name="FK_TOKEN_USUARIO"))
  private Usuario usuario;

  @Column(nullable=false, length=120)
  private String token;

  @Enumerated(EnumType.STRING)
  @Column(nullable=false, length=20)
  private TokenTypeEnum type;

  @Column(nullable=false)
  private Instant expiresAt;

  private Instant usedAt;
  @Column(length=50)  private String ip;
  @Column(length=200) private String userAgent;

  @Transient
  public boolean isValid() {
    return usedAt == null && Instant.now().isBefore(expiresAt);
  }
}
