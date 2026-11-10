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

    public List<TeachingAssignment> findByCourseId(String courseId) {
        return findAll().stream()
                .filter(assignment -> Objects.equals(courseId, assignment.getCourseId()))
                .toList();
    }

    public List<TeachingAssignment> findByStaffId(String staffId) {
        return findAll().stream()
                .filter(assignment -> Objects.equals(staffId, assignment.getStaffId()))
                .toList();
    }
}