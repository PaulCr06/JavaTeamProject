package com.example.demo.service;

import com.example.demo.model.TeachingAssignment;
import com.example.demo.repository.TeachingAssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeachingAssignmentService {
    private final TeachingAssignmentRepository assignmentRepository;

    public TeachingAssignmentService(TeachingAssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public TeachingAssignment saveAssignment(TeachingAssignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public List<TeachingAssignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public List<TeachingAssignment> getAssignmentsByCourseId(String courseId) {
        return assignmentRepository.findByCourseId(courseId);
    }

    public List<TeachingAssignment> getAssignmentsByStaffId(String staffId) {
        return assignmentRepository.findByStaffId(staffId);
    }

    public TeachingAssignment getAssignmentById(String id) {
        return assignmentRepository.findById(id);
    }

    public void deleteAssignment(String id) {
        assignmentRepository.delete(id);
    }
}