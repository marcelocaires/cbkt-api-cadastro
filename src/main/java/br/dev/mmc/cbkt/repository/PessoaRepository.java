package br.dev.mmc.cbkt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.dev.mmc.cbkt.domain.Pessoa;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    
    Optional<Pessoa> findByCpf(String cpf);
    
    Optional<Pessoa> findByNomeAndCpf(String nome, String cpf);
}
