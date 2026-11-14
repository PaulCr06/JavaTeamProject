package com.example.demo;

import com.example.demo.model.Assistant;
import com.example.demo.model.Roles;
import com.example.demo.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private AssistantRepository assistantRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeachingAssignmentRepository teachingAssignmentRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Test
    void contextLoads() {
        assertNotNull(assistantRepository);
        assertNotNull(courseRepository);
        assertNotNull(departmentRepository);
        assertNotNull(enrollmentRepository);
        assertNotNull(roomRepository);
        assertNotNull(studentRepository);
        assertNotNull(teacherRepository);
        assertNotNull(teachingAssignmentRepository);
        assertNotNull(universityRepository);
    }

    @Test
    void allRepositoriesHaveAtLeast10Items() {
        assertTrue(assistantRepository.findAll().size() >= 10, "Assistant repository should have >= 10 items");
        assertTrue(courseRepository.findAll().size() >= 10, "Course repository should have >= 10 items");
        assertTrue(departmentRepository.findAll().size() >= 10, "Department repository should have >= 10 items");
        assertTrue(enrollmentRepository.findAll().size() >= 10, "Enrollment repository should have >= 10 items");
        assertTrue(roomRepository.findAll().size() >= 10, "Room repository should have >= 10 items");
        assertTrue(studentRepository.findAll().size() >= 10, "Student repository should have >= 10 items");
        assertTrue(teacherRepository.findAll().size() >= 10, "Teacher repository should have >= 10 items");
        assertTrue(teachingAssignmentRepository.findAll().size() >= 10, "TeachingAssignment repository should have >= 10 items");
        assertTrue(universityRepository.findAll().size() >= 10, "University repository should have >= 10 items");
    }

    @Test
    void testIdUniquenessAndValidity() {
        assertThrows(IllegalArgumentException.class, () -> assistantRepository.save(null));

        Assistant invalidAssistant = new Assistant("", "Test", Roles.TA);
        assertThrows(IllegalArgumentException.class, () -> assistantRepository.save(invalidAssistant));

        Assistant existingAssistant = assistantRepository.findAll().get(0);
        Assistant duplicate = new Assistant(existingAssistant.getId(), "Duplicate Name", Roles.Lab);
        assertThrows(IllegalArgumentException.class, () -> assistantRepository.save(duplicate));
    }
}
