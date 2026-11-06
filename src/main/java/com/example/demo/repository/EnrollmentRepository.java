package com.example.demo.repository;

import com.example.demo.model.Enrollment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class EnrollmentRepository extends InMemoryRepository<Enrollment, String> {
    public List<Enrollment> findByStudentId(String studentId) {
        return findAll().stream()
                .filter(enrollment -> Objects.equals(studentId, enrollment.getStudentId()))
                .toList();
    }

    public List<Enrollment> findByCourseId(String courseId) {
        return findAll().stream()
                .filter(enrollment -> Objects.equals(courseId, enrollment.getCourseId()))
                .toList();
    }
}