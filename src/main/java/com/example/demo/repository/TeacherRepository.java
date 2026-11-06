package com.example.demo.repository;

import com.example.demo.model.Teacher;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherRepository extends InMemoryRepository<Teacher, String>{}