package com.example.demo.controller;

import com.example.demo.service.TeacherService;
import org.springframework.stereotype.Controller;

@Controller
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
}