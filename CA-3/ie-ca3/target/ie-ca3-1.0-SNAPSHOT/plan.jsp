<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="CourseEnrolment.CourseEnrolment"  %>
<%@ page import="CourseEnrolment.System.Offering" %>
<%@ page import="CourseEnrolment.Student.WeeklyScheduleItem" %>
<%@ page import="java.util.List" %>
<%@ page import="CourseEnrolment.Student.Grade" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="CourseEnrolment.System.ClassTime" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Plan</title>
    <style>
        table{
            width: 100%;
            text-align: center;
            
        }
        table, th, td{
            border: 1px solid black;
            border-collapse: collapse;
        }
    </style>
</head>
<body>
    <a href="/">Home</a>
    <li id="code">Student Id: <%= request.getAttribute("std_id") %></li>

    <br>

    <%
        List<WeeklyScheduleItem> studentWeeklySchedule = CourseEnrolment.getInstance().getStudent().getSelectedCourses();
    %>
    <table>
        <tr>
            <th></th>
            <th>7:30-9:00</th>
            <th>9:00-10:30</th>
            <th>10:30-12:00</th>
            <th>14:00-15:30</th>
            <th>16:00-17:30</th>
        </tr>
        <tr>
            <td>Saturday</td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Saturday", "7:30-9:00") %> </td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Saturday", "9:00-10:30") %> </td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Saturday", "10:30-12:00") %> </td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Saturday", "14:00-15:30") %> </td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Saturday", "16:00-17:30") %> </td>
        </tr>
        <tr>
            <td>Sunday</td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Sunday", "7:30-9:00") %> </td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Sunday", "9:00-10:30") %> </td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Sunday", "10:30-12:00") %> </td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Sunday", "14:00-15:30") %> </td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Sunday", "16:00-17:30") %> </td>
        </tr>
        <tr>
            <td>Monday</td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Monday", "7:30-9:00") %> </td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Monday", "9:00-10:30") %> </td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Monday", "10:30-12:00") %> </td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Monday", "14:00-15:30") %> </td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Monday", "16:00-17:30") %> </td>
        </tr>
        <tr>
            <td>Tuesday</td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Tuesday", "7:30-9:00") %> </td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Tuesday", "9:00-10:30") %> </td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Tuesday", "10:30-12:00") %> </td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Tuesday", "14:00-15:30") %> </td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Tuesday", "16:00-17:30") %> </td>
        </tr>
        <tr>
            <td>Wednesday</td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Wednesday", "7:30-9:00") %> </td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Wednesday", "9:00-10:30") %> </td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Wednesday", "10:30-12:00") %> </td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Wednesday", "14:00-15:30") %> </td>
            <td> <%= CourseEnrolment.getInstance().getStudent().resolveCourse("Wednesday", "16:00-17:30") %> </td>
        </tr>
    </table>
</body>
</html>