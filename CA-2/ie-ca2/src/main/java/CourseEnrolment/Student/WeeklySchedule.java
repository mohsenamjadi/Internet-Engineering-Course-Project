package CourseEnrolment.Student;

import CourseEnrolment.System.Offering;


import java.util.ArrayList;
import java.util.List;

public class WeeklySchedule {
    private List<WeeklyScheduleItem> weeklyScheduleItems = new ArrayList<>();

    private void findIfExistsWeeklyScheduleItem(WeeklyScheduleItem newWeeklyScheduleItem) throws Exception {
        for (WeeklyScheduleItem weeklyScheduleItem: weeklyScheduleItems) {
            if (weeklyScheduleItem.getStudent().getStudentId().equals(newWeeklyScheduleItem.getStudent().getStudentId()) &&
                    weeklyScheduleItem.getOffering().getCode().equals(newWeeklyScheduleItem.getOffering().getCode())) {
                throw new Exception("DuplicateWeeklyScheduleItem");
            }
        }
    }

    public void addToWeeklySchedule(WeeklyScheduleItem newWeeklyScheduleItem) throws Exception {
        findIfExistsWeeklyScheduleItem(newWeeklyScheduleItem);
        Student student = newWeeklyScheduleItem.getStudent();
        classTimeCollisionError(newWeeklyScheduleItem, student);
        examTimeCollisionError(newWeeklyScheduleItem, student);
        checkPrerquisites(newWeeklyScheduleItem, student);
        weeklyScheduleItems.add(newWeeklyScheduleItem);
    }

    public List<WeeklyScheduleItem> getStudentWeeklySchedule(Student student) throws Exception{
        List<WeeklyScheduleItem> specificStudentWeeklySchedule = new ArrayList<>();
        //boolean flag = false;
        for (WeeklyScheduleItem weeklyScheduleItem: weeklyScheduleItems) {
            if (weeklyScheduleItem.getStudent().getStudentId().equals(student.getStudentId())) {
                //flag = true;
                specificStudentWeeklySchedule.add(weeklyScheduleItem);
            }
        }

//        if (!flag)
//            throw new Exception("EmptyWeeklySchedule");

        return specificStudentWeeklySchedule;
    }

    public void emptyWeeklySchedule(Student student, Offering offering) throws Exception{
        for (WeeklyScheduleItem weeklyScheduleItem: weeklyScheduleItems)
            if (weeklyScheduleItem.getStudent().getStudentId().equals(student.getStudentId()) &&
                weeklyScheduleItem.getOffering().getCode().equals(offering.getCode()) &&
                    weeklyScheduleItem.getOffering().getClassCode().equals(offering.getClassCode())) {
                        weeklyScheduleItems.remove(weeklyScheduleItem);
                        return;
            }

        throw new Exception("WeeklyScheduleItemNotFound");
    }

    public void examTimeCollisionError(WeeklyScheduleItem newWeeklyScheduleItem, Student student) throws Exception{
        List<WeeklyScheduleItem> filledStudentWeeklySchedule = getStudentWeeklySchedule(student);
        for (WeeklyScheduleItem eachStudentWeeklyScheduleItem: filledStudentWeeklySchedule)
            if(eachStudentWeeklyScheduleItem.getOffering().getExamTime().getStart().equals( newWeeklyScheduleItem.getOffering().getExamTime().getStart()) &&
                eachStudentWeeklyScheduleItem.getOffering().getExamTime().getEnd().equals( newWeeklyScheduleItem.getOffering().getExamTime().getEnd()))
                    throw new Exception("ExamTimeCollisionError "+newWeeklyScheduleItem.getOffering().getCode()+" "+eachStudentWeeklyScheduleItem.getOffering().getCode());
    }

    public void classTimeCollisionError(WeeklyScheduleItem newWeeklyScheduleItem, Student student ) throws Exception{
        List<WeeklyScheduleItem> filledStudentWeeklySchedule = getStudentWeeklySchedule(student);
        for (WeeklyScheduleItem eachStudentWeeklyScheduleItem: filledStudentWeeklySchedule)
            if(eachStudentWeeklyScheduleItem.getOffering().getClassTime().getDays().equals( newWeeklyScheduleItem.getOffering().getClassTime().getDays()) &&
                eachStudentWeeklyScheduleItem.getOffering().getClassTime().getTime().equals( newWeeklyScheduleItem.getOffering().getClassTime().getTime()))
                    throw new Exception("ClassTimeCollisionError "+newWeeklyScheduleItem.getOffering().getCode()+" "+eachStudentWeeklyScheduleItem.getOffering().getCode());
    }

    public void checkPrerquisites(WeeklyScheduleItem newWeeklyScheduleItem , Student student)  throws Exception{
        List<String> prerequisites = newWeeklyScheduleItem.getOffering().getPrerequisites();
        List<Grade> studentGrades = student.getGrades();
        for (String prerequisite : prerequisites) {
            for (Grade eachCourseStudentGrade : studentGrades) {
                if (eachCourseStudentGrade.getCode().equals(prerequisite)) {
                    if (eachCourseStudentGrade.getGrade() < 10) {
                        throw new Exception("YouDidNotPassPrerequsites");
                    }
                }
            }
        }
    }

    private void unitCheck(Student student) throws Exception{
        int units = 0;
        for (WeeklyScheduleItem weeklyScheduleItem: weeklyScheduleItems)
            if (weeklyScheduleItem.getStudent().getStudentId().equals(student.getStudentId()))
                units+=weeklyScheduleItem.getOffering().getUnits();

        if (units<12)
            throw new Exception("MinimumUnitsError");
        else if (units>20)
            throw new Exception("MaximumUnitsError");
    }

    private boolean ifContainSameElement(List<String> list_1, List<String> list_2){
        for (String itemIt: list_1)
            for (String itemIt_2: list_2)
                if (itemIt.equals(itemIt_2))
                    return true;

        return false;
    }

    private void classTimeCollisionCheck(Student student) throws Exception{
        for (WeeklyScheduleItem weeklyScheduleItem: weeklyScheduleItems)
            for (WeeklyScheduleItem innerWeeklyScheduleItem: weeklyScheduleItems) {
                if (innerWeeklyScheduleItem == weeklyScheduleItem)
                    continue;

                if (weeklyScheduleItem.getStudent().getStudentId().equals(student.getStudentId()) &&
                    innerWeeklyScheduleItem.getStudent().getStudentId().equals(student.getStudentId()))
                        if (ifContainSameElement(weeklyScheduleItem.getOffering().getClassTime().getDays(), innerWeeklyScheduleItem.getOffering().getClassTime().getDays()) &&
                            weeklyScheduleItem.getOffering().getClassTime().getTime().equals(innerWeeklyScheduleItem.getOffering().getClassTime().getTime()))
                                throw new Exception("ClassTimeCollisionError "+weeklyScheduleItem.getOffering().getCode()+" "+innerWeeklyScheduleItem.getOffering().getCode());
            }
    }

    private void examTimeCollisionCheck(Student student) throws Exception{
        for (WeeklyScheduleItem weeklyScheduleItem: weeklyScheduleItems)
            for (WeeklyScheduleItem innerWeeklyScheduleItem: weeklyScheduleItems) {
                if (innerWeeklyScheduleItem == weeklyScheduleItem)
                    continue;

                if (weeklyScheduleItem.getStudent().getStudentId().equals(student.getStudentId()) &&
                    innerWeeklyScheduleItem.getStudent().getStudentId().equals(student.getStudentId()))
                        if(weeklyScheduleItem.getOffering().getExamTime().getStart().equals(innerWeeklyScheduleItem.getOffering().getExamTime().getStart()) &&
                            weeklyScheduleItem.getOffering().getExamTime().getEnd().equals(innerWeeklyScheduleItem.getOffering().getExamTime().getEnd()))
                                throw new Exception("ExamTimeCollisionError "+weeklyScheduleItem.getOffering().getCode()+" "+innerWeeklyScheduleItem.getOffering().getCode());
            }
    }

    private void capacityCheck() throws Exception{
        for (WeeklyScheduleItem weeklyScheduleItem: weeklyScheduleItems)
            if (weeklyScheduleItem.getOffering().attendeesCount() >= weeklyScheduleItem.getOffering().getCapacity())
                throw new Exception("CapacityError "+weeklyScheduleItem.getOffering().getCode());
    }

    public void finalizeWeeklySchedule(Student student) throws Exception{
        unitCheck(student);
//        classTimeCollisionCheck(student);
//        examTimeCollisionCheck(student);
//        capacityCheck();

        for (WeeklyScheduleItem weeklyScheduleItem : weeklyScheduleItems)
            if (weeklyScheduleItem.getStudent().getStudentId().equals(student.getStudentId())) {
                weeklyScheduleItem.finalizeWeeklyScheduleItem();
                weeklyScheduleItem.getOffering().attendeesCountInc();
            }
    }
}

