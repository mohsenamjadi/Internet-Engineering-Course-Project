//package com.internet.engineering.IECA7.controllers;
//
//import com.internet.engineering.IECA7.domain.CourseEnrolment.CourseEnrolment;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/logout")
//public class LogoutController {
//
//
//    @PostMapping("")
//    public String logout(final HttpServletResponse response) throws IOException {
//        try {
//            CourseEnrolment.getInstance().nullStudent();
//            response.sendRedirect("http://localhost:8080/login");
//            return Config.OK_RESPONSE;
//        } catch (Exception e) {
//            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
//            return null;
//        }
//    }
//}