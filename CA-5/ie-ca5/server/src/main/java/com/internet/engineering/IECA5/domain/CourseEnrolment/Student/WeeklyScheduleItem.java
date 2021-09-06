package com.internet.engineering.IECA5.domain.CourseEnrolment.Student;

import com.google.gson.annotations.Expose;
import com.internet.engineering.IECA5.domain.CourseEnrolment.System.Offering;

public class WeeklyScheduleItem {
    @Expose
    private Offering offering;
    private Student student;
    @Expose
    private String status;

    public WeeklyScheduleItem(Student student, Offering offering) {
        this.offering = offering;
        this.student = student;
        this.status = "non-finalized";
    }

    public void setStatus(String status) {this.status = status;}

    public Student getStudent() {
        return student;
    }

    public Offering getOffering() {
        return offering;
    }

    public String getStatus() {
        return status;
    }

    public void finalizeWeeklyScheduleItem() {
        status = "finalized";
    }
}