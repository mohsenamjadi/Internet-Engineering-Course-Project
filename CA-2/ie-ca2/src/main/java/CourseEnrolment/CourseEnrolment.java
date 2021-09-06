package CourseEnrolment;

import CourseEnrolment.Student.Student;
import CourseEnrolment.Student.WeeklyScheduleItem;
import CourseEnrolment.System.Offering;
import CourseEnrolment.System.SystemManager;

import java.util.List;

public class CourseEnrolment {
    private SystemManager systemManager = new SystemManager();


    public void addOffering(Offering offering) throws Exception {
        systemManager.addOffering(offering);
    }

    public void addStudent(Student student) throws Exception {
        systemManager.addStudent(student);
    }

    public void addGrade(Student student) throws Exception {
        systemManager.addStudent(student);
    }

    public void printOfferings() {
        systemManager.printOfferings();
    }

    public Offering getOffering(String offeringCode, String classCode) throws Exception {
        return systemManager.getOffering(offeringCode, classCode);
    }

    public Offering getOffering(String offeringCode) throws Exception {
        return systemManager.getOffering(offeringCode);
    }

    public List<Offering> getCourses() throws Exception {
        return systemManager.getCourses();
    }

    public Student getStudent(String studnetId) throws Exception {
        return systemManager.getStudent(studnetId);
    }


    public void addToWeeklySchedule(String studentId, String code, String classCode) throws Exception {
        Student student = getStudent(studentId);
        Offering offering = getOffering(code, classCode);
        WeeklyScheduleItem weeklyScheduleItem = new WeeklyScheduleItem(student, offering);
        systemManager.addToWeeklySchedule(weeklyScheduleItem);
    }

    public void removeFromWeeklySchedule(String studentId, String code, String classCode) throws Exception{
        Student student = getStudent(studentId);
        Offering offering = getOffering(code, classCode);
        systemManager.removeFromWeeklySchedule(student , offering);
    }

    public List<WeeklyScheduleItem> getWeeklySchedule(String studentId) throws Exception{
        Student student = getStudent(studentId);
        return systemManager.getWeeklySchedule(student);
    }

    public void finalizeWeeklySchedule(String studentId) throws Exception{
        Student student = getStudent(studentId);
        systemManager.finalizeWeeklySchedule(student);
    }
}
