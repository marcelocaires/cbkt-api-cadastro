package br.dev.mmc.cbkt.service;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
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
        T target = repository.findById(id).orElseThrow();
        mergeNonNull(source, target);
        return repository.save(target);
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

    public static <T> void mergeNonNull(T source, T target) {
        for (Field field : source.getClass().getDeclaredFields()) {

            if (Modifier.isStatic(field.getModifiers()) ||
                Modifier.isFinal(field.getModifiers()) ||
                field.getName().equals("id")) {
                continue;
            }

            field.setAccessible(true);

            try {
                Object value = field.get(source);

                // IGNORA COLEÇÕES (OneToMany, ManyToMany, etc.)
                if (value != null && Collection.class.isAssignableFrom(field.getType())) {
                    continue;
                }

                if (value != null) {
                    field.set(target, value);
                }

            } catch (IllegalAccessException ignored) {}
        }
    }


}
