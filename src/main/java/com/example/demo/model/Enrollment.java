package com.example.demo.model;
import java.time.LocalDate;

public class Enrollment {
    private String id;
    private String studentId;
    private String courseID;
    private String grade;

    private LocalDate enrollmentDate;

    public Enrollment(){}

    public Enrollment(String id, String studentId, String courseID, String grade, LocalDate enrollmentDate){
        this.id = id;
        this.studentId = studentId;
        this.courseID = courseID;
        this.grade = grade;
        this.enrollmentDate = enrollmentDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
}
