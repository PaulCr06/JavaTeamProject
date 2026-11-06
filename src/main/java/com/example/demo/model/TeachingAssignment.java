package com.example.demo.model;

public class TeachingAssignment implements Identifiable<String> {
    private String id;
    private String courseId;
    private String staffID;
    private String managing;

    public TeachingAssignment(){}

    public TeachingAssignment(String id, String courseId, String staffID, String managing){
        this.id = id;
        this.courseId = courseId;
        this.staffId = staffId;
        this.managing = managing;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ManagingType getManaging() {
        return managing;
    }

    public void setManaging(ManagingType managing) {
        this.managing = managing;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
