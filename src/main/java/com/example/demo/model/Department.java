package com.example.demo.model;

import java.util.List;

public class Department {
    private String id;
    private String name;
    private List<Course>courses;
    private List<Teacher>teachers;

    public Department() {}

    public Department(String id, String name, List<Course>courses,List<Teacher>teachers) {
        this.id = id;
        this.name = name;
        this.courses = courses;
        this.teachers = teachers;

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
        this.courses = courses;
    }
    public List<Teacher> getTeachers(){
        return teachers;
    }
    public void setTeachers(List<Teacher> teachers){
        this.teachers = teachers;
    }

}
