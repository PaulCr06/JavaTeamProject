package com.example.demo.service;

import com.example.demo.model.Enrollment;
import com.example.demo.model.Student;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

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

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student) {
        Student existing = studentRepository.findById(student.getId());
        if (existing == null) {
            return null;
        }
        student.setEnrollments(existing.getEnrollments());
        return studentRepository.update(student);
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
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
