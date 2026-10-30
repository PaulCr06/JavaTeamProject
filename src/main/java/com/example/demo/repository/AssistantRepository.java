package com.example.demo.repository;

import com.example.demo.model.Assistant;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AssistantRepository implements Interface<Assistant,String> {
    private final Map<String, Assistant> assistants = new HashMap<>();


    @Override
    public Assistant save(Assistant entity) {
        assistants.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<Assistant> findAll() {
        return new ArrayList<>(assistants.values());
    }

    @Override
    public Assistant findById(String id) {
        return assistants.get(id);
    }

    @Override
    public void delete(String id) {
        assistants.remove(id);
    }
}