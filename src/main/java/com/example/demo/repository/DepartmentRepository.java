package com.example.demo.repository;

import com.example.demo.model.Department;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentRepository extends InMemoryRepository<Department, String>{
}
