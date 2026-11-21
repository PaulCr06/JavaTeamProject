package com.example.demo.repository;

import com.example.demo.model.Course;
import org.springframework.stereotype.Repository;

@Repository
public class CourseRepository extends InFileRepository<Course, String> {
    public CourseRepository() {
        super("src/main/resources/data/course.json", Course.class, "c");
    }

    public CourseRepository(String filePath) {
        super(filePath, Course.class, "c");
    }
}
