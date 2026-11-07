package com.example.demo.controller;

import org.springframework.ui.Model;

import com.example.demo.model.Assistant;
import com.example.demo.model.Course;
import com.example.demo.service.CourseService;




import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class CourseController {
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
        
    }
    @GetMapping("/course")
    public String getAllCourses(Model model){
        model.addAttribute("courses",courseService.getAllCourses());
        return "Course/index";
    }
    @GetMapping("/course/new")
    public String showCreateForm(Model model){
        model.addAttribute("course",new Course());
        return "Course/form";
    }
    @PostMapping("/course")
    public String saveCourse(@ModelAttribute  Course course) {
        courseService.saveCourse(course);
        return "redirect:/course";
    }
     @PostMapping("/course/{id}/delete")
    public String deleteCourse(@PathVariable  String id) {
        courseService.deleteCourse(id);
        return "redirect:/course";
    }
    

}
