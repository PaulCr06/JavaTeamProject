package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Department implements Identifiable<String>{
    private String id;
    private String name;
    private List<Course> courses = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();

    public Department() {}

    public Department(String name) {
        this(null, name, new ArrayList<>(), new ArrayList<>());
    }

    public Department(String id, String name, List<Course> courses, List<Teacher> teachers) {
        this.id = id;
        this.name = name;
        this.courses = courses != null ? courses : new ArrayList<>();
        this.teachers = teachers != null ? teachers : new ArrayList<>();
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Course> getCourses(){
        return courses;
    }
    public void setCourses(List<Course> courses){
        this.courses = courses != null ? courses : new ArrayList<>();
    }
    public List<Teacher> getTeachers(){
        return teachers;
    }
    public void setTeachers(List<Teacher> teachers){
        this.teachers = teachers != null ? teachers : new ArrayList<>();
    }
}
