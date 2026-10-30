package com.example.demo.repository;

import com.example.demo.model.Enrollment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EnrollmentRepository implements  Interface<Enrollment,String> {
    private final Map<String, Enrollment> enrollments = new HashMap<>();


    @Override
    public Enrollment save(Enrollment entity) {
        enrollments.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<Enrollment> findAll() {
        return new  ArrayList<>(enrollments.values());
    }

    @Override
    public Enrollment findById(String s) {
        return  enrollments.get(s);
    }

    @Override
    public void delete(String s) {
        enrollments.remove(s);
    }
}