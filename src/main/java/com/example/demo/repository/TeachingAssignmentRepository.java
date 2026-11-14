package com.example.demo.repository;

import com.example.demo.model.TeachingAssignment;
import org.springframework.stereotype.Repository;

@Repository
public class TeachingAssignmentRepository extends InFileRepository<TeachingAssignment, String> {
    public TeachingAssignmentRepository() {
        super("src/main/resources/data/teachingassignment.json", TeachingAssignment.class);
    }
}
