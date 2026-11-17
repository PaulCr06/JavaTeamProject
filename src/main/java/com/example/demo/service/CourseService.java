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
    private final EnrollmentService enrollmentService;
    private final TeachingAssignmentService teachingAssignmentService;

    public CourseService(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository,
                         TeachingAssignmentRepository teachingAssignmentRepository, RoomRepository roomRepository,
                         DepartmentRepository departmentRepository, EnrollmentService enrollmentService,
                         TeachingAssignmentService teachingAssignmentService) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.teachingAssignmentRepository = teachingAssignmentRepository;
        this.roomRepository = roomRepository;
        this.departmentRepository = departmentRepository;
        this.enrollmentService = enrollmentService;
        this.teachingAssignmentService = teachingAssignmentService;
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
        departmentRepository.update(department);

        if (room.getCourses() == null) {
            room.setCourses(new ArrayList<>());
        }
        room.getCourses().add(savedCourse);
        roomRepository.update(room);

        return savedCourse;
    }

    public Course updateCourse(Course course) {
        Course existing = courseRepository.findById(course.getId());
        if (existing == null) {
            return null;
        }

        Department department = departmentRepository.findById(course.getDepartmentId());
        if (department == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Department with this ID does not exist!");
        }

        Room room = roomRepository.findById(course.getRoomId());
        if (room == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Room with this ID does not exist!");
        }

        Department oldDepartment = departmentRepository.findById(existing.getDepartmentId());
        if (oldDepartment != null && oldDepartment.getCourses() != null) {
            oldDepartment.getCourses().removeIf(c -> c.getId().equals(existing.getId()));
            departmentRepository.update(oldDepartment);
        }

        Room oldRoom = roomRepository.findById(existing.getRoomId());
        if (oldRoom != null && oldRoom.getCourses() != null) {
            oldRoom.getCourses().removeIf(c -> c.getId().equals(existing.getId()));
            roomRepository.update(oldRoom);
        }

        course.setEnrollments(existing.getEnrollments());
        course.setAssignments(existing.getAssignments());

        Course updatedCourse = courseRepository.update(course);

        Department targetDepartment = departmentRepository.findById(updatedCourse.getDepartmentId());
        if (targetDepartment.getCourses() == null) {
            targetDepartment.setCourses(new ArrayList<>());
        }
        targetDepartment.getCourses().add(updatedCourse);
        departmentRepository.update(targetDepartment);

        Room targetRoom = roomRepository.findById(updatedCourse.getRoomId());
        if (targetRoom.getCourses() == null) {
            targetRoom.setCourses(new ArrayList<>());
        }
        targetRoom.getCourses().add(updatedCourse);
        roomRepository.update(targetRoom);

        return updatedCourse;
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

        Department department = departmentRepository.findById(course.getDepartmentId());
        if (department != null && department.getCourses() != null) {
            department.getCourses().removeIf(c -> c.getId().equals(id));
            departmentRepository.update(department);
        }

        Room room = roomRepository.findById(course.getRoomId());
        if (room != null && room.getCourses() != null) {
            room.getCourses().removeIf(c -> c.getId().equals(id));
            roomRepository.update(room);
        }

        for (Enrollment enrollment : enrollmentRepository.findAll()) {
            if (id.equals(enrollment.getCourseId())) {
                enrollmentService.deleteEnrollment(enrollment.getId());
            }
        }

        for (TeachingAssignment assignment : teachingAssignmentRepository.findAll()) {
            if (id.equals(assignment.getCourseId())) {
                teachingAssignmentService.deleteAssignment(assignment.getId());
            }
        }

        courseRepository.delete(id);
    }
}
