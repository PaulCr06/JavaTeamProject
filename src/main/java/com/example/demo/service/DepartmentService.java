package com.example.demo.service;

import com.example.demo.model.Department;
import com.example.demo.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
    public Department getDepartmentById(String id) {
        return departmentRepository.findById(id);
    }
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }
    public void deleteDepartment(String id) {
        departmentRepository.delete(id);
    }
}
