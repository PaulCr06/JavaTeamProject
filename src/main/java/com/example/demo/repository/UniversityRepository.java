package com.example.demo.repository;

import com.example.demo.model.University;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UniversityRepository implements Interface<University,String> {

    private final Map<String,University> universities= new HashMap<>();

    @Override
    public University save(University entity) {
        universities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<University> findAll() {
        return new ArrayList<>(universities.values());
    }

    @Override
    public University findById(String id) {
        return universities.get(id);
    }

    @Override
    public void delete(String id) {
        universities.remove(id);

    }
}
