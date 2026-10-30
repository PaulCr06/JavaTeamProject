package com.example.demo.repository;

import com.example.demo.model.Department;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentRepository implements Interface<Department,String> {
    private final Map<String, Department> departments = new HashMap<>();
    @Override
    public Department save(Department entity) {
        departments.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<Department> findAll() {
        return new ArrayList<>(departments.values());
    }

    @Override
    public Department findById(String s) {
        return departments.get(s);
    }

    @Override
    public void delete(String s) {
        departments.remove(s);
    }
}
