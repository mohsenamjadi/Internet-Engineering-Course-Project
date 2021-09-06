package com.internet.engineering.IECA8.domain.CourseEnrolment.System;

import com.internet.engineering.IECA8.domain.CourseEnrolment.Student.Student;
import com.internet.engineering.IECA8.domain.CourseEnrolment.Student.WeeklySchedule;
import com.internet.engineering.IECA8.domain.CourseEnrolment.Student.WeeklyScheduleItem;
import com.internet.engineering.IECA8.repository.Repository;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class SystemManager {
    private WeeklySchedule studentWeeklySchedule = new WeeklySchedule();

    private List<Offering> getAllOfferings() throws SQLException {
        return Repository.getInstance().getAllOfferings();
    }

    private List<Student> getAllOStudents() throws SQLException {
        return Repository.getInstance().getAllOStudents();
    }

    public void addOffering(Offering offering) throws Exception {
        try {
            Repository.getInstance().insertCourse(offering);
        } catch (SQLException e) {
            throw new Exception("DuplicateOffering");
        }
    }

    public List<Offering> getCourses() throws SQLException{
        return getAllOfferings();
    }

    public void addStudent(Student student) throws Exception {
        try {
            Repository.getInstance().insertStudent(student);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("id is already taken");
        }
    }

    public Offering getOffering(String offeringCode, String classCode) throws SQLException {
        Offering course = Repository.getInstance().findCourseById(offeringCode, classCode);
        List<Offering> courses = new ArrayList<>();
        courses.add(course);
        setClassTimeAndPrerequisites(courses);

        return course;
    }

    public Offering getOffering(String offeringCode) throws Exception {
        Offering course = Repository.getInstance().getOfferingByCode(offeringCode);
        List<Offering> courses = new ArrayList<>();
        courses.add(course);
        setClassTimeAndPrerequisites(courses);

        return course;
    }

    public Student getStudent(String studentId) throws Exception {
        try {
            Student student = Repository.getInstance().getStudent(studentId);
            student.getTerms();
            return student;
        } catch (SQLException sqlException) {
            throw new Exception("StudentNotFound");
        }
    }

    public Student getStudent() throws Exception {
        return getStudent( SecurityContextHolder.getContext().getAuthentication().getName() );
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
                List<Prerequisite> prerequisites = Repository.getInstance().search(course.getCode(), course.getClassCode());
                course.setPrerequisites(prerequisites);

                List<Day> days = Repository.getInstance().searchDays(course.getCode(), course.getClassCode());
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

        List<Offering> filteredCourses = Repository.getInstance().search(str);

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

    public Student loginStudent(String email, String password) throws Exception {
        try {
            Student student = Repository.getInstance().getStudentByEmail(email);
            if (student.getPassword().equals(password))
                return student;
            else
                throw new Exception("passwords does not match");
        } catch (SQLException e) {
            throw new Exception("user not available");
        }
    }
}