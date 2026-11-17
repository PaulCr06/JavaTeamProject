package com.example.demo.service;

import com.example.demo.model.Assistant;
import com.example.demo.model.Course;
import com.example.demo.model.TeachingAssignment;
import com.example.demo.model.Teacher;
import com.example.demo.repository.AssistantRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.repository.TeachingAssignmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeachingAssignmentService {
    private final TeachingAssignmentRepository assignmentRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final AssistantRepository assistantRepository;

    public TeachingAssignmentService(TeachingAssignmentRepository assignmentRepository, CourseRepository courseRepository,
                                     TeacherRepository teacherRepository, AssistantRepository assistantRepository) {
        this.assignmentRepository = assignmentRepository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.assistantRepository = assistantRepository;
    }

    public TeachingAssignment saveAssignment(TeachingAssignment assignment) {
        Course course = courseRepository.findById(assignment.getCourseId());
        if (course == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Course with this ID does not exist!");
        }
        if (teacherRepository.findById(assignment.getStaffId()) == null
                && assistantRepository.findById(assignment.getStaffId()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Teacher or Assistant with this ID does not exist!");
        }

        TeachingAssignment savedAssignment = assignmentRepository.save(assignment);
        addToCourse(course, savedAssignment);
        courseRepository.update(course);
        addToStaff(savedAssignment.getStaffId(), savedAssignment);
        return savedAssignment;
    }

    public TeachingAssignment updateAssignment(TeachingAssignment assignment) {
        TeachingAssignment existing = assignmentRepository.findById(assignment.getId());
        if (existing == null) {
            return null;
        }

        Course targetCourse = courseRepository.findById(assignment.getCourseId());
        if (targetCourse == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Course with this ID does not exist!");
        }
        if (teacherRepository.findById(assignment.getStaffId()) == null
                && assistantRepository.findById(assignment.getStaffId()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Teacher or Assistant with this ID does not exist!");
        }

        Course oldCourse = courseRepository.findById(existing.getCourseId());
        if (oldCourse != null && oldCourse.getAssignments() != null) {
            oldCourse.getAssignments().removeIf(a -> a.getId().equals(existing.getId()));
            courseRepository.update(oldCourse);
        }
        removeFromStaff(existing.getStaffId(), existing.getId());

        TeachingAssignment updatedAssignment = assignmentRepository.update(assignment);
        targetCourse = courseRepository.findById(updatedAssignment.getCourseId());
        addToCourse(targetCourse, updatedAssignment);
        courseRepository.update(targetCourse);
        addToStaff(updatedAssignment.getStaffId(), updatedAssignment);
        return updatedAssignment;
    }

    public List<TeachingAssignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public TeachingAssignment getAssignmentById(String id) {
        return assignmentRepository.findById(id);
    }

    public void deleteAssignment(String id) {
        TeachingAssignment existing = assignmentRepository.findById(id);
        if (existing != null) {
            Course course = courseRepository.findById(existing.getCourseId());
            if (course != null && course.getAssignments() != null) {
                course.getAssignments().removeIf(a -> a.getId().equals(id));
                courseRepository.update(course);
            }
            removeFromStaff(existing.getStaffId(), id);
        }
        assignmentRepository.delete(id);
    }

    private void addToCourse(Course course, TeachingAssignment assignment) {
        if (course.getAssignments() == null) {
            course.setAssignments(new ArrayList<>());
        }
        course.getAssignments().add(assignment);
    }

    private void addToStaff(String staffId, TeachingAssignment assignment) {
        Teacher teacher = teacherRepository.findById(staffId);
        if (teacher != null) {
            if (teacher.getAssignments() == null) {
                teacher.setAssignments(new ArrayList<>());
            }
            teacher.getAssignments().add(assignment);
            teacherRepository.update(teacher);
        }

        Assistant assistant = assistantRepository.findById(staffId);
        if (assistant != null) {
            if (assistant.getAssignments() == null) {
                assistant.setAssignments(new ArrayList<>());
            }
            assistant.getAssignments().add(assignment);
            assistantRepository.update(assistant);
        }
    }

    private void removeFromStaff(String staffId, String assignmentId) {
        Teacher teacher = teacherRepository.findById(staffId);
        if (teacher != null && teacher.getAssignments() != null) {
            teacher.getAssignments().removeIf(a -> a.getId().equals(assignmentId));
            teacherRepository.update(teacher);
        }

        Assistant assistant = assistantRepository.findById(staffId);
        if (assistant != null && assistant.getAssignments() != null) {
            assistant.getAssignments().removeIf(a -> a.getId().equals(assignmentId));
            assistantRepository.update(assistant);
        }
    }
}
