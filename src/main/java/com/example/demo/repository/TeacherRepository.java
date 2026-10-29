package com.example.demo.repository;

import com.example.demo.model.Teacher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TeacherRepository {
    private final Map<String, Teacher> teachers = new HashMap<>();

    public Teacher save(Teacher teacher) {
        teachers.put(teacher.getId(), teacher);
        return teacher;
    }

    public List<Teacher> findAll() {
        return new ArrayList<>(teachers.values());
    }

    public Teacher findById(String id) {
        return teachers.get(id);
    }

    public void delete(String id) {
        teachers.remove(id);
    }
}