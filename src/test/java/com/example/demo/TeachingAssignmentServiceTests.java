package com.example.demo;

import com.example.demo.model.Course;
import com.example.demo.model.Teacher;
import com.example.demo.model.TeachingAssignment;
import com.example.demo.model.Assistant;
import com.example.demo.repository.AssistantRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.repository.TeachingAssignmentRepository;
import com.example.demo.service.TeachingAssignmentService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TeachingAssignmentServiceTests {

    @Test
    void saveAddsAssignmentToCourseAndStaffLists() {
        AssignmentRepository assignmentRepository = new AssignmentRepository();
        CoursesRepository courseRepository = new CoursesRepository();
        TeachersRepository teacherRepository = new TeachersRepository();
        AssistantRepository assistantRepository = new FakeAssistantRepository();
        TeachingAssignmentService service = new TeachingAssignmentService(
                assignmentRepository, courseRepository, teacherRepository, assistantRepository);

        Course course = new Course();
        course.setId("course");
        course.setAssignments(null);
        courseRepository.put(course);
        Teacher teacher = new Teacher("teacher", "Test", null, null, null, null);
        teacher.setAssignments(null);
        teacherRepository.put(teacher);
        TeachingAssignment assignment = new TeachingAssignment();
        assignment.setId("assignment");
        assignment.setCourseId("course");
        assignment.setStaffId("teacher");

        assertEquals(assignment, service.saveAssignment(assignment));

        assertEquals(assignment, course.getAssignments().get(0));
        assertEquals(assignment, teacher.getAssignments().get(0));
        assertEquals(1, courseRepository.updateCountFor("course"));
        assertEquals(1, teacherRepository.updateCountFor("teacher"));
    }

    @Test
    void updateAndDeleteMoveAndRemoveAssignmentFromLinkedParents() {
        AssignmentRepository assignmentRepository = new AssignmentRepository();
        CoursesRepository courseRepository = new CoursesRepository();
        TeachersRepository teacherRepository = new TeachersRepository();
        AssistantRepository assistantRepository = new FakeAssistantRepository();
        TeachingAssignmentService service = new TeachingAssignmentService(
                assignmentRepository, courseRepository, teacherRepository, assistantRepository);

        TeachingAssignment existing = new TeachingAssignment("assignment", "old-course", "old-teacher", null);
        TeachingAssignment updated = new TeachingAssignment("assignment", "new-course", "new-teacher", null);
        Course oldCourse = new Course();
        oldCourse.setId("old-course");
        Course newCourse = new Course();
        newCourse.setId("new-course");
        oldCourse.setAssignments(new ArrayList<>(java.util.List.of(existing)));
        courseRepository.put(oldCourse);
        courseRepository.put(newCourse);
        Teacher oldTeacher = new Teacher("old-teacher", "Old", null, null, null, null);
        Teacher newTeacher = new Teacher("new-teacher", "New", null, null, null, null);
        oldTeacher.setAssignments(new ArrayList<>(java.util.List.of(existing)));
        teacherRepository.put(oldTeacher);
        teacherRepository.put(newTeacher);
        assignmentRepository.put(existing);

        assertEquals(updated, service.updateAssignment(updated));
        assertEquals(0, oldCourse.getAssignments().size());
        assertEquals(updated, newCourse.getAssignments().get(0));
        assertEquals(0, oldTeacher.getAssignments().size());
        assertEquals(updated, newTeacher.getAssignments().get(0));

        service.deleteAssignment("assignment");
        assertEquals(0, newCourse.getAssignments().size());
        assertEquals(0, newTeacher.getAssignments().size());
        assertEquals(1, courseRepository.updateCountFor("old-course"));
        assertEquals(2, courseRepository.updateCountFor("new-course"));
        assertEquals(1, teacherRepository.updateCountFor("old-teacher"));
        assertEquals(2, teacherRepository.updateCountFor("new-teacher"));
        assertNull(assignmentRepository.findById("assignment"));
    }

    private static class AssignmentRepository extends TeachingAssignmentRepository {
        private final Map<String, TeachingAssignment> values = new HashMap<>();

        void put(TeachingAssignment assignment) {
            values.put(assignment.getId(), assignment);
        }

        @Override
        public TeachingAssignment save(TeachingAssignment assignment) {
            put(assignment);
            return assignment;
        }

        @Override
        public TeachingAssignment update(TeachingAssignment assignment) {
            put(assignment);
            return assignment;
        }

        @Override
        public TeachingAssignment findById(String id) {
            return values.get(id);
        }

        @Override
        public void delete(String id) {
            values.remove(id);
        }
    }

    private static class CoursesRepository extends CourseRepository {
        private final Map<String, Course> values = new HashMap<>();
        private final Map<String, Integer> updateCounts = new HashMap<>();

        void put(Course course) {
            values.put(course.getId(), course);
        }

        int updateCountFor(String id) {
            return updateCounts.getOrDefault(id, 0);
        }

        @Override
        public Course update(Course course) {
            put(course);
            updateCounts.merge(course.getId(), 1, Integer::sum);
            return course;
        }

        @Override
        public Course findById(String id) {
            return values.get(id);
        }
    }

    private static class TeachersRepository extends TeacherRepository {
        private final Map<String, Teacher> values = new HashMap<>();
        private final Map<String, Integer> updateCounts = new HashMap<>();

        void put(Teacher teacher) {
            values.put(teacher.getId(), teacher);
        }

        int updateCountFor(String id) {
            return updateCounts.getOrDefault(id, 0);
        }

        @Override
        public Teacher update(Teacher teacher) {
            put(teacher);
            updateCounts.merge(teacher.getId(), 1, Integer::sum);
            return teacher;
        }

        @Override
        public Teacher findById(String id) {
            return values.get(id);
        }
    }

    private static class FakeAssistantRepository extends AssistantRepository {
        @Override
        public Assistant findById(String id) {
            return null;
        }
    }
}
