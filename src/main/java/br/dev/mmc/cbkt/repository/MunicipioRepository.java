package br.dev.mmc.cbkt.repository;

import br.dev.mmc.cbkt.domain.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipioRepository extends JpaRepository<Municipio, Long> {
}
