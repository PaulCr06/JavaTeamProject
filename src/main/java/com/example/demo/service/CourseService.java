package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseService {

  private final CourseRepository courseRepository;

  public CourseService(CourseRepository courseRepository) {
      this.courseRepository = courseRepository;
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
      courseRepository.delete(id);
  }
}
