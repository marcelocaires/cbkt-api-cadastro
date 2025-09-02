package br.dev.mmc.cbkt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.dev.mmc.cbkt.domain.Clube;

public interface ClubeRepository extends BaseRepository<Clube, Long> {
    
    @EntityGraph(
        type = EntityGraph.EntityGraphType.LOAD,
        attributePaths = { "atletas", "atletas.atleta" }
    )
    @Query("select c from Clube c where c.id = :id")
    Optional<Clube> findDetalheById(@Param("id") Long id);

    @EntityGraph(
        type = EntityGraph.EntityGraphType.LOAD,
        attributePaths = { "atletas", "atletas.atleta" }
    )
    @Query("select c from Clube c where c.nome like %:nome%")
    List<Clube> findDetalheByNome(@Param("nome") String nome);

    @EntityGraph(
        type = EntityGraph.EntityGraphType.LOAD,
        attributePaths = { "atletas", "atletas.atleta" }
    )
    @Query("select c from Clube c")
    List<Clube> searchDetalheAll();
}
