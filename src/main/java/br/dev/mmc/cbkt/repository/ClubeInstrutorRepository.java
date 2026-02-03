package br.dev.mmc.cbkt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.mmc.cbkt.domain.ClubeInstrutor;

public interface ClubeInstrutorRepository extends JpaRepository<ClubeInstrutor, Long> {
    List<ClubeInstrutor> findByClubeId(Long clubeId);
    List<ClubeInstrutor> findByClubeIdAndAtivoTrue(Long clubeId);
    List<ClubeInstrutor> findByAtletaIdAndAtivoTrue(Long atletaId);
}
