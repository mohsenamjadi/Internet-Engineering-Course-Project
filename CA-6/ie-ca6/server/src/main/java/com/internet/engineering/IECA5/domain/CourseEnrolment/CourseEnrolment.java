package com.internet.engineering.IECA5.domain.CourseEnrolment;

import com.internet.engineering.IECA5.domain.CourseEnrolment.Student.Grade;
import com.internet.engineering.IECA5.domain.CourseEnrolment.Student.Student;
import com.internet.engineering.IECA5.domain.CourseEnrolment.Student.WeeklyScheduleItem;
import com.internet.engineering.IECA5.domain.CourseEnrolment.System.Device;
import com.internet.engineering.IECA5.domain.CourseEnrolment.System.Offering;
import com.internet.engineering.IECA5.domain.CourseEnrolment.System.Prerequisite;
import com.internet.engineering.IECA5.domain.CourseEnrolment.System.SystemManager;
import com.internet.engineering.IECA5.repository.MzRepository;
import com.internet.engineering.IECA5.utils.HTTPRequestHandler.HTTPRequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.sql.SQLException;
import java.util.List;

public class CourseEnrolment {
    private SystemManager systemManager = new SystemManager();

    private static CourseEnrolment instance;


    private CourseEnrolment() { }

    public static CourseEnrolment getInstance() {
        if (instance == null){
            instance = new CourseEnrolment();

//            try {
//                importStudentsFromWeb();
//                importCoursesFromWeb();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }

        return instance;
    }

    public void addOffering(Offering offering) throws Exception {
        systemManager.addOffering(offering);
    }

    public void addDevice(Device device) throws Exception {
        systemManager.addDevice(device);
    }

    public void removeDevice(String deviceId) throws Exception {
        systemManager.removeDevice(deviceId);
    }

    public void addStudent(Student student) throws Exception {
        systemManager.addStudent(student);
    }

    public Offering getOffering(String offeringCode, String classCode) throws Exception {
        return systemManager.getOffering(offeringCode, classCode);
    }

    public Offering getOffering(String offeringCode) throws Exception {
        return systemManager.getOffering(offeringCode);
    }

    public Device getDevice(String deviceId) throws Exception {
        return systemManager.getDevice(deviceId);
    }

    public List<Offering> getCourses() throws Exception {
        return systemManager.getCourses();
    }

    public Student getStudent(String studnetId) throws Exception {
        return systemManager.getStudent(studnetId);
    }

    public Student getStudent() {
        return systemManager.getStudent();
    }

    public void setStudent(String studentId) throws Exception {
        systemManager.setStudent(studentId);

        if (getStudent().getGrades().isEmpty())
            importStudentGradesFromWeb();
    }

    public void nullStudent() {
        systemManager.nullStudent();
    }

    public void addToWeeklySchedule(String studentId, String code, String classCode) throws Exception {
        Student student = getStudent(studentId);
        Offering offering = getOffering(code, classCode);
        WeeklyScheduleItem weeklyScheduleItem = new WeeklyScheduleItem(student, offering);
        systemManager.addToWeeklySchedule(weeklyScheduleItem);
    }

    public void addToWeeklySchedule(WeeklyScheduleItem weeklyScheduleItem) throws Exception {
        systemManager.addToWeeklySchedule(weeklyScheduleItem);
    }

    public void removeFromWeeklySchedule(String studentId, String code, String classCode) throws Exception{
        Student student = getStudent(studentId);
        Offering offering = getOffering(code, classCode);
        systemManager.removeFromWeeklySchedule(student, offering);
    }

    public List<WeeklyScheduleItem> getWeeklySchedule(String studentId, boolean flag) throws Exception{
        Student student = getStudent(studentId);
        return systemManager.getWeeklySchedule(student, flag);
    }

    public void finalizeWeeklySchedule(String studentId) throws Exception{
        Student student = getStudent(studentId);
        systemManager.finalizeWeeklySchedule(student);
    }

    public List<Offering> searchCourses(String str) throws SQLException {
        return systemManager.searchCourses(str);
    }

    public List<Device> searchDevices(String str) throws SQLException {
        return systemManager.searchDevices(str);
    }

    public List<Offering> searchCoursesByType(String str) throws SQLException{
        return systemManager.searchCoursesByType(str);
    }

    public void emptyWeeklySchedule (String studentId) throws Exception {
        Student student = getStudent(studentId);
        systemManager.emptyWeeklySchedule(student);
    }

    private static void importStudentGradesFromWeb() throws Exception {
        String GradesJsonString = HTTPRequestHandler.getRequest("http://138.197.181.131:5100/api/grades/"+CourseEnrolment.getInstance().getStudent().getStudentId());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Grade> grades = gson.fromJson(GradesJsonString, new TypeToken<List<Grade>>() {
        }.getType());
        for (Grade grade : grades) {
            try {
                grade.setStudentId(
                    CourseEnrolment.getInstance().getStudent().getStudentId()
                );
                CourseEnrolment.getInstance().getStudent().insertGrade(grade);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}