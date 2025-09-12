package br.dev.mmc.cbkt.service;

import java.util.Objects;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.dev.mmc.cbkt.domain.Usuario;
import br.dev.mmc.cbkt.domain.enums.StatusUsuarioEnum;
import br.dev.mmc.cbkt.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = repo.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        var auths = u.getRoles().stream().map(SimpleGrantedAuthority::new).toList();

        return User.withUsername(u.getEmail())
            .password(Objects.toString(u.getSenha(), ""))
            .authorities(auths)
            .accountLocked(u.getStatus() == StatusUsuarioEnum.LOCKED || !u.getAtivo())
            .disabled(!u.getAtivo())
            .build();
    }
}
