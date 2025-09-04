package br.dev.mmc.cbkt.repository;

import br.dev.mmc.cbkt.domain.LoginAudit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginAuditRepository extends JpaRepository<LoginAudit, Long> {
    Page<LoginAudit> findByUsuarioIdOrderByLoginAtDesc(Long usuarioId, Pageable pageable);
}
