package controllers;

import CourseEnrolment.CourseEnrolment;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "Login", urlPatterns = "/login")
public class Login extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if(CourseEnrolment.getInstance().getStudent() != null) {
                response.sendRedirect("http://localhost:8080/");
            } else {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
                requestDispatcher.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String studentId = request.getParameter("std_id");

        response.setContentType("text/html; charset=UTF-8");

        try {
            CourseEnrolment.getInstance().setStudent(studentId);
            response.setStatus(200);
            response.getWriter().println("login Success");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(400, e.getMessage());
        }
    }
}