package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public String getAllCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "Course/index";
    }

    @GetMapping("/courses/{id}")
    public String getCourse(@PathVariable String id, Model model) {
        Course course = courseService.getCourse(id);
        if (course == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("course", course);
        return "Course/details";
    }

    @GetMapping("/courses/new")
    public String showCreateForm(Model model) {
        model.addAttribute("course", new Course());
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
    public String saveCourse(@ModelAttribute Course course) {
        courseService.saveCourse(course);
        return "redirect:/courses";
    }

    @PostMapping("/courses/{id}")
    public String updateCourse(@PathVariable String id, @ModelAttribute Course course) {
        course.setId(id);
        if (courseService.updateCourse(course) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/courses";
    }

    @PostMapping("/courses/{id}/delete")
    public String deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }
}
