package com.example.demo.repository;

import com.example.demo.model.Course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseRepository implements  Interface<Course,String> {
    private final Map<String,Course> courses = new HashMap<>();
    @Override
    public Course save(Course entity) {
        courses.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(courses.values());
    }

    @Override
    public Course findById(String s) {
        return courses.get(s);
    }

    @Override
    public void delete(String s) {
        courses.remove(s);
    }
}
