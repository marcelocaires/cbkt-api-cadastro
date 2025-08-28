package br.dev.mmc.cbkt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudService<E,ID> {
    E create(E entity);
    List<E> createAll(List<E> entity);
    Page<E> read(Pageable pageable);
    List<E> read();
    E update(ID id, E entity);
    void delete(ID id);
    E getOrThrow(ID id);
    Optional<E> getById(ID id);
    boolean existsById(ID id);
}