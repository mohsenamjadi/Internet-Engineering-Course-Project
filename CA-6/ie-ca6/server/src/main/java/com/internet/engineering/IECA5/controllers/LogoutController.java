package com.internet.engineering.IECA5.controllers;

import com.google.gson.Gson;
import com.internet.engineering.IECA5.domain.CourseEnrolment.CourseEnrolment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

@RestController
@RequestMapping("/logout")
public class LogoutController {

    @GetMapping("")
    public void logout(final HttpServletResponse response) throws IOException {
        if (CourseEnrolment.getInstance().getStudent() == null)
            response.sendRedirect("http://localhost:8080/login");
    }

    @PostMapping("")
    public String logout_(final HttpServletResponse response) throws IOException {
        try {
            CourseEnrolment.getInstance().nullStudent();
            response.sendRedirect("http://localhost:8080/login");
            return Config.OK_RESPONSE;
        } catch (Exception e) {
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return null;
        }
    }
}