package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Room;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.RoomRepository;

import java.util.List;

public class CourseService {

  private final CourseRepository courseRepository;

  public CourseService(CourseRepository courseRepository) {
      this.courseRepository = courseRepository;
  }
  public Course saveCourse(Course course) {
      return courseRepository.save(course);
  }
  public Course getCourse(String Id) {
      return courseRepository.findById(Id);
  }
  public List<Course> getAllCourses() {
      return courseRepository.findAll();
  }
  public void delete(String Id) {
      courseRepository.delete(Id);
  }
}
