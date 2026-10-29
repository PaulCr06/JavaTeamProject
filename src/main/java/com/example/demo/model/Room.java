package com.example.demo.model;

import java.util.List;

public class Room {
    private String id;
    private double capacity;
    private String number;
    private List<Course> courses;

    public Room(String id, double capacity, String number, List<Course> courses) {
        this.id = id;
        this.capacity = capacity;
        this.number = number;
        this.courses = courses;
    }
    public String getId() {
        return id;

    }
    public void setId(String id) {
        this.id = id;
    }
    public double getCapacity() {
        return capacity;
    }
    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public List<Course> getCourses() {
        return courses;
    }
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }



}
