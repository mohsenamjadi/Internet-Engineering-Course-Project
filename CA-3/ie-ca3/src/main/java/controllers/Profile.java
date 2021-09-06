package controllers;

import CourseEnrolment.CourseEnrolment;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "Profile", urlPatterns = "/profile")
public class Profile extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if(CourseEnrolment.getInstance().getStudent() == null)
                response.sendRedirect("http://localhost:8080/login");
            else{
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("profile.jsp");
                requestDispatcher.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}