package br.dev.mmc.cbkt.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.dev.mmc.cbkt.domain.Atleta;
import br.dev.mmc.cbkt.domain.AtletaClube;
import br.dev.mmc.cbkt.domain.AtletaGraduacao;

public interface AtletaRepository extends JpaRepository<Atleta, Long>, JpaSpecificationExecutor<Atleta> {
    Optional<Atleta> findByNomeAtleta(String nome);

    @Query("SELECT a FROM Atleta a WHERE a.nomeAtleta LIKE %:nome%")
    List<Atleta> searchByNome(String nome);


    // Carrega o atleta + histórico de graduações (Atleta.graduacoes -> AtletaGraduacao.graduacao)
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,attributePaths = {"graduacoes","graduacoes.graduacao"})
    @Query("select a from Atleta a where a.id = :id")
    Optional<Atleta> findAtletaComGraduacoes(@Param("id") Long id);

    // Carrega o atleta + histórico de clubes (Atleta.clubes -> AtletaClube.clube)
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,attributePaths = {"clubes","clubes.clube"})
    @Query("select a from Atleta a where a.id = :id")
    Optional<Atleta> findAtletaComClubes(@Param("id") Long id);

    @EntityGraph(
        type = EntityGraph.EntityGraphType.LOAD,
        attributePaths = {
            "clubes",
            "clubes.clube",
            "graduacoes",
            "graduacoes.graduacao"
        }
    )
    @Query("select a from Atleta a where a.id = :id")
    Page<Atleta> getAtletasComClubesEGraduacoes(Specification<Atleta> spec, Pageable pageable);

    // Consulta paginada com filtros e o mesmo grafo (sem fetch join)
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"graduacoes", "graduacoes.graduacao"})
    @Query(
    """
        select distinct a
        from Atleta a
        where (:nome is not null and upper(a.nomeAtleta) like upper(concat('%', :nome, '%')))
           or (:cpf  is not null and a.documentos.cpf = :cpf)
    """)
    Page<Atleta> findGraduacoesByFiltro(
        @Param("nome") String nome,
        @Param("cpf") String cpf,
        Pageable pageable
    );

    // Consulta paginada com filtros e o mesmo grafo (sem fetch join)
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"graduacoes", "graduacoes.graduacao"})
    @Query(
    """
        select distinct a
        from Atleta a
        where (:nome is not null and upper(a.nomeAtleta) like upper(concat('%', :nome, '%')))
    """)
    Page<Atleta> findPageByFiltroNome(
        @Param("nome") String nome,
        Pageable pageable
    );

    @Query("""
        select distinct a
          from Atleta a
         where (:nome is not null and a.nomeAtleta like :nome)
           or (:cpf  is not null and a.documentos.cpf = :cpf)
    """)
    List<Atleta> findGraduacoesByFiltro(
        @Param("nome") String nome,
        @Param("cpf") String cpf
    );

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"graduacoes", "graduacoes.graduacao"})
    @Query("""
        select distinct a
          from Atleta a
         where a.dataNascimento = :dtNascimento
           and a.contato.email = :email
           and a.documentos.cpf = :cpf
        """)
    Optional<Atleta> findAtleta(
        @Param("dtNascimento") Date dtNascimento,
        @Param("cpf") String cpf,
        @Param("email") String email
    );

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"graduacoes", "graduacoes.graduacao"})
    @Query("""
        select distinct a
          from Atleta a
         where a.id=:id
        """)
    Optional<Atleta> getAtletaById(@Param("id") Long id);

    //Especificação para filtro por nome, graduação e clube
    static Specification<Atleta> filtroByNGC(String filtro) {
        return (root, cq, cb) -> {
            if (filtro == null || filtro.isBlank()) {
                return cb.conjunction(); // sem filtro
            }

            String like = "%" + filtro.trim().toLowerCase() + "%";

            // join da graduação (se for entidade)
            var graduacaoJoin = root.join("graduacao", jakarta.persistence.criteria.JoinType.LEFT);
            return cb.or(
                cb.like(cb.lower(root.get("nomeAtleta")), like),
                cb.like(cb.lower(root.get("nomeClube")), like),
                cb.like(cb.lower(graduacaoJoin.get("descricaoGraduacao")), like) // ajuste aqui se o campo da graduação for outro
            );
        };
    }
}
