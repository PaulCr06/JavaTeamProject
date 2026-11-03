package com.example.demo.model;
import java.time.LocalDate;

public class Teacher extends Staff{
    private String titel;
    private String departmentID;
    private String email;
    private LocalDate hireDate;

    public Teacher(){
        super();
    }

    public Teacher(String id, String name, String titel, String departmentID, String email, LocalDate hireDate){
        super(id, name);
        this.titel = titel;
        this.departmentID = departmentID;
        this.email = email;
        this.hireDate = hireDate;
    }


    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
}
