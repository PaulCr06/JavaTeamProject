package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Enrollment;
import com.example.demo.model.TeachingAssignment;
import com.example.demo.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final TeachingAssignmentRepository teachingAssignmentRepository;
    private final RoomRepository roomRepository;
    private final DepartmentRepository departmentRepository;

  public CourseService(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, TeachingAssignmentRepository teachingAssignmentRepository, RoomRepository roomRepository, DepartmentRepository departmentRepository) {
      this.courseRepository = courseRepository;
      this.enrollmentRepository = enrollmentRepository;
      this.teachingAssignmentRepository = teachingAssignmentRepository;
      this.roomRepository = roomRepository;
      this.departmentRepository = departmentRepository;
  }
  public Course saveCourse(Course course) {
      if(departmentRepository.findById(course.getDepartmentId())==null){
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Eroare: Departamentul cu acest ID nu există!");
      }
      if(roomRepository.findById(course.getRoomId())==null){
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Eroare: Camera cu acest ID nu există!");
      }

      return courseRepository.save(course);
  }
  public Course getCourse(String id) {
      return courseRepository.findById(id);
  }
  public List<Course> getAllCourses() {
      return courseRepository.findAll();
  }
    public void deleteCourse(String id) {
        Course course = courseRepository.findById(id);

        for (Enrollment enrollment : course.getEnrollments()) {
            enrollmentRepository.delete(enrollment.getId());
        }

        for (TeachingAssignment t1 : course.getAssignments()) {
            teachingAssignmentRepository.delete(t1.getId());
        }

        courseRepository.delete(id);
    }
}
