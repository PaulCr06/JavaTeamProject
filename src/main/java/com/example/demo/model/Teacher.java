package com.example.demo.model;

public class Teacher {
    private String titel;
    private String DepartmentId;

    public Teacher(String titel, String DepartmentId) {
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
