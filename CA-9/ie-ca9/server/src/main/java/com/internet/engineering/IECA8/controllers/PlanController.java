package com.internet.engineering.IECA8.controllers;
//

import com.google.gson.GsonBuilder;
import com.internet.engineering.IECA8.domain.CourseEnrolment.CourseEnrolment;

import com.google.gson.Gson;
import com.internet.engineering.IECA8.domain.CourseEnrolment.System.Offering;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;


@RestController
@RequestMapping("/plan")
public class PlanController {

    @GetMapping("")
    public String getStudentWeeklySchedule(final HttpServletResponse response) throws IOException {
//    public List<WeeklyScheduleItem> getStudentWeeklySchedule(final HttpServletResponse response) throws IOException {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        try {
            return gson.toJson(CourseEnrolment.getInstance().getStudent().getLastSubmit());
            //            return CourseEnrolment.getInstance().getStudent().getLastSubmit();
        } catch (Exception e) {
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return null;
        }
    }

    @PostMapping("/resolveCourse")
    public Offering resolveCourse(@RequestBody(required = true) String jsonString, final HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        try {
            Properties properties = gson.fromJson(jsonString, Properties.class);
            String day = properties.getProperty("day");
            String time = properties.getProperty("time");
            return CourseEnrolment.getInstance().getStudent().resolveCourse(day, time);
        } catch (Exception e) {
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return null;
        }
    }
}