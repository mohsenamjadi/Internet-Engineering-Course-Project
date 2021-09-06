package com.internet.engineering.IECA8.domain.CourseEnrolment.Student;

import com.google.gson.annotations.Expose;

public class Grade {
    @Expose
    private String code;
    @Expose
    private String studentId;
    @Expose
    private double grade;
    @Expose
    private int term;


    public Grade(String code, String studentId, double grade, int term){
        this.code = code;
        this.grade = grade;
        this.studentId = studentId;
        this.term = term;
    }

    public String getCode(){return code;}
    public String getStudentId(){return studentId;}
    public double getGrade(){return grade;}
    public int getTerm(){return term;}
    public void setStudentId(String studentId) { this.studentId = studentId; }
}