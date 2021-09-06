package com.internet.engineering.IECA8.domain.CourseEnrolment.System;

import com.google.gson.annotations.Expose;
import com.internet.engineering.IECA8.repository.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Offering {
    @Expose
    private String code;
    @Expose
    private String classCode;
    @Expose
    private String name;
    @Expose
    private int units;
    @Expose
    private String type;
    @Expose
    private String instructor;
    @Expose
    private int capacity;
    @Expose
    private List<Prerequisite> prerequisitesObj = new ArrayList<>();
    @Expose
    private List<String> prerequisites = new ArrayList<>();
    @Expose
    private ClassTime classTime;
    @Expose
    private ExamTime examTime;
    @Expose
    private int signedUp;
//    private List<Student> waitingList;
    @Expose
    private String time;
    @Expose
    private List<Day> days;

    public Offering(String code, String classCode, String name, String instructor, int units, String type, ClassTime classTime, ExamTime examTime, int capacity,  int signedUp, List<String> prerequisites, String time) {
        this.code = code;
        this.classCode = classCode;
        this.name = name;
        this.instructor = instructor;
        this.units = units;
        this.type = type;
        this.classTime = classTime;
        this.examTime = examTime;
        this.capacity = capacity;
        this.prerequisites = prerequisites;
        this.signedUp = signedUp;
        this.time = time;
//        waitingList = new ArrayList<>();
        days = new ArrayList<>();
    }

    public Offering() {
//        waitingList = new ArrayList<>();
        days = new ArrayList<>();
    }

//    public void addToWaitingList(Student student) {
//        waitingList.add(student);
//    }

//    // REMIND ME!
//    public void handleWaitingList() {
//
//        waitingList
//        .stream().forEach(student ->{
//            student.addToLastSubmit(new WeeklyScheduleItem(student, this));
//            try {
//                student.getSelectedCourses().stream().forEach(selectedCourse -> selectedCourse.finalizeWeeklyScheduleItem());
//            } catch (Exception e) {}
//            }
//        );
//
//        capacity += waitingList.size();
//        signedUp += waitingList.size();
//
//        waitingList.clear();
//    }

    public String getCode() {
        return code;
    }
    public String getClassCode() {
        return classCode;
    }
    public String getName() {
        return name;
    }
    public int getUnits() {
        return units;
    }
    public String getType() {
        return type;
    }
    public ClassTime getClassTime() {
        return classTime;
    }
    public void setClassTime(ClassTime classTime) {
        this.classTime = classTime;
    }
    public ExamTime getExamTime() {
        return examTime;
    }
    public List<String> getPrerequisites() {
        return prerequisites;
    }
    public List<Prerequisite> getPrerequisitesObj() {
        return prerequisitesObj;
    }
    public void addToPrerequisites(Prerequisite prerequisite) {
        this.prerequisitesObj.add(prerequisite);
    }
    public void setPrerequisites(List<Prerequisite> prerequisites) {
        this.prerequisitesObj = prerequisites;
    }
    public int getCapacity() {
        return capacity;
    }
    public int getSignedUp() {
        return signedUp;
    }
    public void signedUpInc() {
        signedUp++;
    }
    public void capacityInc() throws SQLException {
        capacity++;
        Repository.getInstance().updateCapacityAndSignedUp(this);
    }
    public void signedUpDec() throws SQLException{
        signedUp--;
        Repository.getInstance().updateCapacityAndSignedUp(this);
    }
    public String getInstructor() {
        return instructor;
    }
    public String getTime() {return time;}
    public void addToDays(Day day) {
        this.days.add(day);
    }
    public List<Day> getDays() {
        return days;
    }
    public void setTime(String time) { this.time = time; }
}