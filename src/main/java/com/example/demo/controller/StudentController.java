package com.example.demo.controller;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/student")
    public String getAllStudents(Model model){
        model.addAttribute("students",studentService.findAllStudents());
        return "student/index";
    }

    @GetMapping("/student/new")
    public String showCreateForm(Model model){
        model.addAttribute("student", new Student());
        return "student/form";
    }

    @PostMapping("/student")
    public String saveStudent(@ModelAttribute Student student){
        studentService.saveStudent(student);
        return "redirect:/student";
    }

    @PostMapping("/student/{id}/delete")
    public String deleteStudent(@PathVariable String id){
        studentService.deleteStudent(id);
        return "redirect:/student";
    }
}
