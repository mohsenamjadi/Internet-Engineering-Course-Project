<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="CourseEnrolment.CourseEnrolment"  %>
<%@ page import="CourseEnrolment.System.Offering" %>
<%@ page import="CourseEnrolment.Student.WeeklyScheduleItem" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Courses</title>
    <style>
        .course_table {
            width: 100%;
            text-align: center;
        }
        .search_form {
            text-align: center;
        }
    </style>
</head>
<body>
    <a href="/">Home</a>
    <li id="code">Student Id: <%= request.getAttribute("std_id") %></li>
    <li id="units">Total Selected Units: <%= request.getAttribute("totalSelectedUnits") %></li>

    <br>

    <%
        List<WeeklyScheduleItem> studentWeeklySchedule = CourseEnrolment.getInstance().getStudent().getSelectedCourses();
    %>
    <table>
        <tr>
            <th>Code</th>
            <th>Class Code</th> 
            <th>Name</th>
            <th>Units</th>
            <th></th>
        </tr>
        <%
            for(WeeklyScheduleItem weeklyScheduleItem: studentWeeklySchedule){
        %>
        <tr>
            <td><%=weeklyScheduleItem.getOffering().getCode()%></td>
            <td><%=weeklyScheduleItem.getOffering().getClassCode()%></td>
            <td><%=weeklyScheduleItem.getOffering().getName()%></td>
            <td><%=weeklyScheduleItem.getOffering().getUnits()%></td>
            <td>
                <form action="" method="POST" >
                    <input id="form_action" type="hidden" name="action" value="remove">
                    <input id="form_course_code" type="hidden" name="course_code" value="<%=weeklyScheduleItem.getOffering().getCode()%>">
                    <input id="form_class_code" type="hidden" name="class_code" value="<%=weeklyScheduleItem.getOffering().getClassCode()%>">
                    <button type="submit">Remove</button>
                </form>
            </td>
        </tr>
        <%}%>
    </table>

    <br>

    <form action="" method="POST">
        <button type="submit" name="action" value="submit">Submit Plan</button>
        <button type="submit" name="action" value="reset">Reset</button>
    </form>

    <br>

    <form class="search_form" action="" method="POST">
        <label>Search:</label>
        <input type="text" name="search" value="<%= CourseEnrolment.getInstance().getStudent().getSearchFilter() %>">
        <button type="submit" name="action" value="search">Search</button>
        <button type="submit" name="action" value="clear">Clear Search</button>
    </form>



    <br>

    <%
        List<Offering> coursesList = CourseEnrolment.getInstance().searchCourses(
                CourseEnrolment.getInstance().getStudent().getSearchFilter()
        );
    %>
    <table class="course_table">
        <tr>
            <th>Code</th>
            <th>Class Code</th> 
            <th>Name</th>
            <th>Units</th>
            <th>Signed Up</th>
            <th>Capacity</th>
            <th>Type</th>
            <th>Days</th>
            <th>Time</th>
            <th>Exam Start</th>
            <th>Exam End</th>
            <th>Prerequisites</th>
            <th></th>
        </tr>
        <%
            for (Offering course: coursesList) {
        %>
        <tr>
            <td><%=course.getCode()%></td>
            <td><%=course.getClassCode()%></td>
            <td><%=course.getName()%></td>
            <td><%=course.getUnits()%></td>
            <td><%=course.getSignedUp()%></td>
            <td><%=course.getCapacity()%></td>
            <td><%=course.getType()%></td>
            <td><%=course.getClassTime().getDays()%></td>
            <td><%=course.getClassTime().getTime()%></td>
            <td><%=course.getExamTime().getStart()%></td>
            <td><%=course.getExamTime().getEnd()%></td>
            <td><%=course.getPrerequisites()%></td>
            <td>
                <form action="" method="POST" >
                    <input id="form_action" type="hidden" name="action" value="add">
                    <input id="form_class_code" type="hidden" name="course_code" value="<%=course.getCode()%>">
                    <input id="form_class_code" type="hidden" name="class_code" value="<%=course.getClassCode()%>">
                    <button type="submit">Add</button>
                </form>
            </td>
        </tr>
        <%}%>
    </table>
</body>
</html>