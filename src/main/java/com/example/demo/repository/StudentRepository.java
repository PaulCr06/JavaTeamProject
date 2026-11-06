package com.example.demo.repository;

import com.example.demo.model.Student;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository extends InMemoryRepository<Student, String>{
}