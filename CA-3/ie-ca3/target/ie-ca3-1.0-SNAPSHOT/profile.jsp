<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="CourseEnrolment.CourseEnrolment"  %>
<%@ page import="CourseEnrolment.Student.Grade" %>
<%@ page import="java.util.List" %>
<%@ page import="CourseEnrolment.Student.Grade" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Profile</title>
    <style>
        li {
            padding: 5px
        }
        table{
            width: 10%;
            text-align: center;
        }
    </style>
</head>
<body>
<a href="/">Home</a>
<ul>
    <li id="std_id">Student Id: <%= CourseEnrolment.getInstance().getStudent().getStudentId() %></li>
    <li id="first_name">First Name: <%= CourseEnrolment.getInstance().getStudent().getName() %></li>
    <li id="last_name">Last Name: <%= CourseEnrolment.getInstance().getStudent().getSecondName() %></li>
    <li id="birthdate">Birthdate: <%= CourseEnrolment.getInstance().getStudent().getBirthDate() %></li>
    <li id="gpa">GPA: <%= CourseEnrolment.getInstance().getStudent().getGpa() %></li>
    <li id="tpu">Total Passed Units: <%= CourseEnrolment.getInstance().getStudent().getTotalPassedUnits() %></li>
</ul>
<table>
    <%
        List<Grade> studentGradeslist = CourseEnrolment.getInstance().getStudent().getGrades();
    %>
    <tr>
        <th>Code</th>
        <th>Grade</th>
    </tr>
    <%
        for(Grade eachCourseGrade: studentGradeslist){
    %>
    <tr>
        <td><%= eachCourseGrade.getCode() %></td>
        <td><%= eachCourseGrade.getGrade() %></td>
    </tr>
    <%}%>
</table>
</body>
</html>