package br.dev.mmc.cbkt.service;

import br.dev.mmc.cbkt.domain.Usuario;

public interface JwtIssuer {
    String issueFor(Usuario usuario);
}
