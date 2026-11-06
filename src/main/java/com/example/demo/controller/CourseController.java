package com.example.demo.controller;

import org.springframework.ui.Model;
import com.example.demo.model.Course;
import com.example.demo.service.CourseService;




import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/course")
public class CourseController {
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
        
    }
    @GetMapping
    public String getCourses(Model model){
        model.addAttribute("courses",courseService.getAllCourses());
        return "/student/Course/index";
    }
    

}
