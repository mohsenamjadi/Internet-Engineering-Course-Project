package com.internet.engineering.IECA5.controllers;

import com.google.gson.GsonBuilder;
import com.internet.engineering.IECA5.domain.CourseEnrolment.CourseEnrolment;
import com.internet.engineering.IECA5.domain.CourseEnrolment.Student.Student;
import com.internet.engineering.IECA5.domain.CourseEnrolment.Student.Grade;

import com.google.gson.Gson;
import com.internet.engineering.IECA5.domain.CourseEnrolment.Student.Term;
import com.internet.engineering.IECA5.domain.CourseEnrolment.System.Offering;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/studentProfile")
//    public Student getStudent(final HttpServletResponse response) throws IOException {
    public String getStudent(final HttpServletResponse response) throws IOException {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        try {
            if(CourseEnrolment.getInstance().getStudent() == null) {
                response.sendRedirect("http://localhost:8080/login");
                return null;
            }
            else{
                CourseEnrolment.getInstance().getStudent().getTerms();
                //                return CourseEnrolment.getInstance().getStudent();
                return gson.toJson(CourseEnrolment.getInstance().getStudent());
            }
        } catch (Exception e) {
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return null;
        }
    }

    @GetMapping("/studentProfile/gpa")
    public Double getStudentGPA(final HttpServletResponse response) throws IOException {
        try {
            if(CourseEnrolment.getInstance().getStudent() == null) {
                response.sendRedirect("http://localhost:8080/login");
                return null;
            }
            else{
                return CourseEnrolment.getInstance().getStudent().getGpa();
            }
        } catch (Exception e) {
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return null;
        }
    }

    @GetMapping("/studentProfile/totalPassed")
    public Double getStudentTotalPassedUnits(final HttpServletResponse response) throws IOException {
        try {
            if(CourseEnrolment.getInstance().getStudent() == null) {
                response.sendRedirect("http://localhost:8080/login");
                return null;
            }
            else{
                return CourseEnrolment.getInstance().getStudent().getTotalPassedUnits();
            }
        } catch (Exception e) {
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return null;
        }
    }
}