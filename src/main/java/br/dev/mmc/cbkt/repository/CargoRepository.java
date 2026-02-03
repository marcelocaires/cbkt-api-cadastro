package br.dev.mmc.cbkt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.dev.mmc.cbkt.domain.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
    
    List<Cargo> findByAtivoOrderByHierarquia(Boolean ativo);

    Optional<Cargo> getByNomeAndAtivo(String nome,Boolean ativo);

    List<Cargo> findByAtivo(Boolean ativo);
}
