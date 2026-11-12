package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Enrollment;
import com.example.demo.model.TeachingAssignment;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.repository.TeachingAssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final TeachingAssignmentRepository teachingAssignmentRepository;

  public CourseService(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, TeachingAssignmentRepository teachingAssignmentRepository) {
      this.courseRepository = courseRepository;
      this.enrollmentRepository = enrollmentRepository;
      this.teachingAssignmentRepository = teachingAssignmentRepository;
  }
  public Course saveCourse(Course course) {
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
