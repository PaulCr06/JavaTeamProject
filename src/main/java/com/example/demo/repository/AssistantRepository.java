package com.example.demo.repository;

import com.example.demo.model.Assistant;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AssistantRepository {
    private final Map<String, Assistant> assistants = new HashMap<>();

    public Assistant save(Assistant assistant) {
        assistants.put(assistant.getId(), assistant);
        return assistant;
    }

    public List<Assistant> findAll() {
        return new ArrayList<>(assistants.values());
    }

    public Assistant findById(String id) {
        return assistants.get(id);
    }

    public void delete(String id) {
        assistants.remove(id);
    }
}