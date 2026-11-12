package com.example.demo.controller;

import org.springframework.ui.Model;

import com.example.demo.model.Assistant;
import com.example.demo.model.Course;
import com.example.demo.service.CourseService;




import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller

public class CourseController {
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
        
    }
    @GetMapping("/courses")
    public String getAllCourses(Model model){
        model.addAttribute("courses",courseService.getAllCourses());
        return "Course/index";
    }

    @GetMapping("/courses/{id}")
    public String getCourse(@PathVariable String id, Model model){
        Course course = courseService.getCourse(id);
        if (course == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("course", course);
        return "Course/details";
    }

    @GetMapping("/courses/new")
    public String showCreateForm(Model model){
        model.addAttribute("course",new Course());
        model.addAttribute("isUpdate", false);
        return "Course/form";
    }

    @GetMapping("/courses/{id}/edit")
    public String showUpdateForm(@PathVariable String id, Model model) {
        Course course = courseService.getCourse(id);
        if (course == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("course", course);
        model.addAttribute("isUpdate", true);
        return "Course/form";
    }

    @PostMapping("/courses")
    public String saveCourse(@ModelAttribute  Course course) {
        courseService.saveCourse(course);
        return "redirect:/courses";
    }
     @PostMapping("/courses/{id}/delete")
    public String deleteCourse(@PathVariable  String id) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }
    

}
