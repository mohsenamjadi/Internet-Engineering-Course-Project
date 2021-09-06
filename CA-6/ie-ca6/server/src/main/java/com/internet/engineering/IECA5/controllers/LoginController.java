package com.internet.engineering.IECA5.controllers;

import com.google.gson.Gson;
import com.internet.engineering.IECA5.domain.CourseEnrolment.CourseEnrolment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;


@RestController
@RequestMapping("/login")
public class LoginController {

    @GetMapping("")
    public void login(final HttpServletResponse response) throws IOException {
        if (CourseEnrolment.getInstance().getStudent() != null)
            response.sendRedirect("http://localhost:8080/");
    }

    @PostMapping("")
    public String login(@RequestBody String jsonString, final HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(jsonString, Properties.class);
        try {
            CourseEnrolment.getInstance().setStudent(properties.getProperty("std_id"));
            return Config.OK_RESPONSE;
        } catch (Exception e) {
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return null;
        }
    }
}