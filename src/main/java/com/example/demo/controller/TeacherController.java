package com.example.demo.controller;

import com.example.demo.model.Teacher;
import com.example.demo.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/teacher")
    public String getAllTeachers(Model model) {
        model.addAttribute("teachers", teacherService.findAllTeachers());
        return "teacher/index";
    }

    @GetMapping("/teacher/{id}")
    public String getTeacher(@PathVariable String id, Model model) {
        Teacher teacher = teacherService.getTeacherById(id);
        if (teacher == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("teacher", teacher);
        return "teacher/details";
    }

    @GetMapping("/teacher/new")
    public String showCreateForm(Model model) {
        model.addAttribute("teacher", new Teacher());
        model.addAttribute("isUpdate", false);
        return "teacher/form";
    }

    @GetMapping("/teacher/{id}/edit")
    public String showUpdateForm(@PathVariable String id, Model model) {
        Teacher teacher = teacherService.getTeacherById(id);
        if (teacher == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("teacher", teacher);
        model.addAttribute("isUpdate", true);
        return "teacher/form";
    }

    @PostMapping("/teacher")
    public String saveTeacher(@ModelAttribute Teacher teacher) {
        teacherService.saveTeacher(teacher);
        return "redirect:/teacher";
    }

    @PostMapping("/teacher/{id}")
    public String updateTeacher(@PathVariable String id, @ModelAttribute Teacher teacher) {
        teacher.setId(id);
        if (teacherService.updateTeacher(teacher) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/teacher";
    }

    @PostMapping("/teacher/{id}/delete")
    public String deleteTeacher(@PathVariable String id) {
        teacherService.deleteTeacher(id);
        return "redirect:/teacher";
    }
}
