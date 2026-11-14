package com.example.demo.service;

import com.example.demo.model.Enrollment;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public Enrollment saveEnrollment(Enrollment enrollment) {
        if (studentRepository.findById(enrollment.getStudentId()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Student with this ID does not exist!");
        }
        if (courseRepository.findById(enrollment.getCourseId()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Course with this ID does not exist!");
        }
        return enrollmentRepository.save(enrollment);
    }

    public Enrollment updateEnrollment(Enrollment enrollment) {
        if (studentRepository.findById(enrollment.getStudentId()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Student with this ID does not exist!");
        }
        if (courseRepository.findById(enrollment.getCourseId()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Course with this ID does not exist!");
        }
        Enrollment existing = enrollmentRepository.findById(enrollment.getId());
        if (existing == null) {
            return null;
        }
        return enrollmentRepository.update(enrollment);
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment getEnrollmentById(String id) {
        return enrollmentRepository.findById(id);
    }

    public void deleteEnrollment(String id) {
        enrollmentRepository.delete(id);
    }
}
