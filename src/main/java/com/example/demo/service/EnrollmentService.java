package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Enrollment;
import com.example.demo.model.Grade;
import com.example.demo.model.Student;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
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
        if (enrollment == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Enrollment cannot be null!");
        }
        if (enrollment.getStudentId() == null || enrollment.getStudentId().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Student ID must not be empty!");
        }
        if (enrollment.getCourseId() == null || enrollment.getCourseId().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Course ID must not be empty!");
        }

        Student student = studentRepository.findById(enrollment.getStudentId());
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Student with this ID does not exist!");
        }
        Course course = courseRepository.findById(enrollment.getCourseId());
        if (course == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Course with this ID does not exist!");
        }

        boolean alreadyEnrolled = enrollmentRepository.findAll().stream()
                .anyMatch(e -> enrollment.getStudentId().equals(e.getStudentId())
                        && enrollment.getCourseId().equals(e.getCourseId()));
        if (alreadyEnrolled) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Student is already enrolled in this course!");
        }

        if (enrollment.getGrade() == null) {
            enrollment.setGrade(Grade.NA);
        }
        if (enrollment.getEnrollmentDate() == null) {
            enrollment.setEnrollmentDate(LocalDate.now());
        }

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        if (student.getEnrollments() == null) {
            student.setEnrollments(new ArrayList<>());
        }
        student.getEnrollments().add(savedEnrollment);
        studentRepository.update(student);

        if (course.getEnrollments() == null) {
            course.setEnrollments(new ArrayList<>());
        }
        course.getEnrollments().add(savedEnrollment);
        courseRepository.update(course);

        return savedEnrollment;
    }

    public Enrollment updateEnrollment(Enrollment enrollment) {
        if (enrollment == null || enrollment.getId() == null) {
            return null;
        }

        Enrollment existing = enrollmentRepository.findById(enrollment.getId());
        if (existing == null) {
            return null;
        }

        Student newStudent = studentRepository.findById(enrollment.getStudentId());
        if (newStudent == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Student with this ID does not exist!");
        }
        Course newCourse = courseRepository.findById(enrollment.getCourseId());
        if (newCourse == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Course with this ID does not exist!");
        }

        boolean duplicateOther = enrollmentRepository.findAll().stream()
                .anyMatch(e -> !e.getId().equals(enrollment.getId())
                        && enrollment.getStudentId().equals(e.getStudentId())
                        && enrollment.getCourseId().equals(e.getCourseId()));
        if (duplicateOther) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Student is already enrolled in this course!");
        }

        if (enrollment.getGrade() == null) {
            enrollment.setGrade(existing.getGrade());
        }
        if (enrollment.getEnrollmentDate() == null) {
            enrollment.setEnrollmentDate(existing.getEnrollmentDate());
        }

        Student oldStudent = studentRepository.findById(existing.getStudentId());
        if (oldStudent != null && oldStudent.getEnrollments() != null) {
            oldStudent.getEnrollments().removeIf(e -> e.getId().equals(existing.getId()));
            studentRepository.update(oldStudent);
        }

        Course oldCourse = courseRepository.findById(existing.getCourseId());
        if (oldCourse != null && oldCourse.getEnrollments() != null) {
            oldCourse.getEnrollments().removeIf(e -> e.getId().equals(existing.getId()));
            courseRepository.update(oldCourse);
        }

        Enrollment updatedEnrollment = enrollmentRepository.update(enrollment);

        Student targetStudent = studentRepository.findById(updatedEnrollment.getStudentId());
        if (targetStudent.getEnrollments() == null) {
            targetStudent.setEnrollments(new ArrayList<>());
        }
        targetStudent.getEnrollments().add(updatedEnrollment);
        studentRepository.update(targetStudent);

        Course targetCourse = courseRepository.findById(updatedEnrollment.getCourseId());
        if (targetCourse.getEnrollments() == null) {
            targetCourse.setEnrollments(new ArrayList<>());
        }
        targetCourse.getEnrollments().add(updatedEnrollment);
        courseRepository.update(targetCourse);

        return updatedEnrollment;
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment getEnrollmentById(String id) {
        return enrollmentRepository.findById(id);
    }

    public void deleteEnrollment(String id) {
        Enrollment existing = enrollmentRepository.findById(id);
        if (existing != null) {
            Student student = studentRepository.findById(existing.getStudentId());
            if (student != null && student.getEnrollments() != null) {
                student.getEnrollments().removeIf(e -> e.getId().equals(id));
                studentRepository.update(student);
            }

            Course course = courseRepository.findById(existing.getCourseId());
            if (course != null && course.getEnrollments() != null) {
                course.getEnrollments().removeIf(e -> e.getId().equals(id));
                courseRepository.update(course);
            }
        }
        enrollmentRepository.delete(id);
    }
}
