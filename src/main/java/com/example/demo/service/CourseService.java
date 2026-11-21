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
        if (course == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Course cannot be null!");
        }
        if (course.getTitle() == null || course.getTitle().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Course title must not be empty!");
        }
        if (course.getCredits() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Credits must be a positive integer!");
        }

        if (departmentRepository != null) {
            Department department = departmentRepository.findById(course.getDepartmentId());
            if (department == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Department with this ID does not exist!");
            }
            if (department.getCourses() == null) {
                department.setCourses(new ArrayList<>());
            }
            department.getCourses().add(course);
            departmentRepository.update(department);
        }

        if (roomRepository != null) {
            Room room = roomRepository.findById(course.getRoomId());
            if (room == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Room with this ID does not exist!");
            }
            if (room.getCourses() == null) {
                room.setCourses(new ArrayList<>());
            }
            room.getCourses().add(course);
            roomRepository.update(room);
        }

        if (courseRepository != null) {
            return courseRepository.save(course);
        }
        return course;
    }

    public Course updateCourse(Course course) {
        if (course == null || course.getId() == null) {
            return null;
        }
        if (course.getTitle() == null || course.getTitle().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Course title must not be empty!");
        }
        if (course.getCredits() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Credits must be a positive integer!");
        }

        Course existing = courseRepository != null ? courseRepository.findById(course.getId()) : null;
        if (existing == null) {
            return null;
        }

        if (departmentRepository != null) {
            Department department = departmentRepository.findById(course.getDepartmentId());
            if (department == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Department with this ID does not exist!");
            }
            Department oldDepartment = departmentRepository.findById(existing.getDepartmentId());
            if (oldDepartment != null && oldDepartment.getCourses() != null) {
                oldDepartment.getCourses().removeIf(c -> c.getId().equals(existing.getId()));
                departmentRepository.update(oldDepartment);
            }
            Department targetDepartment = departmentRepository.findById(course.getDepartmentId());
            if (targetDepartment != null) {
                if (targetDepartment.getCourses() == null) {
                    targetDepartment.setCourses(new ArrayList<>());
                }
                targetDepartment.getCourses().add(course);
                departmentRepository.update(targetDepartment);
            }
        }

        if (roomRepository != null) {
            Room room = roomRepository.findById(course.getRoomId());
            if (room == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Room with this ID does not exist!");
            }
            Room oldRoom = roomRepository.findById(existing.getRoomId());
            if (oldRoom != null && oldRoom.getCourses() != null) {
                oldRoom.getCourses().removeIf(c -> c.getId().equals(existing.getId()));
                roomRepository.update(oldRoom);
            }
            Room targetRoom = roomRepository.findById(course.getRoomId());
            if (targetRoom != null) {
                if (targetRoom.getCourses() == null) {
                    targetRoom.setCourses(new ArrayList<>());
                }
                targetRoom.getCourses().add(course);
                roomRepository.update(targetRoom);
            }
        }

        course.setEnrollments(existing.getEnrollments());
        course.setAssignments(existing.getAssignments());

        return courseRepository.update(course);
    }

    public Course getCourse(String id) {
        if (courseRepository == null) {
            return null;
        }
        Course course = courseRepository.findById(id);
        if (course != null) {
            if (enrollmentRepository != null) {
                List<Enrollment> courseEnrollments = new ArrayList<>();
                for (Enrollment enrollment : enrollmentRepository.findAll()) {
                    if (id.equals(enrollment.getCourseId())) {
                        courseEnrollments.add(enrollment);
                    }
                }
                course.setEnrollments(courseEnrollments);
            }

            if (teachingAssignmentRepository != null) {
                List<TeachingAssignment> courseAssignments = new ArrayList<>();
                for (TeachingAssignment assignment : teachingAssignmentRepository.findAll()) {
                    if (id.equals(assignment.getCourseId())) {
                        courseAssignments.add(assignment);
                    }
                }
                course.setAssignments(courseAssignments);
            }
        }
        return course;
    }

    public List<Course> getAllCourses() {
        return courseRepository != null ? courseRepository.findAll() : new ArrayList<>();
    }

    public void deleteCourse(String id) {
        if (courseRepository == null) {
            return;
        }
        Course course = courseRepository.findById(id);
        if (course == null) {
            return;
        }

        if (departmentRepository != null) {
            Department department = departmentRepository.findById(course.getDepartmentId());
            if (department != null && department.getCourses() != null) {
                department.getCourses().removeIf(c -> c.getId().equals(id));
                departmentRepository.update(department);
            }
        }

        if (roomRepository != null) {
            Room room = roomRepository.findById(course.getRoomId());
            if (room != null && room.getCourses() != null) {
                room.getCourses().removeIf(c -> c.getId().equals(id));
                roomRepository.update(room);
            }
        }

        if (enrollmentRepository != null && enrollmentService != null) {
            for (Enrollment enrollment : enrollmentRepository.findAll()) {
                if (id.equals(enrollment.getCourseId())) {
                    enrollmentService.deleteEnrollment(enrollment.getId());
                }
            }
        }

        if (teachingAssignmentRepository != null && teachingAssignmentService != null) {
            for (TeachingAssignment assignment : teachingAssignmentRepository.findAll()) {
                if (id.equals(assignment.getCourseId())) {
                    teachingAssignmentService.deleteAssignment(assignment.getId());
                }
            }
        }

        courseRepository.delete(id);
    }
}
