package CourseEnrolment.System;

import CourseEnrolment.Student.Student;
import CourseEnrolment.Student.WeeklySchedule;
import CourseEnrolment.Student.WeeklyScheduleItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SystemManager {
    private List<Offering> offerings = new ArrayList<>();
    private List<Student> students = new ArrayList<>();
    private WeeklySchedule studentWeeklySchedule = new WeeklySchedule();
    private Student student = null;


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

    public List<Offering> getCourses() {
        return offerings;
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

    public Offering getOffering(String offeringCode, String classCode) throws Exception {
        for (Offering offering : offerings) {
            if (offering.getCode().equals(offeringCode) && offering.getClassCode().equals(classCode)) {
                return offering;
            }
        }
        throw new Exception("OfferingNotFound");
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(String studentId) throws Exception {
        student = getStudent(studentId);
    }

    public void nullStudent() {
        student = null;
    }

    public void addToWeeklySchedule(WeeklyScheduleItem weeklyScheduleItem) throws Exception {
        studentWeeklySchedule.addToWeeklySchedule(weeklyScheduleItem);
    }

    public List<WeeklyScheduleItem> getWeeklySchedule(Student student) throws Exception{
        return studentWeeklySchedule.getStudentWeeklySchedule(student);
    }

    public void removeFromWeeklySchedule(Student student, Offering offering) throws Exception{
        studentWeeklySchedule.removeFromWeeklySchedule(student , offering);
    }

    public void finalizeWeeklySchedule(Student student) throws Exception{
        studentWeeklySchedule.finalizeWeeklySchedule(student);
    }

    public List<Offering> searchCourses(String str)
    {
        if (str.isEmpty())
            return offerings;

        List<Offering> filteredCourses = new ArrayList<>();

        Iterator<Offering> iter = offerings.iterator();
        while(iter.hasNext()) {
            Offering offering = iter.next();
            if (offering.getName().contains(str))
                filteredCourses.add(offering);
        }

        return filteredCourses;
    }

    public void emptyWeeklySchedule (Student student) throws Exception {
        studentWeeklySchedule.emptyWeeklySchedule(student);
    }
}