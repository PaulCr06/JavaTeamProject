package com.example.demo.repository;


import com.example.demo.model.Teacher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TeacherRepository implements Interface<Teacher, String> {
    private final Map<String, Teacher> teachers = new HashMap<>();



    @Override
    public Teacher save(Teacher  student) {
        teachers.put(student.getId(), student);
        return student;
    }

    @Override
    public List<Teacher> findAll() {
        List<Teacher> students = new ArrayList<>();
        students.addAll(teachers.values());
        return students;
    }

    @Override
    public Teacher findById(String id) {
        return teachers.get(id);
    }

    @Override
    public void delete(String id) {
        teachers.remove(id);
    }
}