package br.dev.mmc.cbkt.service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.dev.mmc.cbkt.domain.Atleta;
import br.dev.mmc.cbkt.domain.LoginAudit;
import br.dev.mmc.cbkt.domain.PasswordToken;
import br.dev.mmc.cbkt.domain.Usuario;
import br.dev.mmc.cbkt.domain.enums.StatusUsuarioEnum;
import br.dev.mmc.cbkt.domain.enums.TokenTypeEnum;
import br.dev.mmc.cbkt.repository.LoginAuditRepository;
import br.dev.mmc.cbkt.repository.PasswordTokenRepository;
import br.dev.mmc.cbkt.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepo;
    private final PasswordTokenRepository tokenRepo;
    private final LoginAuditRepository auditRepo;
    private final PasswordEncoder passwordEncoder;
    private final Mailer mailer;
    private final JwtIssuer jwtIssuer;

    public void provisionarUsuarioParaAtleta(Atleta atleta) {
        if (atleta == null || atleta.getContato().getEmail() == null || atleta.getContato().getEmail().isBlank()) return;
        if (usuarioRepo.findByEmailIgnoreCase(atleta.getContato().getEmail()).isPresent()) return;

        Usuario u = Usuario.builder()
                .nome(atleta.getNomeAtleta())
                .email(atleta.getContato().getEmail())
                .status(StatusUsuarioEnum.PENDING_PASSWORD)
                .atleta(atleta)
                .roles(new HashSet<>(Set.of("ROLE_ATLETA")))
                .ativo(true)
                .build();
        usuarioRepo.save(u);
        enviarToken(u, TokenTypeEnum.ENROLLMENT, Duration.ofHours(72));
    }

    public void solicitarResetSenha(String email, String ip, String ua) {
        usuarioRepo.findByEmailIgnoreCase(email).ifPresent(u -> {
            if (Boolean.TRUE.equals(u.getAtivo())) {
                enviarToken(u, TokenTypeEnum.RESET, Duration.ofMinutes(60));
            }
        });
    }

    public void concluirDefinicaoOuResetSenha(String tokenValue, String novaSenha, String ip, String ua) {
        PasswordToken t = tokenRepo.findByToken(tokenValue)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token inválido"));
        if (!t.isValid()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token expirado ou já utilizado");
        }

        Usuario u = t.getUsuario();
        u.setSenha(passwordEncoder.encode(novaSenha));
        u.setStatus(StatusUsuarioEnum.ACTIVE);
        u.setPasswordChangedAt(Instant.now());
        usuarioRepo.save(u);

        t.setUsedAt(Instant.now());
        t.setIp(ip);
        t.setUserAgent(ua);
        tokenRepo.save(t);
    }

    public String login(String email, String senha, String ip, String ua) {
        Usuario u = usuarioRepo.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new BadCredentialsException("Credenciais inválidas"));

        if (u.getStatus() == StatusUsuarioEnum.PENDING_PASSWORD) {
            throw new LockedException("Defina sua senha a partir do link enviado por e-mail.");
        }
        if (!Boolean.TRUE.equals(u.getAtivo()) || u.getStatus() == StatusUsuarioEnum.LOCKED) {
            throw new LockedException("Usuário inativo/bloqueado.");
        }
        if (!passwordEncoder.matches(senha, Objects.toString(u.getSenha(), ""))) {
            registrarLogin(u, false, "BadCredentials", ip, ua);
            throw new BadCredentialsException("Credenciais inválidas");
        }

        registrarLogin(u, true, null, ip, ua);
        return jwtIssuer.issueFor(u);
    }

    public void registrarLogin(Usuario u, boolean success, String failure, String ip, String ua) {
        if (success) {
            u.setLastLoginAt(Instant.now());
            usuarioRepo.save(u);
        }
        auditRepo.save(LoginAudit.builder()
                .usuario(u)
                .loginAt(Instant.now())
                .success(success)
                .failureReason(failure)
                .ip(ip)
                .userAgent(ua)
                .build());
    }

    private void enviarToken(Usuario u, TokenTypeEnum type, Duration ttl) {
        String token = secureRandomUrlToken(48);
        PasswordToken t = PasswordToken.builder()
                .usuario(u)
                .type(type)
                .token(token)
                .expiresAt(Instant.now().plus(ttl))
                .build();
        tokenRepo.save(t);

        String link = (type == TokenTypeEnum.ENROLLMENT)
                ? "https://app.seu-dominio.com/ativar?token=" + token
                : "https://app.seu-dominio.com/resetar-senha?token=" + token;

        String html = montarHtmlEmail(u.getNome(), link, type);
        mailer.send(u.getEmail(),
                (type == TokenTypeEnum.ENROLLMENT ? "Ative seu acesso" : "Redefinição de senha"),
                html);
    }

    private static String secureRandomUrlToken(int bytes) {
        byte[] b = new byte[bytes];
        new SecureRandom().nextBytes(b);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(b);
    }

    private String montarHtmlEmail(String nome, String link, TokenTypeEnum type) {
        return ("""
                <p>Olá, %s!</p>
                <p>%s sua senha clicando no botão abaixo:</p>
                <p><a href="%s" style="background:#0ea5e9;color:#fff;padding:12px 18px;border-radius:8px;text-decoration:none;">%s</a></p>
                <p>Se você não solicitou, ignore este e-mail.</p>
                """).formatted(
                nome,
                (type == TokenTypeEnum.ENROLLMENT ? "Defina" : "Redefina"),
                link,
                (type == TokenTypeEnum.ENROLLMENT ? "Definir senha" : "Redefinir senha")
        );
    }
}
