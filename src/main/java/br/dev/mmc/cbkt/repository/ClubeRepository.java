package br.dev.mmc.cbkt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.dev.mmc.cbkt.domain.Clube;
import jakarta.persistence.criteria.Predicate;

public interface ClubeRepository extends BaseRepository<Clube, Long>, JpaSpecificationExecutor<Clube> {
    
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

    //Especificação para filtro por nome, graduação e clube
    static Specification<Clube> clubeFiltro(String filtro) {
        return (root, cq, cb) -> {
            // Filtro prévio obrigatório: classificacao deve conter "clube"
            Predicate preFiltro = cb.like(cb.lower(root.get("classificacao")), "%clube%");
            if (filtro == null || filtro.isBlank()) {
                //retorna a consulta sem filtros
                return preFiltro;
            }

            String like = "%" + filtro.trim().toLowerCase() + "%";
            Predicate filtroDinamico = cb.or(
                cb.like(cb.lower(root.get("nome")), like),
                cb.like(cb.lower(root.get("abreviatura")), like)
            );

            // Retorna a união obrigatória (preFiltro AND filtroDinamico)
            return cb.and(preFiltro, filtroDinamico);
        };
    }    
}
