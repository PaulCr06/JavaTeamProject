package com.example.demo.service;

import com.example.demo.model.Enrollment;
import com.example.demo.model.Student;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentService enrollmentService;

    public StudentService(StudentRepository studentRepository, EnrollmentRepository enrollmentRepository,
                          EnrollmentService enrollmentService) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.enrollmentService = enrollmentService;
    }

    private void validateStudent(Student student) {
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Student cannot be null!");
        }
        if (student.getName() == null || student.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Student name must not be empty!");
        }
        if (student.getEmail() == null || student.getEmail().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Student email must not be empty!");
        }
        if (student.getBirthDate() != null && student.getBirthDate().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Birth date cannot be in the future!");
        }
    }

    public Student saveStudent(Student student) {
        validateStudent(student);
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student) {
        if (student == null || student.getId() == null) {
            return null;
        }
        validateStudent(student);

        Student existing = studentRepository.findById(student.getId());
        if (existing == null) {
            return null;
        }
        student.setEnrollments(existing.getEnrollments());
        return studentRepository.update(student);
    }

    public List<Student> findAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<Enrollment> allEnrollments = enrollmentRepository.findAll();
        for (Student student : students) {
            List<Enrollment> studentEnrollments = new ArrayList<>();
            for (Enrollment enrollment : allEnrollments) {
                if (student.getId() != null && student.getId().equals(enrollment.getStudentId())) {
                    studentEnrollments.add(enrollment);
                }
            }
            student.setEnrollments(studentEnrollments);
        }
        return students;
    }

    public void deleteStudent(String id) {
        List<Enrollment> allEnrollments = enrollmentRepository.findAll();
        for (Enrollment enrollment : allEnrollments) {
            if (id.equals(enrollment.getStudentId())) {
                enrollmentService.deleteEnrollment(enrollment.getId());
            }
        }
        studentRepository.delete(id);
    }

    public Student findStudentById(String id) {
        Student student = studentRepository.findById(id);
        if (student != null) {
            List<Enrollment> studentEnrollments = new ArrayList<>();
            for (Enrollment enrollment : enrollmentRepository.findAll()) {
                if (id.equals(enrollment.getStudentId())) {
                    studentEnrollments.add(enrollment);
                }
            }
            student.setEnrollments(studentEnrollments);
        }
        return student;
    }
}
