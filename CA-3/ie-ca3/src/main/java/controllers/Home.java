package controllers;

import CourseEnrolment.CourseEnrolment;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "Home", urlPatterns = "/")
public class Home extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if(CourseEnrolment.getInstance().getStudent() == null)
                response.sendRedirect("http://localhost:8080/login");
            else{
                request.setAttribute("std_id", CourseEnrolment.getInstance().getStudent().getStudentId());
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("home.jsp");
                requestDispatcher.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}