package com.example.demo.repository;

import com.example.demo.model.TeachingAssignment;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TeachingAssignmentRepository implements Interface<TeachingAssignment,String>{
    private final Map<String, TeachingAssignment> assignments = new HashMap<>();

    @Override
    public TeachingAssignment save(TeachingAssignment assignment) {
        assignments.put(assignment.getId(), assignment);
        return assignment;
    }
    @Override
    public List<TeachingAssignment> findAll() {
        return new ArrayList<>(assignments.values());
    }
    @Override
    public TeachingAssignment findById(String id) {
        return assignments.get(id);
    }
    @Override
    public void delete(String id) {
        assignments.remove(id);
    }
}