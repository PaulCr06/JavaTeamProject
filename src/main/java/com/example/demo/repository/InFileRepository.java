package com.example.demo.repository;

import com.example.demo.model.Identifiable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class InFileRepository<T extends Identifiable<ID>, ID> implements CrudRepository<T, ID>{
    protected final Map<ID, T> repositoryMap = new HashMap<>();

    private final String filePath;
    private final Class<T> entityClass;
    private final ObjectMapper objectMapper;

    public InFileRepository(String filePath, Class<T> entityClass){
        this.filePath = filePath;
        this.entityClass = entityClass;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());

        loadFromFile();
    }

    private void loadFromFile(){
        File file = new File(filePath);
        if(!file.exists()){
            return;
        }
        try{
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, entityClass);
            List<T> entities = objectMapper.readValue(file, listType);
            for(T entity : entities){
                repositoryMap.put(entity.getId(), entity);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, repositoryMap.values());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T save(T entity) {
        repositoryMap.put(entity.getId(), entity);
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
