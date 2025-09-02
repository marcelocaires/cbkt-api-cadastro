package br.dev.mmc.cbkt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.mmc.cbkt.domain.Graduacao;

public interface GraduacaoRepository extends JpaRepository<Graduacao, Long> {
}
