package br.dev.mmc.cbkt.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.dev.mmc.cbkt.domain.Atleta;

public interface AtletaRepository extends JpaRepository<Atleta, Long> {
    Optional<Atleta> findByNomeAtleta(String nome);

    @Query("SELECT a FROM Atleta a WHERE a.nomeAtleta LIKE %:nome%")
    List<Atleta> searchByNome(String nome);


    // Carrega o atleta + histórico de graduações (Atleta.graduacoes -> AtletaGraduacao.graduacao)
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,attributePaths = {"graduacoes", "graduacoes.graduacao"})
    @Query("select a from Atleta a where a.id = :id")
    Optional<Atleta> findDetalheComGraduacoes(@Param("id") Long id);

    // Consulta paginada com filtros e o mesmo grafo (sem fetch join)
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"graduacoes", "graduacoes.graduacao"})
    @Query("""
        select distinct a
          from Atleta a
         where (:nome is null or upper(a.nomeAtleta) like upper(concat('%', :nome, '%')))
           and (:cpf  is null or a.documentos.cpf = :cpf)
        """)
    Page<Atleta> findGraduacoesByFiltro(
        @Param("nome") String nome,
        @Param("cpf") String cpf,
        Pageable pageable
    );

        // Consulta paginada com filtros e o mesmo grafo (sem fetch join)
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"graduacoes", "graduacoes.graduacao"})
    @Query("""
        select distinct a
          from Atleta a
         where (:nome is null or upper(a.nomeAtleta) like upper(concat('%', :nome, '%')))
           and (:cpf  is null or a.documentos.cpf = :cpf)
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
}
