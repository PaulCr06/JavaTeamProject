package com.example.demo.model;

public class Teacher extends Staff{
    private String titel;
    private String departmentID;

    public Teacher(){
        super();
    }

    public Teacher(String id, String name, String titel, String departmentID){
        super(id, name);
        this.titel = titel;
        this.departmentID = departmentID;
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
}
