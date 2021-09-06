package controllers;

import CourseEnrolment.CourseEnrolment;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "GetCourses", urlPatterns = "/courses")
public class GetCourses extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (CourseEnrolment.getInstance().getStudent() == null)
                response.sendRedirect("http://localhost:8080/login");
            else {
                request.setAttribute("std_id", CourseEnrolment.getInstance().getStudent().getStudentId());
                request.setAttribute("totalSelectedUnits", CourseEnrolment.getInstance().getStudent().getTotalUnits());
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("courses.jsp");
                requestDispatcher.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        boolean submit = false;

        try {
            if (action.equals("reset")) {
                CourseEnrolment.getInstance().getStudent().setSelectedCourses(
                        CourseEnrolment.getInstance().getStudent().getLastSubmit()
                );
            } else if (action.equals("search")) {
                CourseEnrolment.getInstance().getStudent().setSearchFilter(request.getParameter("search"));
            } else if (action.equals("add")) {
                CourseEnrolment.getInstance().addToWeeklySchedule(
                        CourseEnrolment.getInstance().getStudent().getStudentId(),
                        request.getParameter("course_code"),
                        request.getParameter("class_code")
                );
            } else if (action.equals("remove")) {
                CourseEnrolment.getInstance().removeFromWeeklySchedule(
                        CourseEnrolment.getInstance().getStudent().getStudentId(),
                        request.getParameter("course_code"),
                        request.getParameter("class_code")
                );
            } else if (action.equals("clear")) {
                CourseEnrolment.getInstance().getStudent().setSearchFilter("");
            } else if (action.equals("submit")) {
                CourseEnrolment.getInstance().finalizeWeeklySchedule(
                        CourseEnrolment.getInstance().getStudent().getStudentId()
                );

                CourseEnrolment.getInstance().getStudent().setLastSubmit(
                        CourseEnrolment.getInstance().getStudent().getSelectedCourses()
                );

                submit = true;
                response.sendRedirect("http://localhost:8080/plan");
            }

            if(!submit)
                response.sendRedirect("http://localhost:8080/courses");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(400, e.getMessage());
        }
    }
}