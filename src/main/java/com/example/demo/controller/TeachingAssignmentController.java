package com.example.demo.controller;

import com.example.demo.service.TeachingAssignmentService;
import org.springframework.stereotype.Controller;

@Controller
public class TeachingAssignmentController {
    private final TeachingAssignmentService assignmentService;

    public TeachingAssignmentController(TeachingAssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }
}