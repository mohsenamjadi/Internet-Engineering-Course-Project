package CourseEnrolment.System;

import CourseEnrolment.Student.Student;
import CourseEnrolment.Student.WeeklySchedule;
import CourseEnrolment.Student.WeeklyScheduleItem;

import java.util.ArrayList;
import java.util.List;

public class SystemManager {
    private List<Offering> offerings = new ArrayList<>();
    private List<Student> students = new ArrayList<>();
    private WeeklySchedule studentWeeklySchedule = new WeeklySchedule();


    private boolean doesOfferingExist(Offering offering) {
        for (Offering offeringItem : offerings)
            if (offeringItem.isCopy(offering))
                return true;
        return false;
    }

    private boolean doesStudentExist(String studentId) {
        for (Student studentItem : students)
            if (studentItem.isCopy(studentId))
                return true;
        return false;
    }

    public void addOffering(Offering offering) throws Exception {
        if (doesOfferingExist(offering))
            throw new Exception("DuplicateOffering");
        offerings.add(offering);
    }

    public void addStudent(Student student) throws Exception {
        if (doesStudentExist(student.getStudentId()))
            throw new Exception("DuplicateStudent");
        students.add(student);
    }

    public void printOfferings() {
        if (offerings.size() == 0) {
            System.out.println("EmptyOfferings");
        }
        for (Offering offering : offerings) {
            offering.print();
        }
    }

    public Offering getOffering(String offeringCode) throws Exception {
        for (Offering offering : offerings) {
            if (offering.getCode().equals(offeringCode)) {
                return offering;
            }
        }
        throw new Exception("OfferingNotFound");
    }

    public Student getStudent(String studentId) throws Exception {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        throw new Exception("StudentNotFound");
    }

    public void addToWeeklySchedule(WeeklyScheduleItem weeklyScheduleItem) throws Exception {
        studentWeeklySchedule.addToWeeklySchedule(weeklyScheduleItem);
    }

    public void getWeeklySchedule(Student student) throws Exception{
        studentWeeklySchedule.getStudentWeeklySchedule(student);
    }

    public void removeFromWeeklySchedule(Student student, Offering offering) throws Exception{
        studentWeeklySchedule.emptyWeeklySchedule(student , offering);
    }

    public void finalizeWeeklySchedule(Student student) throws Exception{
        studentWeeklySchedule.finalizeWeeklySchedule(student);
    }
}