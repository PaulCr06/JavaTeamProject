package com.example.demo.repository;

import com.example.demo.model.Enrollment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EnrollmentRepository {
    private final Map<String, Enrollment> enrollments = new HashMap<>();

    public Enrollment save(Enrollment enrollment) {
        enrollments.put(enrollment.getId(), enrollment);
        return enrollment;
    }

    public List<Enrollment> findAll() {
        return new ArrayList<>(enrollments.values());
    }

    public Enrollment findById(String id) {
        return enrollments.get(id);
    }

    public void delete(String id) {
        enrollments.remove(id);
    }
}