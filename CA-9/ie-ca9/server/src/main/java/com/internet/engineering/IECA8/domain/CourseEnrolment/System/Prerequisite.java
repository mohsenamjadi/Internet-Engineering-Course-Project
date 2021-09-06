package com.internet.engineering.IECA8.domain.CourseEnrolment.System;

public class Prerequisite {
    private String code;
    private String classCode;
    private String prerequisite;

    public Prerequisite(String code, String classCode, String prerequisite) {
        this.code = code;
        this.classCode = classCode;
        this.prerequisite = prerequisite;
    }

    public String getCode() { return code; }

    public String getClassCode() { return classCode; }

    public String getPrerequisite() { return prerequisite; }
}
