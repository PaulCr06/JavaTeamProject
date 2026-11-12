package com.example.demo.service;

import com.example.demo.model.TeachingAssignment;
import com.example.demo.repository.TeachingAssignmentRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.repository.AssistantRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TeachingAssignmentService {
    private final TeachingAssignmentRepository assignmentRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final AssistantRepository assistantRepository;

    public TeachingAssignmentService(TeachingAssignmentRepository assignmentRepository, CourseRepository courseRepository, TeacherRepository teacherRepository, AssistantRepository assistantRepository) {
        this.assignmentRepository = assignmentRepository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.assistantRepository = assistantRepository;
    }

    public TeachingAssignment saveAssignment(TeachingAssignment assignment) {
        if (courseRepository.findById(assignment.getCourseId()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Eroare: Cursul cu acest ID nu există!");
        }
        boolean teacherExists = teacherRepository.findById(assignment.getStaffId()) != null;
        boolean assistantExists = assistantRepository.findById(assignment.getStaffId()) != null;

        if (!teacherExists && !assistantExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Eroare: Profesorul sau Asistentul nu există!");
        }

        return assignmentRepository.save(assignment);
    }

    public List<TeachingAssignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public TeachingAssignment getAssignmentById(String id) {
        return assignmentRepository.findById(id);
    }

    public void deleteAssignment(String id) {
        assignmentRepository.delete(id);
    }
}