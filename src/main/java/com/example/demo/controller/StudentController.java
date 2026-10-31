package com.example.demo.controller;
import com.example.demo.service.StudentService;
import org.springframework.stereotype.Controller;

@Controller
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }
}
