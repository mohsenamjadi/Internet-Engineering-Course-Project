package com.internet.engineering.IECA7.controllers;

import com.google.gson.GsonBuilder;
import com.internet.engineering.IECA7.domain.CourseEnrolment.CourseEnrolment;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/studentProfile")
    public String getStudent(final HttpServletResponse response) throws IOException {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        try {
            return gson.toJson(CourseEnrolment.getInstance().getStudent());
        } catch (Exception e) {
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return null;
        }
    }

    @GetMapping("/studentProfile/gpa")
    public Double getStudentGPA(final HttpServletResponse response) throws IOException {
        try {
            return CourseEnrolment.getInstance().getStudent().getGpa();
        } catch (Exception e) {
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return null;
        }
    }

    @GetMapping("/studentProfile/totalPassed")
    public Double getStudentTotalPassedUnits(final HttpServletResponse response) throws IOException {
        try {
            return CourseEnrolment.getInstance().getStudent().getTotalPassedUnits();
        } catch (Exception e) {
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return null;
        }
    }
}