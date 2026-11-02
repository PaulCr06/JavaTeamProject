package com.example.demo.controller;

import com.example.demo.service.DepartmentService;
import org.springframework.stereotype.Controller;

@Controller
public class DepartmentController {
    private final DepartmentService departmentService;
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
}
