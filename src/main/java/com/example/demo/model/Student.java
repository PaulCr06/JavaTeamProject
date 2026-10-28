package com.example.demo.model;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Student {
    private String id;
    private String name;
    private List<Enrollment> enrollments = new ArrayList<>();
    private LocalDate birthDate;
    private String email;

    public Student(){}

    public Student(String id, String name, LocalDate birthDate, String email){
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
