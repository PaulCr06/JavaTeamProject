package com.example.demo.controller;

import com.example.demo.service.EnrollmentService;
import org.springframework.stereotype.Controller;

@Controller
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }
}