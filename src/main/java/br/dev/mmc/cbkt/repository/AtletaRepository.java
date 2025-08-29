package br.dev.mmc.cbkt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.dev.mmc.cbkt.domain.Atleta;

public interface AtletaRepository extends JpaRepository<Atleta, Long> {
    boolean existsByCpf(String cpf);
    Optional<Atleta> findByCpf(String cpf);

    Optional<Atleta> findByCpfAtleta(String cpf);

    Optional<Atleta> findByNomeAtleta(String nome);

    @Query("SELECT a FROM Atleta a WHERE a.nomeAtleta LIKE %:nome%")
    List<Atleta> searchByNome(String nome);
}
