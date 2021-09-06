package CourseEnrolment;

import CourseEnrolment.Student.Student;
import CourseEnrolment.Student.WeeklyScheduleItem;
import CourseEnrolment.System.Offering;
import CourseEnrolment.System.SystemManager;

public class Controller {
    private SystemManager systemManager = new SystemManager();


    public void addOffering(Offering offering) throws Exception {
        systemManager.addOffering(offering);
    }

    public void addStudent(Student student) throws Exception {
        systemManager.addStudent(student);
    }

    public void printOfferings() {
        systemManager.printOfferings();
    }

    public Offering getOffering(String offeringCode) throws Exception {
        return systemManager.getOffering(offeringCode);
    }

    public Student getStudent(String studnetId) throws Exception {
        return systemManager.getStudent(studnetId);
    }


    public void addToWeeklySchedule(String studentId, String code) throws Exception {
        Student student = getStudent(studentId);
        Offering offering = getOffering(code);
        WeeklyScheduleItem weeklyScheduleItem = new WeeklyScheduleItem(student, offering);
        systemManager.addToWeeklySchedule(weeklyScheduleItem);
    }

    public void removeFromWeeklySchedule(String studentId, String code) throws Exception{
        Student student = getStudent(studentId);
        Offering offering = getOffering(code);
        systemManager.removeFromWeeklySchedule(student , offering);
    }

    public void getWeeklySchedule(String studentId) throws Exception{
        Student student = getStudent(studentId);
        systemManager.getWeeklySchedule(student);
    }

    public void finalizeWeeklySchedule(String studentId) throws Exception{
        Student student = getStudent(studentId);
        systemManager.finalizeWeeklySchedule(student);
    }
}
