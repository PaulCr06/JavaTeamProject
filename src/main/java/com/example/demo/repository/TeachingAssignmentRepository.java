package com.example.demo.repository;

import com.example.demo.model.TeachingAssignment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class TeachingAssignmentRepository extends InMemoryRepository<TeachingAssignment, String> {
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