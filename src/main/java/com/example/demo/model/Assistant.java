package com.example.demo.model;

import java.util.List;

public class Assistant extends Staff {


    private String role;


    public Assistant(String id, String name, List<TeachingAssignment> assignments, String role) {

        super(id, name, assignments);
        this.role = role;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}