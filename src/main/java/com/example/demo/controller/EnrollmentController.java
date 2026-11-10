package com.example.demo.controller;

import com.example.demo.model.Enrollment;
import com.example.demo.model.Grade;
import com.example.demo.service.EnrollmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/enrollment")
    public String getAllEnrollments(Model model){
        model.addAttribute("enrollments", enrollmentService.getAllEnrollments());
        return "enrollment/index";
    }

    @GetMapping("/enrollment/new")
    public String showCreateForm(Model model) {
        model.addAttribute("enrollment", new Enrollment());
        model.addAttribute("gradeTypes",Grade.values());
        return "enrollment/form";
    }

    @PostMapping("/enrollment")
    public String saveEnrollment(@ModelAttribute Enrollment enrollment) {
        enrollmentService.saveEnrollment(enrollment);
        return "redirect:/enrollment";
    }

    @PostMapping("/enrollment/{id}/delete")
    public String deleteEnrollment(@PathVariable String id) {
        enrollmentService.deleteEnrollment(id);
        return "redirect:/enrollment";
    }
}