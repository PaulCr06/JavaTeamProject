package com.example.demo.repository;

import com.example.demo.model.Department;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentRepository extends InFileRepository<Department, String> {
    public DepartmentRepository() {
        super("src/main/resources/data/department.json", Department.class, "dep");
    }

    public DepartmentRepository(String filePath) {
        super(filePath, Department.class, "dep");
    }
}
