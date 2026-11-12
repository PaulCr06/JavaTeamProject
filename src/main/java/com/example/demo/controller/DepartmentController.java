package com.example.demo.controller;

import com.example.demo.model.Department;
import com.example.demo.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class DepartmentController {
    private final DepartmentService departmentService;
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    @GetMapping("/department")
    public String getAllDepartments(Model model){
        model.addAttribute("departments",departmentService.getAllDepartments());
        return "Department/index";

    }

    @GetMapping("/department/{id}")
    public String getDepartment(@PathVariable String id, Model model){
        Department department = departmentService.getDepartmentById(id);
        if (department == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("department", department);
        return "Department/details";
    }

    @GetMapping("/department/new")
    public String showCreateForm(Model model){
        model.addAttribute("department",new Department());
        model.addAttribute("isUpdate", false);
        return "Department/form";

    }

    @GetMapping("/department/{id}/edit")
    public String showUpdateForm(@PathVariable String id, Model model) {
        Department department = departmentService.getDepartmentById(id);
        if (department == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("department", department);
        model.addAttribute("isUpdate", true);
        return "Department/form";
    }

    @PostMapping("/department")
    public String saveDepartment(@ModelAttribute Department department){
            departmentService.saveDepartment(department);
            return "redirect:/department";

    }
    @PostMapping("/department/{id}/delete")
    public String deleteDepartment(@PathVariable String id){
            departmentService.deleteDepartment(id);
            return "redirect:/department";

    }

}
