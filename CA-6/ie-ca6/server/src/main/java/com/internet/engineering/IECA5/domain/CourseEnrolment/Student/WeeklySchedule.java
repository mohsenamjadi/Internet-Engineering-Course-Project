package com.internet.engineering.IECA5.domain.CourseEnrolment.Student;

import com.internet.engineering.IECA5.domain.CourseEnrolment.CourseEnrolment;
import com.internet.engineering.IECA5.domain.CourseEnrolment.System.Offering;
import com.internet.engineering.IECA5.domain.CourseEnrolment.System.Prerequisite;
import com.internet.engineering.IECA5.repository.MzRepository;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class WeeklySchedule {
//    private List<WeeklyScheduleItem> weeklyScheduleItems = new ArrayList<>();

    private List<WeeklyScheduleItem> getAllOWeeklySchedules() throws SQLException {
        return MzRepository.getInstance().getAllOWeeklySchedules();
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
        MzRepository.getInstance().insertWeeklyScheduleItem(newWeeklyScheduleItem);
//        weeklyScheduleItems.add(newWeeklyScheduleItem);
    }

    // REMIND ME!
    public List<WeeklyScheduleItem> getStudentWeeklySchedule(Student student, boolean flag) throws Exception{
        List<WeeklyScheduleItem> weeklyScheduleItems = getAllOWeeklySchedules();

        List<WeeklyScheduleItem> specificStudentWeeklySchedule = new ArrayList<>();
        //boolean flag = false;
        for (WeeklyScheduleItem weeklyScheduleItem: weeklyScheduleItems) {
            if (weeklyScheduleItem.getStudent().getStudentId().equals(student.getStudentId())) {
                //flag = true;
                specificStudentWeeklySchedule.add(weeklyScheduleItem);
            }
        }

        // NEWLY ADDED
        if(flag) {
            specificStudentWeeklySchedule = specificStudentWeeklySchedule.stream().
            filter(weeklyScheduleItem -> weeklyScheduleItem.getVisibility().equals("true")).
            collect(Collectors.toList());
        }

        //

//        if (!flag)
//            throw new Exception("EmptyWeeklySchedule");

        return specificStudentWeeklySchedule;
    }

//    public void removeFromWeeklySchedule(Student student, Offering offering) throws Exception{
//        List<WeeklyScheduleItem> weeklyScheduleItems = getAllOWeeklySchedules();
//
//        for (WeeklyScheduleItem weeklyScheduleItem: weeklyScheduleItems)
//            if (weeklyScheduleItem.getStudent().getStudentId().equals(student.getStudentId()) &&
//                weeklyScheduleItem.getOffering().getCode().equals(offering.getCode()) &&
//                    weeklyScheduleItem.getOffering().getClassCode().equals(offering.getClassCode())) {
//                        weeklyScheduleItems.remove(weeklyScheduleItem);
//                        return;
//            }
//
//        throw new Exception("WeeklyScheduleItemNotFound");
//    }

    public void removeFromWeeklySchedule(Student student, Offering offering) throws Exception{
        MzRepository.getInstance().deleteWeeklyScheduleItem(student.getStudentId(), offering.getCode(), offering.getClassCode());
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
//        List<Prerequisite> prerequisites = newWeeklyScheduleItem.getOffering().getPrerequisites();
        List<Prerequisite> prerequisites = MzRepository.getInstance().search(
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

//    finalized, non-visible -> remove
////    non-visible, non-finalized -> /
//    non-finliazed, visible -> finalize
//    finliaze, visible -> /nothing to be done

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



//        int units = 0;
//        List<WeeklyScheduleItem> weeklyScheduleItems = getAllOWeeklySchedules();
//
//        for (WeeklyScheduleItem weeklyScheduleItem: weeklyScheduleItems)
//            if (weeklyScheduleItem.getStudent().getStudentId().equals(student.getStudentId()))
//                units+=weeklyScheduleItem.getOffering().getUnits();

        if (units.get() <12)
            throw new Exception("MinimumUnitsError");
        else if (units.get() >20)
            throw new Exception("MaximumUnitsError");
    }

    private void capacityCheck() throws Exception{
        for (WeeklyScheduleItem weeklyScheduleItem: CourseEnrolment.getInstance().getStudent().getSelectedCourses())
            if (weeklyScheduleItem.getOffering().getSignedUp() >= weeklyScheduleItem.getOffering().getCapacity())
                throw new Exception("CapacityError "+weeklyScheduleItem.getOffering().getCode());
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

    private boolean ifAlreadySubmitted(WeeklyScheduleItem weeklyScheduleItem){
        for(WeeklyScheduleItem iter : CourseEnrolment.getInstance().getStudent().getLastSubmit())
            if(iter.getOffering().getCode() == weeklyScheduleItem.getOffering().getCode() && iter.getOffering().getClassCode() == weeklyScheduleItem.getOffering().getClassCode())
                return true;

        return false;
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


//        finalized, visible -> /
//        finalized, non-visibile -> remove
//        non-finalized, visible -> finalize
//        non-finalized, non-visibile -> /
//
//
//
//




        List<WeeklyScheduleItem> weeklyScheduleItems = getAllOWeeklySchedules();

//        capacityCheck();
//        unitCheck(student);

        //NEWLY ADDED
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
                            MzRepository.getInstance().updateWeeklyScheduleItem(weeklyScheduleItem);
                        }
                        else
                            weeklyScheduleItem.finalizeWeeklyScheduleItem();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                });
        //NEWLY ADDED
//
//        for (WeeklyScheduleItem weeklyScheduleItem : weeklyScheduleItems)
//            if (weeklyScheduleItem.getStudent().getStudentId().equals(student.getStudentId())) {
//                if(ifAlreadySubmitted(weeklyScheduleItem))
//                    continue;
//                checkPrerquisites(weeklyScheduleItem, student);
//                ifAlreadyPassed(weeklyScheduleItem);
//                if(isFull(weeklyScheduleItem)){
////                    weeklyScheduleItem.getOffering().addToWaitingList(CourseEnrolment.getInstance().getStudent());
//                    weeklyScheduleItem.setStatus("pending");
//                    MzRepository.getInstance().updateWeeklyScheduleItem(weeklyScheduleItem);
//                    continue;
//                }
//
//                weeklyScheduleItem.finalizeWeeklyScheduleItem();
//            }

        handleSignedUps();
    }

    public void emptyWeeklySchedule (Student student) throws SQLException {
        MzRepository.getInstance().emptyWeeklySchedule(student.getStudentId());
//        List<WeeklyScheduleItem> weeklyScheduleItems = getAllOWeeklySchedules();
//
//        for (Iterator<WeeklyScheduleItem> iterator = weeklyScheduleItems.iterator(); iterator.hasNext();) {
//            WeeklyScheduleItem weeklyScheduleItem = iterator.next();
//            if(weeklyScheduleItem.getStudent().getStudentId().equals(student.getStudentId()))
//                iterator.remove();
//        }
    }
}