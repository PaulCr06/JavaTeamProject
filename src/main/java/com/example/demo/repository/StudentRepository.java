package com.example.demo.repository;

import com.example.demo.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentRepository implements Interface<Student, String> {

    // Key is studentId
    private final Map<String, Student> store = new HashMap<>();

    @Override
    public Student save(Student student) {
        store.put(student.getId(), student);
        return student;
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Student findById(String id) {
        return store.get(id);
    }

    @Override
    public void delete(String id) {
        store.remove(id);
    }
}