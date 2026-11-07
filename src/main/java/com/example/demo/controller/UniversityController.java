package com.example.demo.controller;


import com.example.demo.model.University;
import com.example.demo.service.UniversityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UniversityController {
    private final UniversityService universityService;

    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping("/university")
    public String getAllUniversities(Model model) {
        model.addAttribute("universities", universityService.getAllUniversity());
        return "University/index";
    }

    @GetMapping("/university/new")
    public String showCreateForm(Model model) {
        model.addAttribute("university", new University());
        return "University/form";
    }

    @PostMapping("/university")
    public String saveUniversity(@ModelAttribute University university) {
        universityService.saveUniversity(university);
        return "redirect:/university";
    }

    @PostMapping("/university/{id}/delete")
    public String deleteUniversity(@PathVariable String id) {
        universityService.deleteUniversity(id);
        return "redirect:/university";
    }
}
