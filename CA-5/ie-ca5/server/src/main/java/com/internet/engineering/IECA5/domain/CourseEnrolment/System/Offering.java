package com.internet.engineering.IECA5.domain.CourseEnrolment.System;

import com.google.gson.annotations.Expose;
import com.internet.engineering.IECA5.domain.CourseEnrolment.CourseEnrolment;
import com.internet.engineering.IECA5.domain.CourseEnrolment.Student.Student;
import com.internet.engineering.IECA5.domain.CourseEnrolment.Student.WeeklyScheduleItem;

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
    private List<String> prerequisites = new ArrayList<>();
    @Expose
    private ClassTime classTime;
    @Expose
    private ExamTime examTime;
    @Expose
    private int signedUp;
    private List<Student> waitingList;

    public Offering(String code, String classCode, String name, String instructor, int units, String type, ClassTime classTime, ExamTime examTime, int capacity, List<String> prerequisites) {
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
    }

    public Offering() {
        waitingList = new ArrayList<>();
    }

    public void addToWaitingList(Student student) {
        waitingList.add(student);
    }

    public void handleWaitingList() {

        waitingList
        .stream().forEach(student ->{
            student.addToLastSubmit(new WeeklyScheduleItem(student, this));
            try {
                student.getSelectedCourses().stream().forEach(selectedCourse -> selectedCourse.finalizeWeeklyScheduleItem());
            } catch (Exception e) {}
            }
        );

        capacity += waitingList.size();
        signedUp += waitingList.size();

        waitingList.clear();
    }

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
    public ExamTime getExamTime() {
        return examTime;
    }
    public List<String> getPrerequisites() {
        return prerequisites;
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
    public void signedUpDec() {
        signedUp--;
    }
    public String getInstructor() {
        return instructor;
    }

    public boolean isCopy(Offering offering) {
        return this.name.equals(offering.name);
    }
}