package br.dev.mmc.cbkt.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.mmc.cbkt.controller.forms.EmailForm;
import br.dev.mmc.cbkt.controller.forms.LoginForm;
import br.dev.mmc.cbkt.controller.forms.TokenPasswordForm;
import br.dev.mmc.cbkt.controller.responses.JwtResponse;
import br.dev.mmc.cbkt.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginForm req, HttpServletRequest http) {
        String jwt = authService.login(
                req.email(),
                req.senha(),
                clientIp(http),
                http.getHeader("User-Agent")
        );
        return new JwtResponse(jwt);
    }

    @PostMapping("/enroll")
    public void concluir(@RequestBody TokenPasswordForm dto, HttpServletRequest http) {
        authService.concluirDefinicaoOuResetSenha(
                dto.token(),
                dto.novaSenha(),
                clientIp(http),
                http.getHeader("User-Agent")
        );
    }

    @PostMapping("/forgot-password")
    public void forgot(@RequestBody EmailForm dto, HttpServletRequest http) {
        authService.solicitarResetSenha(
                dto.email(),
                clientIp(http),
                http.getHeader("User-Agent")
        );
    }

    @PostMapping("/reset-password")
    public void reset(@RequestBody TokenPasswordForm dto, HttpServletRequest http) {
        authService.concluirDefinicaoOuResetSenha(
                dto.token(),
                dto.novaSenha(),
                clientIp(http),
                http.getHeader("User-Agent")
        );
    }

    private static String clientIp(HttpServletRequest req) {
        String h = req.getHeader("X-Forwarded-For");
        return (h != null && !h.isBlank()) ? h.split(",")[0].trim() : req.getRemoteAddr();
    }
}
