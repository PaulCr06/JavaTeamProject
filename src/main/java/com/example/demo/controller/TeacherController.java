package com.example.demo.controller;
import com.example.demo.model.Teacher;
import com.example.demo.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/teacher")
    public String getAllTeachers(Model model){
        model.addAttribute("teachers", teacherService.findAllTeachers());
        return "student/teacher/index";
    }

    @GetMapping("/teacher/new")
    public String showCreateForm(Model model){
        model.addAttribute("teacher", new Teacher());
        return "teacher/form";
    }

    @PostMapping("/teacher")
    public String saveTeacher(@ModelAttribute Teacher teacher){
        teacherService.saveTeacher(teacher);
        return "redirect:/teacher";
    }

    @PostMapping("/teacher/{id}/delete")
    public String deleteTeacher(@PathVariable String id){
        teacherService.deleteTeacher(id);
        return "redirect:/teacher";
    }

}