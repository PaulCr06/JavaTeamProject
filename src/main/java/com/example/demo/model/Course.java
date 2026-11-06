package com.example.demo.model;

import java.util.List;

public class Course implements Identifiable<String> {
    private String id;
    private String title;
    private int credits;
    private String departmentId;
    private String roomId;
    private List<Enrollment> enrollments;
    private List<TeachingAssignment> assignments;


    public Course(){}

    public Course(String id, String title, int credits, String departmentId, String roomId,List<Enrollment>enrollments,List<TeachingAssignment> assignments) {
        this.id = id;
        this.title = title;
        this.credits = credits;
        this.departmentId = departmentId;
        this.roomId = roomId;
        this.enrollments = enrollments;
        this.assignments = assignments;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getCredits() {
        return credits;
    }
    public void setCredits(int credits) {
        this.credits = credits;
    }
    public String getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
    public String getRoomId() {
        return roomId;
    }
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }
    public List<TeachingAssignment> getAssignments() {
        return assignments;
    }
    public void setAssignments(List<TeachingAssignment> assignments) {
        this.assignments = assignments;
    }


}
