package br.dev.mmc.cbkt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.dev.mmc.cbkt.config.exceptions.ResourceNotFoundException;

@Transactional
public abstract class CrudServiceImpl<T,ID> implements CrudService<T, ID> {

    private final JpaRepository<T, ID> repository;

    public CrudServiceImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    public T create(T entity) { 
        return repository.save(entity);
    }

    public List<T> createAll(List<T> list) {
        return repository.saveAll(list);
    }

    public T update(ID id, T source) {
        return repository.save(source);
    }

    public void delete(ID id) {
        var existing = getOrThrow(id);
        repository.delete(existing);
    }

    public Page<T> read(Pageable pageable) {
        return repository.findAll(pageable);
    }
    @Transactional(readOnly = true)
    public List<T> read() {
        return repository.findAll();
    }
    @Transactional(readOnly = true)
    public Optional<T> getById(ID id) { 
        return repository.findById(id); 
    }
    @Transactional(readOnly = true)
    public T getOrThrow(ID id) {
        return repository.findById(id).orElseThrow(() -> 
            new ResourceNotFoundException("objeto não encontrado"));
    }
    @Transactional(readOnly = true)
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }
}
