package com.example.demo.repository;

import com.example.demo.model.Identifiable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class FileRepository<T extends Identifiable<ID>, ID> implements Interface<T, ID> {

    protected final Map<ID, T> repositoryMap = new HashMap<>();

    @Override
    public T save(T entity) {
        repositoryMap.put(entity.getId(), entity); // Works universally now!
        return entity;
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(repositoryMap.values());
    }

    @Override
    public T findById(ID id) {
        return repositoryMap.get(id);
    }

    @Override
    public void delete(ID id) {
        repositoryMap.remove(id);
    }
}