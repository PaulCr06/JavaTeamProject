package com.example.demo.repository;

import com.example.demo.model.Student;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository extends InFileRepository<Student, String>{

    public StudentRepository(){
        super("src/main/resources/data/student.json", Student.class);
    }
}