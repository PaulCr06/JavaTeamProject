package com.example.demo.repository;

import com.example.demo.model.Identifiable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class InFileRepository<T extends Identifiable<ID>, ID> implements CrudRepository<T, ID> {
    protected final Map<ID, T> repositoryMap = new HashMap<>();

    private final String filePath;
    private final Class<T> entityClass;
    private final ObjectMapper objectMapper;

    public InFileRepository(String filePath, Class<T> entityClass) {
        this.filePath = filePath;
        this.entityClass = entityClass;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);//pretty-print

        loadFromFile();
    }

    protected void validateId(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }
        if (id instanceof String str && str.trim().isEmpty()) {
            throw new IllegalArgumentException("ID must not be empty.");
        }
    }

    private void loadFromFile() {
        repositoryMap.clear();

        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }

        try {
            List<T> list = objectMapper.readerForListOf(entityClass).readValue(file);
            for (T item : list) {
                repositoryMap.put(item.getId(), item);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Could not load file: " + filePath, e);
        }
    }

    private void saveToFile() {
        File file = new File(filePath);
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            objectMapper.writeValue(file, repositoryMap.values());
        } catch (IOException e) {
            throw new UncheckedIOException("Could not save to file: " + filePath, e);
        }
    }

    @Override
    public T save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must not be null.");
        }
        validateId(entity.getId());

        loadFromFile();
        if (repositoryMap.containsKey(entity.getId())) {
            throw new IllegalArgumentException("Entity with ID '" + entity.getId() + "' already exists.");
        }

        repositoryMap.put(entity.getId(), entity);
        saveToFile();
        return entity;
    }

    @Override
    public T update(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must not be null.");
        }
        validateId(entity.getId());

        loadFromFile();
        if (!repositoryMap.containsKey(entity.getId())) {
            return null;
        }

        repositoryMap.put(entity.getId(), entity);
        saveToFile();
        return entity;
    }

    @Override
    public List<T> findAll() {
        loadFromFile();
        return new ArrayList<>(repositoryMap.values());
    }

    @Override
    public T findById(ID id) {
        if (id == null || (id instanceof String str && str.trim().isEmpty())) {
            return null;
        }
        loadFromFile();
        return repositoryMap.get(id);
    }

    @Override
    public void delete(ID id) {
        if (id == null || (id instanceof String str && str.trim().isEmpty())) {
            return;
        }
        loadFromFile();
        repositoryMap.remove(id);
        saveToFile();
    }
}
