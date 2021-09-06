package com.internet.engineering.IECA5.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.internet.engineering.IECA5.domain.CourseEnrolment.CourseEnrolment;
import com.internet.engineering.IECA5.domain.CourseEnrolment.Student.Student;
import com.internet.engineering.IECA5.domain.CourseEnrolment.System.Offering;
import com.internet.engineering.IECA5.utils.HTTPRequestHandler.HTTPRequestHandler;

import java.util.ArrayList;
import java.util.List;

public class CourseEnrolmentService {

    private static CourseEnrolmentService instance;


    private CourseEnrolmentService() {}

    public static CourseEnrolmentService getInstance() {
        if (instance == null) {
            instance = new CourseEnrolmentService();
        }
        return instance;
    }


    public static void importCoursesFromWeb() throws Exception {
        String CoursesJsonString = HTTPRequestHandler.getRequest("http://138.197.181.131:5100/api/courses");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Offering> courses = gson.fromJson(CoursesJsonString, new TypeToken<List<Offering>>() {
        }.getType());
        for (Offering course : courses) {
            try {
                CourseEnrolment.getInstance().addOffering(course);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public static void importStudentsFromWeb() throws Exception {
        String StudentsJsonString = HTTPRequestHandler.getRequest("http://138.197.181.131:5100/api/students");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Student> students = gson.fromJson(StudentsJsonString, new TypeToken<List<Student>>() {
        }.getType());
        for (Student student : students) {
            try {
                CourseEnrolment.getInstance().addStudent(student);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


}
