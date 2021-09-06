package com.internet.engineering.IECA5.domain.CourseEnrolment.System;

import com.internet.engineering.IECA5.domain.CourseEnrolment.CourseEnrolment;
import com.internet.engineering.IECA5.domain.CourseEnrolment.Student.Grade;
import com.internet.engineering.IECA5.domain.CourseEnrolment.Student.Student;
import com.internet.engineering.IECA5.domain.CourseEnrolment.Student.WeeklySchedule;
import com.internet.engineering.IECA5.domain.CourseEnrolment.Student.WeeklyScheduleItem;
import com.internet.engineering.IECA5.repository.MzRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class SystemManager {
//    private List<Offering> offerings = new ArrayList<>();
//    private List<Student> students = new ArrayList<>();
    private WeeklySchedule studentWeeklySchedule = new WeeklySchedule();
    private Student student = null;

    private List<Offering> getAllOfferings() throws SQLException {
        return MzRepository.getInstance().getAllOfferings();
    }

    private List<Device> getAllDevices() throws SQLException {
        return MzRepository.getInstance().getAllDevices();
    }

    private List<Student> getAllOStudents() throws SQLException {
        return MzRepository.getInstance().getAllOStudents();
    }

    private boolean doesOfferingExist(Offering offering) throws SQLException{
        List<Offering> offerings = getAllOfferings();

        for (Offering offeringItem : offerings)
            if (offeringItem.isCopy(offering))
                return true;
        return false;
    }

    private boolean doesDeviceExist(Device device) throws SQLException{
        List<Device> devices = getAllDevices();

        for (Device deviceItem : devices)
            if (deviceItem.isCopy(device))
                return true;
        return false;
    }

    private boolean doesStudentExist(String studentId) throws SQLException{
        List<Student> students = getAllOStudents();

        for (Student studentItem : students)
            if (studentItem.isCopy(studentId))
                return true;
        return false;
    }

//    public void addOffering(Offering offering) throws Exception {
//        if (doesOfferingExist(offering))
//            throw new Exception("DuplicateOffering");
//        offerings.add(offering);
//    }

    public void addOffering(Offering offering) throws Exception {
        if (doesOfferingExist(offering))
            throw new Exception("DuplicateOffering");

        MzRepository.getInstance().insertCourse(offering);
    }

    public void addDevice(Device device) throws Exception {
//        if (doesDeviceExist(device))
//            throw new Exception("DuplicateOffering");

        MzRepository.getInstance().insertDevice(device);
    }

    public void removeDevice(String deviceId) throws Exception {
        MzRepository.getInstance().deleteDevice(deviceId);
    }

    public List<Offering> getCourses() throws SQLException{
        return getAllOfferings();
    }

//    public void addStudent(Student student) throws Exception {
//        if (doesStudentExist(student.getStudentId()))
//            throw new Exception("DuplicateStudent");
//        students.add(student);
//    }

    public void addStudent(Student student) throws Exception {
        if (doesStudentExist(student.getStudentId()))
            throw new Exception("DuplicateStudent");

        MzRepository.getInstance().insertStudent(student);
    }

    public Offering getOffering(String offeringCode, String classCode) throws SQLException {
        Offering course = MzRepository.getInstance().findCourseById(offeringCode, classCode);
        List<Offering> courses = new ArrayList<>();
        courses.add(course);
        setClassTimeAndPrerequisites(courses);

        return course;
    }

    public Offering getOffering(String offeringCode) throws Exception {
        Offering course = MzRepository.getInstance().getOfferingByCode(offeringCode);
        List<Offering> courses = new ArrayList<>();
        courses.add(course);
        setClassTimeAndPrerequisites(courses);

        return course;
    }

    public Device getDevice(String deviceId) throws Exception {
        Device device = MzRepository.getInstance().getDeviceById(deviceId);

        return device;
    }

    public Student getStudent(String studentId) throws Exception {
        List<Student> students = getAllOStudents();

        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                student.getTerms();
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
        student.getTerms();
    }

    public void nullStudent() {
        student = null;
    }

    public void addToWeeklySchedule(WeeklyScheduleItem weeklyScheduleItem) throws Exception {
        studentWeeklySchedule.addToWeeklySchedule(weeklyScheduleItem);
    }

    public List<WeeklyScheduleItem> getWeeklySchedule(Student student, boolean flag) throws Exception{
        return studentWeeklySchedule.getStudentWeeklySchedule(student, flag);
    }

    public void removeFromWeeklySchedule(Student student, Offering offering) throws Exception{
        studentWeeklySchedule.removeFromWeeklySchedule(student, offering);
    }

    public void finalizeWeeklySchedule(Student student) throws Exception{
        studentWeeklySchedule.finalizeWeeklySchedule(student);
    }

    public void setClassTimeAndPrerequisites(List<Offering> courses) {
        courses.
                stream().forEach(course -> {
            try {
                List<Prerequisite> prerequisites = MzRepository.getInstance().search(course.getCode(), course.getClassCode());
                course.setPrerequisites(prerequisites);

                List<Day> days = MzRepository.getInstance().searchDays(course.getCode(), course.getClassCode());
                List<String> daysString = days.stream().map(Day::getDay)
                        .collect(Collectors.toList());
                course.setClassTime(new ClassTime(daysString, course.getTime()));
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });
    }

    public List<Offering> searchCourses(String str) throws SQLException
    {
//        Restaurant restaurant = new RestaurantMapper().find(restaurantId);
//        List<Food> menu = MzRepository.getInstance().getRestaurantMenu(restaurantId);
//        restaurant.setMenu(menu);
//
//        return restaurant;

        if (str.isEmpty())
            return getAllOfferings();

        List<Offering> filteredCourses = MzRepository.getInstance().searchCors(str);

        setClassTimeAndPrerequisites(filteredCourses);

        return filteredCourses;
//        List<Offering> offerings = getAllOfferings();
//
//        if (str.isEmpty())
//            return offerings;
//
//        List<Offering> filteredCourses = new ArrayList<>();
//
//        Iterator<Offering> iter = offerings.iterator();
//        while(iter.hasNext()) {
//            Offering offering = iter.next();
//            if (offering.getName().contains(str))
//                filteredCourses.add(offering);
//        }
//
//        return filteredCourses;
    }

    public List<Device> searchDevices(String str) throws SQLException
    {
        if (str.isEmpty())
            return getAllDevices();

        List<Device> filteredDevices = MzRepository.getInstance().searchDevices(str);


        return filteredDevices;

    }

    public List<Offering> searchCoursesByType(String str) throws SQLException
    {
        List<Offering> offerings = getAllOfferings();

        if (str.isEmpty())
            return offerings;

        List<Offering> filteredCourses = new ArrayList<>();

        Iterator<Offering> iter = offerings.iterator();
        while(iter.hasNext()) {
            Offering offering = iter.next();
            if (offering.getType().contains(str)){
                filteredCourses.add(offering);
            }
        }

        return filteredCourses;
    }

    public void emptyWeeklySchedule (Student student) throws Exception {
        studentWeeklySchedule.emptyWeeklySchedule(student);
    }
}