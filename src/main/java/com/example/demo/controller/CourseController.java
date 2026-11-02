package com.example.demo.controller;


import com.example.demo.service.CourseService;
import com.example.demo.service.EnrollmentService;
import org.springframework.stereotype.Controller;

@Controller
public class CourseController {
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
}
