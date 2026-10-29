package com.example.demo.model;

import org.apache.catalina.User;

import java.util.List;

public class Teacher extends Staff {
    private String titel;
    private String DepartmentId;

    public Teacher(String id, String name, List<TeachingAssignment> assignments, String titel, String DepartmentId) {
        super(id, name, assignments);
        this.titel = titel;
        this.DepartmentId = DepartmentId;
    }
    public String getTitel() {
        return titel;
    }
    public void setTitel(String titel) {
        this.titel = titel;
    }
    public String getDepartmentId() {
        return DepartmentId;
    }
    public void setDepartmentId(String DepartmentId) {
        this.DepartmentId = DepartmentId;
    }

}
