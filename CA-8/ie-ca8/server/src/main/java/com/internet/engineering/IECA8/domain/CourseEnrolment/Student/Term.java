package com.internet.engineering.IECA8.domain.CourseEnrolment.Student;

import com.google.gson.annotations.Expose;
import com.internet.engineering.IECA8.domain.CourseEnrolment.CourseEnrolment;

import java.util.List;

public class Term {
    @Expose
    private int term;
    @Expose
    private List<Grade> grades;
    @Expose
    private double moadel;

    public Term(int term){
        this.term = term;
    }

    private void calcMoadel(){
        double sum = 0;
        int coursesUnits = 0;
        try {
            for (Grade grade : grades){
                int courseUnits = CourseEnrolment.getInstance().getOffering(grade.getCode()).getUnits();
                coursesUnits += courseUnits;
                sum += grade.getGrade()*courseUnits;
            }
        } catch (Exception e) {
            return;
        }

        moadel = sum/coursesUnits;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
        calcMoadel();
    }
}