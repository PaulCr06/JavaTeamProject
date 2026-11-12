package com.example.demo.service;

import com.example.demo.model.Enrollment;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.CourseRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
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
        if(studentRepository.findById(enrollment.getStudentId()) == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Eroare: Studentul cu acest ID nu exista");
        }
        if (courseRepository.findById(enrollment.getCourseId()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Eroare: Cursul cu acest ID nu există!");
        }
        return enrollmentRepository.save(enrollment);
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