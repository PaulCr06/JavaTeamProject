package com.example.demo.model;

public class TeachingAssignment {
    private String id;
    private String courseId;
    private String staffID;
    private String managing;

    public TeachingAssignment(){}

    public TeachingAssignment(String id, String courseId, String staffID, String managing){
        this.id = id;
        this.courseId = courseId;
        this.staffID = staffID;
        this.managing = managing;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManaging() {
        return managing;
    }

    public void setManaging(String managing) {
        this.managing = managing;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
