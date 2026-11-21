package com.example.demo.repository;

import com.example.demo.model.Identifiable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class InMemoryRepository<T extends Identifiable<ID>, ID> implements CrudRepository<T, ID> {

    protected final Map<ID, T> repositoryMap = new HashMap<>();
    private final String idPrefix;

    public InMemoryRepository() {
        this("");
    }

    public InMemoryRepository(String idPrefix) {
        this.idPrefix = idPrefix != null ? idPrefix : "";
    }

    protected void validateId(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }
        if (id instanceof String str && str.trim().isEmpty()) {
            throw new IllegalArgumentException("ID must not be empty.");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ID generateNextId() {
        long max = 0;
        for (ID id : repositoryMap.keySet()) {
            if (id == null) continue;
            String str = id.toString().trim();
            if (!idPrefix.isEmpty() && str.toLowerCase().startsWith(idPrefix.toLowerCase())) {
                String numPart = str.substring(idPrefix.length());
                try {
                    long val = Long.parseLong(numPart);
                    if (val > max) max = val;
                } catch (NumberFormatException ignored) {}
            }
            try {
                long val = Long.parseLong(str);
                if (val > max) max = val;
            } catch (NumberFormatException ignored) {}
        }
        long next = max + 1;
        while (true) {
            String candidateStr = idPrefix + next;
            ID candidate = (ID) candidateStr;
            if (!repositoryMap.containsKey(candidate)) {
                return candidate;
            }
            next++;
        }
    }

    @Override
    public T save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must not be null.");
        }
        if (entity.getId() == null || (entity.getId() instanceof String str && str.trim().isEmpty())) {
            entity.setId(generateNextId());
        } else {
            validateId(entity.getId());
            if (repositoryMap.containsKey(entity.getId())) {
                throw new IllegalArgumentException("Entity with ID '" + entity.getId() + "' already exists.");
            }
        }
        repositoryMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(repositoryMap.values());
    }

    @Override
    public T findById(ID id) {
        if (id == null || (id instanceof String str && str.trim().isEmpty())) {
            return null;
        }
        return repositoryMap.get(id);
    }

    @Override
    public void delete(ID id) {
        if (id == null || (id instanceof String str && str.trim().isEmpty())) {
            return;
        }
        repositoryMap.remove(id);
    }

    @Override
    public T update(T entity) {
        if (entity == null || entity.getId() == null || !repositoryMap.containsKey(entity.getId())) {
            return null;
        }
        repositoryMap.put(entity.getId(), entity);
        return entity;
    }
}
