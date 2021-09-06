package com.internet.engineering.IECA7.domain.CourseEnrolment.Student;

import com.internet.engineering.IECA7.domain.CourseEnrolment.CourseEnrolment;
import com.internet.engineering.IECA7.domain.CourseEnrolment.System.Offering;
import com.internet.engineering.IECA7.domain.CourseEnrolment.System.Prerequisite;
import com.internet.engineering.IECA7.repository.Repository;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class WeeklySchedule {
    private List<WeeklyScheduleItem> getAllOWeeklySchedules() throws SQLException {
        return Repository.getInstance().getAllOWeeklySchedules();
    }

    private void findIfExistsWeeklyScheduleItem(WeeklyScheduleItem newWeeklyScheduleItem) throws Exception {
        List<WeeklyScheduleItem> weeklyScheduleItems = getAllOWeeklySchedules();

        for (WeeklyScheduleItem weeklyScheduleItem: weeklyScheduleItems) {
            if (weeklyScheduleItem.getStudent().getStudentId().equals(newWeeklyScheduleItem.getStudent().getStudentId()) &&
                    weeklyScheduleItem.getOffering().getCode().equals(newWeeklyScheduleItem.getOffering().getCode())) {
                throw new Exception("DuplicateWeeklyScheduleItem");
            }
        }
    }

    private void ifAlreadyPassed(WeeklyScheduleItem weeklyScheduleItem) throws Exception {
        if (CourseEnrolment.getInstance().getStudent().getGrades().stream().map(Grade::getCode).collect(Collectors.toList()).contains(weeklyScheduleItem.getOffering().getCode()))
            throw new Exception("AlreadyPassedWeeklyScheduleItem");
    }

    private boolean isFull(WeeklyScheduleItem weeklyScheduleItem) {
        if(weeklyScheduleItem.getOffering().getSignedUp() >= weeklyScheduleItem.getOffering().getCapacity())
            return true;

        return false;
    }

    public void addToWeeklySchedule(WeeklyScheduleItem newWeeklyScheduleItem) throws Exception {
        findIfExistsWeeklyScheduleItem(newWeeklyScheduleItem);
        Student student = newWeeklyScheduleItem.getStudent();
        classTimeCollisionError(newWeeklyScheduleItem, student);
        examTimeCollisionError(newWeeklyScheduleItem, student);
        Repository.getInstance().insertWeeklyScheduleItem(newWeeklyScheduleItem);
    }

    public List<WeeklyScheduleItem> getStudentWeeklySchedule(Student student, boolean flag) throws Exception{
        List<WeeklyScheduleItem> weeklyScheduleItems = getAllOWeeklySchedules();

        List<WeeklyScheduleItem> specificStudentWeeklySchedule = new ArrayList<>();
        for (WeeklyScheduleItem weeklyScheduleItem: weeklyScheduleItems) {
            if (weeklyScheduleItem.getStudent().getStudentId().equals(student.getStudentId())) {
                specificStudentWeeklySchedule.add(weeklyScheduleItem);
            }
        }

        if(flag) {
            specificStudentWeeklySchedule = specificStudentWeeklySchedule.stream().
            filter(weeklyScheduleItem -> weeklyScheduleItem.getVisibility().equals("true")).
            collect(Collectors.toList());
        }

        return specificStudentWeeklySchedule;
    }

    public void removeFromWeeklySchedule(Student student, Offering offering) throws Exception{
        Repository.getInstance().deleteWeeklyScheduleItem(student.getStudentId(), offering.getCode(), offering.getClassCode());
    }

    public void examTimeCollisionError(WeeklyScheduleItem newWeeklyScheduleItem, Student student) throws Exception{
        List<WeeklyScheduleItem> filledStudentWeeklySchedule = getStudentWeeklySchedule(student, true);
        for (WeeklyScheduleItem eachStudentWeeklyScheduleItem: filledStudentWeeklySchedule)
            if(eachStudentWeeklyScheduleItem.getOffering().getExamTime().getStart().equals( newWeeklyScheduleItem.getOffering().getExamTime().getStart()) &&
                eachStudentWeeklyScheduleItem.getOffering().getExamTime().getEnd().equals( newWeeklyScheduleItem.getOffering().getExamTime().getEnd()))
                    throw new Exception("ExamTimeCollisionError "+newWeeklyScheduleItem.getOffering().getCode()+" "+eachStudentWeeklyScheduleItem.getOffering().getCode());
    }

    public void classTimeCollisionError(WeeklyScheduleItem newWeeklyScheduleItem, Student student) throws Exception{
        List<WeeklyScheduleItem> filledStudentWeeklySchedule = getStudentWeeklySchedule(student, true);
        for (WeeklyScheduleItem eachStudentWeeklyScheduleItem: filledStudentWeeklySchedule)
            if(eachStudentWeeklyScheduleItem.getOffering().getClassTime().getDays().equals( newWeeklyScheduleItem.getOffering().getClassTime().getDays()) &&
                eachStudentWeeklyScheduleItem.getOffering().getClassTime().getTime().equals( newWeeklyScheduleItem.getOffering().getClassTime().getTime()))
                    throw new Exception("ClassTimeCollisionError "+newWeeklyScheduleItem.getOffering().getCode()+" "+eachStudentWeeklyScheduleItem.getOffering().getCode());
    }

    public void checkPrerquisites(WeeklyScheduleItem newWeeklyScheduleItem , Student student)  throws Exception{
        List<Prerequisite> prerequisites = Repository.getInstance().search(
                newWeeklyScheduleItem.getOffering().getCode(),
                newWeeklyScheduleItem.getOffering().getClassCode()
        );
        List<Grade> studentGrades = student.getGrades();

        if (!studentGrades.stream().map(Grade::getCode).collect(Collectors.toList()).containsAll(prerequisites))
            throw new Exception("YouDidNotPassPrerequsites");

        for (Prerequisite prerequisite : prerequisites)
            for (Grade eachCourseStudentGrade : studentGrades)
                if(eachCourseStudentGrade.getCode().equals(prerequisite.getPrerequisite()))
                    if (eachCourseStudentGrade.getGrade() < 10)
                        throw new Exception("YouDidNotPassPrerequsites");
    }

    private void unitCheck(Student student) throws Exception{
        AtomicInteger units = new AtomicInteger();


        CourseEnrolment.getInstance().getWeeklySchedule(CourseEnrolment.getInstance().getStudent().getStudentId(), true).
                stream().
                collect(Collectors.toList()).
                forEach(weeklyScheduleItem -> {
                    try {
                        units.addAndGet(weeklyScheduleItem.getOffering().getUnits());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                });

        if (units.get() <12)
            throw new Exception("MinimumUnitsError");
        else if (units.get() >20)
            throw new Exception("MaximumUnitsError");
    }

    //list1 - list2
    public List<WeeklyScheduleItem> relativeComplements(List<WeeklyScheduleItem> list1, List<WeeklyScheduleItem> list2) {
        return list1.stream().filter(c->!list2.contains(c)).collect(Collectors.toList());
    }

    public void handleSignedUps() throws Exception {
        relativeComplements(CourseEnrolment.getInstance().getStudent().getSelectedCourses(),
            CourseEnrolment.getInstance().getStudent().getLastSubmit())
                .stream()
                .filter(weeklyScheduleItem -> weeklyScheduleItem.getStatus().equals("finalized"))
                .forEach(w -> w.getOffering().signedUpInc());

        relativeComplements(CourseEnrolment.getInstance().getStudent().getLastSubmit(),
            CourseEnrolment.getInstance().getStudent().getSelectedCourses())
                .stream().forEach(w -> {
            try {
                w.getOffering().signedUpDec();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });
    }

    public void finalizeWeeklySchedule(Student student) throws Exception{
        unitCheck(student);

        CourseEnrolment.getInstance().getWeeklySchedule(CourseEnrolment.getInstance().getStudent().getStudentId(), false).
                stream().
                filter(weeklyScheduleItem -> weeklyScheduleItem.getVisibility().equals("false")).
                collect(Collectors.toList()).
                forEach(weeklyScheduleItem -> {
                    try {
                        CourseEnrolment.getInstance().removeFromWeeklySchedule(
                                CourseEnrolment.getInstance().getStudent().getStudentId(),
                                weeklyScheduleItem.getOffering().getCode(),
                                weeklyScheduleItem.getOffering().getClassCode()
                        );
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                });

        CourseEnrolment.getInstance().getWeeklySchedule(CourseEnrolment.getInstance().getStudent().getStudentId(), true).
                stream().
                filter(weeklyScheduleItem-> weeklyScheduleItem.getStatus().equals("non-finalized") || weeklyScheduleItem.getStatus().equals("pending")).
                collect(Collectors.toList()).
                forEach(weeklyScheduleItem -> {
                    try {
                        checkPrerquisites(weeklyScheduleItem, student);
                        ifAlreadyPassed(weeklyScheduleItem);
                        if(isFull(weeklyScheduleItem)){
                            weeklyScheduleItem.setStatus("pending");
                            Repository.getInstance().updateWeeklyScheduleItem(weeklyScheduleItem);
                        }
                        else
                            weeklyScheduleItem.finalizeWeeklyScheduleItem();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                });

        handleSignedUps();
    }

    public void emptyWeeklySchedule (Student student) throws SQLException {
        Repository.getInstance().emptyWeeklySchedule(student.getStudentId());
    }
}