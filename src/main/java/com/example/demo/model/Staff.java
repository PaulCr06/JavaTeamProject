package com.example.demo.model;

import java.util.List;

public abstract class Staff {


    private String id;
    private String name;
    private List<TeachingAssignment> assignments;


    public Staff(String id, String name, List<TeachingAssignment> assignments) {
        this.id = id;
        this.name = name;
        this.assignments = assignments;
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

    public List<TeachingAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<TeachingAssignment> assignments) {
        this.assignments = assignments;
    }
}