package br.dev.mmc.cbkt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.dev.mmc.cbkt.domain.Mandato;
import br.dev.mmc.cbkt.domain.enums.TipoEntidade;
import java.util.List;
import java.util.Optional;

@Repository
public interface MandatoRepository extends JpaRepository<Mandato, Long> {
    
    List<Mandato> findByTipoEntidadeAndEntidadeId(TipoEntidade tipoEntidade, Long entidadeId);
    
    Optional<Mandato> findByTipoEntidadeAndEntidadeIdAndAtivo(TipoEntidade tipoEntidade, Long entidadeId, Boolean ativo);
    
    List<Mandato> findByTipoEntidadeAndEntidadeIdOrderByDataInicioDesc(TipoEntidade tipoEntidade, Long entidadeId);
}
