package com.internet.engineering.IECA8.domain.CourseEnrolment.System;

public class Day {
    private String code;
    private String classCode;
    private String day;

    public Day(String code, String classCode, String day) {
        this.code = code;
        this.classCode = classCode;
        this.day = day;
    }

    public String getCode() {
        return code;
    }

    public String getClassCode() {
        return classCode;
    }

    public String getDay() {
        return day;
    }
}
