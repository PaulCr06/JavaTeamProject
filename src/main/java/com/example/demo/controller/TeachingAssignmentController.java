package com.example.demo.controller;

import com.example.demo.model.TeachingAssignment;
import com.example.demo.model.ManagingType;
import com.example.demo.service.TeachingAssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class TeachingAssignmentController {
    private final TeachingAssignmentService teachingAssignmentService;

    public TeachingAssignmentController(TeachingAssignmentService teachingAssignmentService) {
        this.teachingAssignmentService = teachingAssignmentService;
    }

    @GetMapping("/teachingassignment")
    public String getAllAssignments(Model model) {
        model.addAttribute("assignments", teachingAssignmentService.getAllAssignments());
        return "TeachingAssignment/index";
    }

    @GetMapping("/teachingassignment/{id}")
    public String getAssignment(@PathVariable String id, Model model) {
        TeachingAssignment assignment = teachingAssignmentService.getAssignmentById(id);
        if (assignment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("assignment", assignment);
        return "TeachingAssignment/details";
    }

    @GetMapping("/teachingassignment/new")
    public String showCreateForm(Model model) {
        model.addAttribute("teachingAssignment", new TeachingAssignment());
        model.addAttribute("managingTypes", ManagingType.values());
        return "TeachingAssignment/form";
    }

    @PostMapping("/teachingassignment")
    public String saveAssignment(@ModelAttribute TeachingAssignment teachingAssignment) {
        teachingAssignmentService.saveAssignment(teachingAssignment);
        return "redirect:/teachingassignment";
    }

    @PostMapping("/teachingassignment/{id}/delete")
    public String deleteAssignment(@PathVariable String id) {
        teachingAssignmentService.deleteAssignment(id);
        return "redirect:/teachingassignment";
    }
}