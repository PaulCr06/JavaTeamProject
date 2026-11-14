package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/student")
    public String getAllStudents(Model model) {
        model.addAttribute("students", studentService.findAllStudents());
        return "student/index";
    }

    @GetMapping("/student/{id}")
    public String getStudent(@PathVariable String id, Model model) {
        Student student = studentService.findStudentById(id);
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("student", student);
        return "student/details";
    }

    @GetMapping("/student/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("isUpdate", false);
        return "student/form";
    }

    @GetMapping("/student/{id}/edit")
    public String showUpdateForm(@PathVariable String id, Model model) {
        Student student = studentService.findStudentById(id);
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("student", student);
        model.addAttribute("isUpdate", true);
        return "student/form";
    }

    @PostMapping("/student")
    public String saveStudent(@ModelAttribute Student student) {
        studentService.saveStudent(student);
        return "redirect:/student";
    }

    @PostMapping("/student/{id}")
    public String updateStudent(@PathVariable String id, @ModelAttribute Student student) {
        student.setId(id);
        if (studentService.updateStudent(student) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/student";
    }

    @PostMapping("/student/{id}/delete")
    public String deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return "redirect:/student";
    }
}
