package com.example.demo.repository;

import com.example.demo.model.TeachingAssignment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class TeachingAssignmentRepository extends InFileRepository<TeachingAssignment, String> {
    public TeachingAssignmentRepository() {
        super("src/main/resources/data/teachingassignment.json", TeachingAssignment.class);
    }
}