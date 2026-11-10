package com.example.demo.repository;

import com.example.demo.model.Enrollment;
import com.example.demo.model.Grade;
import com.example.demo.model.ManagingType;
import com.example.demo.model.TeachingAssignment;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RelationshipRepositoryTests {
    @Test
    void findsEnrollmentsByStudentAndCourse() {
        EnrollmentRepository repository = new EnrollmentRepository();
        repository.save(new Enrollment("e1", "s1", "c1", Grade.A, null));
        repository.save(new Enrollment("e2", "s1", "c2", Grade.B, null));
        repository.save(new Enrollment("e3", "s2", "c1", Grade.C, null));

//        assertEquals(List.of("e1", "e2"), repository.findByStudentId("s1").stream()
//                .map(Enrollment::getId).sorted().toList());
//        assertEquals(List.of("e1", "e3"), repository.findByCourseId("c1").stream()
//                .map(Enrollment::getId).sorted().toList());
    }

    @Test
    void findsAssignmentsByCourseAndStaff() {
        TeachingAssignmentRepository repository = new TeachingAssignmentRepository();
        repository.save(new TeachingAssignment("a1", "c1", "staff1", ManagingType.Lab));
        repository.save(new TeachingAssignment("a2", "c2", "staff1", ManagingType.Seminary));
        repository.save(new TeachingAssignment("a3", "c1", "staff2", ManagingType.Course));

//        assertEquals(List.of("a1", "a3"), repository.findByCourseId("c1").stream()
//                .map(TeachingAssignment::getId).sorted().toList());
//        assertEquals(List.of("a1", "a2"), repository.findByStaffId("staff1").stream()
//                .map(TeachingAssignment::getId).sorted().toList());
    }
}
