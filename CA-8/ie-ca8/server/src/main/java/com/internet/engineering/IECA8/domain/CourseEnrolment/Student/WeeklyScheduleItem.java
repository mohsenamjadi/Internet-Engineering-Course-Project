package com.internet.engineering.IECA8.domain.CourseEnrolment.Student;

import com.google.gson.annotations.Expose;
import com.internet.engineering.IECA8.domain.CourseEnrolment.System.Offering;
import com.internet.engineering.IECA8.repository.Repository;

import java.sql.SQLException;

public class WeeklyScheduleItem {
    @Expose
    private Offering offering;
    private Student student;
    @Expose
    private String status;
    @Expose
    private String visibility;

    public WeeklyScheduleItem(Student student, Offering offering) {
        this.offering = offering;
        this.student = student;
        this.status = "non-finalized";
        this.visibility = "true";
    }

    public WeeklyScheduleItem(Student student, Offering offering, String status, String visibility) {
        this.offering = offering;
        this.student = student;
        this.status = status;
        this.visibility = visibility;
    }

    public void setStatus(String status) {this.status = status;}

    public Student getStudent() {
        return student;
    }

    public Offering getOffering() {
        return offering;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getStatus() {
        return status;
    }

    public void finalizeWeeklyScheduleItem() {
        status = "finalized";
        try {
            Repository.getInstance().updateWeeklyScheduleItem(this);
        } catch (SQLException sqlException) {

        }
    }

    public void handleWaitingList() throws Exception {
        student.addToLastSubmit(new WeeklyScheduleItem(student, offering));

        offering.capacityInc();
        offering.signedUpInc();
    }
}