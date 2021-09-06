package com.internet.engineering.IECA5.domain.CourseEnrolment.Student;

import com.google.gson.annotations.Expose;

public class Grade {
    @Expose
    private String code;
    @Expose
    private double grade;
    @Expose
    private int term;

    public Grade(String code, int grade){
        this.code = code;
        this.grade = grade;
    }

    public String getCode(){return code;}
    public double getGrade(){return grade;}
    public int getTerm(){return term;}
}