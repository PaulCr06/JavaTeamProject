package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Department;
import com.example.demo.model.Enrollment;
import com.example.demo.model.Room;
import com.example.demo.model.TeachingAssignment;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.TeachingAssignmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final TeachingAssignmentRepository teachingAssignmentRepository;
    private final RoomRepository roomRepository;
    private final DepartmentRepository departmentRepository;

    public CourseService(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository,
                         TeachingAssignmentRepository teachingAssignmentRepository, RoomRepository roomRepository,
                         DepartmentRepository departmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.teachingAssignmentRepository = teachingAssignmentRepository;
        this.roomRepository = roomRepository;
        this.departmentRepository = departmentRepository;
    }

    public Course saveCourse(Course course) {
        Department department = departmentRepository.findById(course.getDepartmentId());
        if (department == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Department with this ID does not exist!");
        }

        Room room = roomRepository.findById(course.getRoomId());
        if (room == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Room with this ID does not exist!");
        }

        Course savedCourse = courseRepository.save(course);

        if (department.getCourses() == null) {
            department.setCourses(new ArrayList<>());
        }
        department.getCourses().add(savedCourse);

        if (room.getCourses() == null) {
            room.setCourses(new ArrayList<>());
        }
        room.getCourses().add(savedCourse);

        return savedCourse;
    }

    public Course updateCourse(Course course) {
        Department department = departmentRepository.findById(course.getDepartmentId());
        if (department == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Department with this ID does not exist!");
        }

        Room room = roomRepository.findById(course.getRoomId());
        if (room == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Room with this ID does not exist!");
        }

        Course existing = courseRepository.findById(course.getId());
        if (existing == null) {
            return null;
        }

        course.setEnrollments(existing.getEnrollments());
        course.setAssignments(existing.getAssignments());

        return courseRepository.update(course);
    }

    public Course getCourse(String id) {
        return courseRepository.findById(id);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public void deleteCourse(String id) {
        Course course = courseRepository.findById(id);
        if (course == null) {
            return;
        }

        for (Enrollment enrollment : enrollmentRepository.findAll()) {
            if (id.equals(enrollment.getCourseId())) {
                enrollmentRepository.delete(enrollment.getId());
            }
        }

        for (TeachingAssignment assignment : teachingAssignmentRepository.findAll()) {
            if (id.equals(assignment.getCourseId())) {
                teachingAssignmentRepository.delete(assignment.getId());
            }
        }

        courseRepository.delete(id);
    }
}
