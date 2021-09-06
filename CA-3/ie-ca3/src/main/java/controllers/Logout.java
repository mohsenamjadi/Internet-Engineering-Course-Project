package controllers;

import CourseEnrolment.CourseEnrolment;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Logout", urlPatterns = "/logout")
public class Logout extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (CourseEnrolment.getInstance().getStudent() == null)
                response.sendRedirect("http://localhost:8080/login");
            else {
                CourseEnrolment.getInstance().nullStudent();
                response.sendRedirect("http://localhost:8080/login");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}