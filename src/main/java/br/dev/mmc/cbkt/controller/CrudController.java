package br.dev.mmc.cbkt.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.dev.mmc.cbkt.config.exceptions.CustomBadRequestException;
import br.dev.mmc.cbkt.service.CrudService;

public abstract class CrudController<T, ID> {

    private final CrudService<T, ID> service;

    protected CrudController(CrudService<T, ID> service) {
        this.service = service;
    }

    @GetMapping("")
    public Iterable<T> getAll() {
        return service.read();
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable ID id) {
        Optional<T> optionalEntity = service.getById(id);
        return optionalEntity.map(entity -> new ResponseEntity<>(entity, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public T create(@RequestBody T entity) {
        return service.create(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable ID id, @RequestBody T entity) {
        service.update(id, entity);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<T> partialUpdate(@PathVariable ID id, @RequestBody T updates) {
        return service.getById(id)
                .map(entity -> {
                    // Apply partial updates to entity here
                    service.update(id, entity);
                    return new ResponseEntity<>(entity, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        try{
            if (!service.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            throw new CustomBadRequestException("Não foi possível remover este registro!");
        }
    }
}