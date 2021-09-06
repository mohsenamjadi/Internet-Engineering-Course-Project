package InterfaceServer;

import CourseEnrolment.Student.WeeklyScheduleItem;
import CourseEnrolment.System.Offering;
import CourseEnrolment.Student.Student;
import CourseEnrolment.Student.Grade;
import HTTPRequestHandler.HTTPRequestHandler;
import CourseEnrolment.CourseEnrolment;
import CourseEnrolment.Utility;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.javalin.Javalin;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class InterfaceServer {
    public InterfaceServer(){
        Utility.Memory.setCourseEnrolment(courseEnrolment);
    }


    private Javalin app;

    private CourseEnrolment courseEnrolment = new CourseEnrolment();


    public CourseEnrolment getEnrollmentSystem() {
        return courseEnrolment;
    }

    public void start(final String COURSES_URI, final String STUDENTS_URI, final String GRADES_URI, final int port) {
        try {
            System.out.println("Importing Courses...");
            importCoursesFromWeb(COURSES_URI);
            System.out.println("Importing Students...");
            importStudentsFromWeb(STUDENTS_URI);
            runServer(port);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void runServer(final int port) throws Exception {
        app = Javalin.create().start(port);

        app.get("/courses", ctx -> {
            try {
                ctx.html(generateGetCoursesPage());
            }catch (Exception e){
                System.out.println(e.getMessage());
                ctx.status(502);
            }
        });

        app.get("/course/:code/:classCode", ctx -> {
            try {
                ctx.html(generateGetCoursePage(ctx.pathParam("code"), ctx.pathParam("classCode")));
            }catch (Exception e){
                System.out.println(e.getMessage());
                ctx.status(502);
            }
        });

        app.get("profile/:id", ctx -> {
            try {
                ctx.html(generateGetStudentProfile(ctx.pathParam("id")));
            }catch (Exception e){
                System.out.println(e.getMessage());
                ctx.status(502).result(":| " + e.getMessage());
            }
        });

        app.post("/course/:code/:classCode", ctx -> {
            String id = "";
            try {
                id = ctx.formParam("std_id");
                courseEnrolment.addToWeeklySchedule(id, ctx.pathParam("code") , ctx.pathParam("classCode") );
                ctx.status(200).result("Added to weekly schedule");
                //ctx.redirect("/plan/"+id);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                ctx.status(403).result(exception.getMessage());
            }
            //ctx.redirect("/profile/"+id);
        });

        app.get("/change_plan/:id", ctx -> {
            try {
                ctx.html(generateGetStudentWeeklySchedule(ctx.pathParam("id")));
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        });

        app.post("/change_plan/:id", ctx -> {
            String courseCodeforRemove = "", courseClassCodeforRemove = "";
            String id = ctx.pathParam("id");
            try {
                courseCodeforRemove = ctx.formParam("course_code");
                courseClassCodeforRemove = ctx.formParam("class_code");
                courseEnrolment.removeFromWeeklySchedule(id, courseCodeforRemove, courseClassCodeforRemove);
                ctx.status(200).result("Removed from weekly schedule");
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                ctx.status(400).result(exception.getMessage());
            }
            ctx.redirect("/change_plan/"+id);
        });

        app.get("/plan/:id", ctx -> {
            try {
                ctx.html(generateGetStudentPlan(ctx.pathParam("id")));
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        });

        app.get("/submit/:id", ctx -> {
            try {
                ctx.html(generateGetSubmitPage(ctx.pathParam("id")));
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                ctx.html(exception.getMessage());
                ctx.status(400);
            }
        });

        app.post("/submit/:id", ctx -> {
            try {
                courseEnrolment.finalizeWeeklySchedule(ctx.pathParam("id"));
                ctx.redirect("/submit_ok");
                //ctx.status(200);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
//                ctx.html(exception.getMessage());
                ctx.redirect("/submit_failed");
                //ctx.status(400);
            }
        });


        app.get("/submit_ok", ctx -> {
            try {
                ctx.html(readResourceFile("submit_ok.html"));
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                ctx.html(exception.getMessage());
                ctx.status(400);
            }
        });

        app.get("/submit_failed", ctx -> {
            try {
                ctx.html(readResourceFile("submit_failed.html"));
                ctx.status(400);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                ctx.html(exception.getMessage());
                ctx.status(400);
            }
        });
    }

    public String generateGetStudentProfile(String id) throws Exception {
        Student student = getEnrollmentSystem().getStudent(id);
        if(student.getGrades().isEmpty())
            importStudentGradesFromWeb("http://138.197.181.131:5000/api/grades/"+id, student);
        List<Grade> grades = student.getGrades();
        HashMap<String, String> context = new HashMap<>();
        context.put("id", student.getStudentId());
        context.put("name", student.getName());
        context.put("secondName", student.getSecondName());
        context.put("birthDate", student.getBirthDate());
        context.put("gpa", Double.toString(student.getGpa()));
        context.put("totalPassedUnits", Integer.toString(student.getTotalPassedUnits()));
        String coursesHTML = HTMLHandler.fillTemplate(readResourceFile("profileBefore.html"), context);

        String profileItemHTML = readResourceFile("profileItem.html");
        for(Grade grade: grades) {
            context.clear();
            context.put("code", grade.getCode());
            context.put("grade", Double.toString(grade.getGrade()));
            coursesHTML += HTMLHandler.fillTemplate(profileItemHTML, context);
        }
        coursesHTML += readResourceFile("profileAfter.html");
        return coursesHTML;
    }

    private String getDays(List<String> daysList){
        String days = "";
        int i=0;
        for(String day : daysList){
            days += day;
            if(i++ != daysList.size()-1)
                days += ", ";
        }

        return days;
    }

    private String getPrerequisites(List<String> prerequisitesList){
        String prerequisites = "";
        int i=0;
        for(String prerequisite : prerequisitesList){
            prerequisites += prerequisite;
            if(i++ != prerequisitesList.size()-1)
                prerequisites += "|";
        }

        return prerequisites;
    }

    public String generateGetCoursesPage() throws Exception{
        String coursesHTML = readResourceFile("coursesBefore.html");
        List<Offering> courses = courseEnrolment.getCourses();
        String courseItemHTML = readResourceFile("coursesItem.html");
        for(Offering course: courses){
            HashMap<String, String> context = new HashMap<>();
            context.put("code", course.getCode());
            context.put("classCode", course.getClassCode());
            context.put("name", course.getName());
            context.put("units", Integer.toString(course.getUnits()));
            context.put("capacity", Integer.toString(course.getCapacity()));
            context.put("type", course.getType());
            context.put("days", getDays(course.getClassTime().getDays()));
            context.put("time", course.getClassTime().getTime());
            context.put("examStart", course.getExamTime().getStart());
            context.put("examEnd", course.getExamTime().getEnd());
            context.put("prerequisites", getPrerequisites(course.getPrerequisites()));
            coursesHTML += HTMLHandler.fillTemplate(courseItemHTML, context);
        }
        coursesHTML += readResourceFile("coursesAfter.html");
        return coursesHTML;
    }

    public String generateGetCoursePage(String code, String classCode) throws Exception{
        HashMap<String, String> context = new HashMap<>();
        context.put("code", code);
        context.put("classCode", classCode);
        context.put("units", Integer.toString(courseEnrolment.getOffering(code, classCode).getUnits()));
        context.put("days", getDays(courseEnrolment.getOffering(code, classCode).getClassTime().getDays()));
        context.put("time", courseEnrolment.getOffering(code, classCode).getClassTime().getTime());
        return HTMLHandler.fillTemplate(readResourceFile("course.html"), context);
    }

    private String generateGetStudentWeeklySchedule(String id) throws Exception {
        String coursesHTML = readResourceFile("change_planBefore.html");
        List<WeeklyScheduleItem> studentWeeklySchedule = courseEnrolment.getWeeklySchedule(id);
        String courseItemHTML = readResourceFile("change_planItem.html");
        for(WeeklyScheduleItem weeklyScheduleItem: studentWeeklySchedule){
            HashMap<String, String> context = new HashMap<>();
            context.put("code", weeklyScheduleItem.getOffering().getCode());
            context.put("classCode", weeklyScheduleItem.getOffering().getClassCode());
            context.put("name", weeklyScheduleItem.getOffering().getName());
            context.put("units", Integer.toString(weeklyScheduleItem.getOffering().getUnits()));
            coursesHTML += HTMLHandler.fillTemplate(courseItemHTML, context);
        }
        coursesHTML += readResourceFile("change_planAfter.html");
        return coursesHTML;
    }

    private String generateGetStudentPlan(String id) throws Exception {
        List<WeeklyScheduleItem> studentWeeklySchedule = courseEnrolment.getWeeklySchedule(id);
        HashMap<String, String> context = new HashMap<>();
        for(WeeklyScheduleItem weeklyScheduleItem: studentWeeklySchedule)
            for(String day: weeklyScheduleItem.getOffering().getClassTime().getDays())
                context.put(day+weeklyScheduleItem.getOffering().getClassTime().getTime(), weeklyScheduleItem.getOffering().getName());
        return HTMLHandler.fillTemplate(readResourceFile("plan.html"), context);
    }

    private String generateGetSubmitPage(String id) throws Exception {
        Student student = getEnrollmentSystem().getStudent(id);
        HashMap<String, String> context = new HashMap<>();
        context.put("id", id);
        context.put("totalUnits", Integer.toString(student.getTotalUnits()));
        return HTMLHandler.fillTemplate(readResourceFile("submit.html"), context);
    }

    private String readResourceFile(String fileName) throws Exception{
        File file = new File(Resources.getResource("templates/" + fileName).toURI());
        return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }

    public void importCoursesFromWeb(String uri) throws Exception {
        String CoursesJsonString = HTTPRequestHandler.getRequest(uri);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Offering> courses = gson.fromJson(CoursesJsonString, new TypeToken<List<Offering>>() {
        }.getType());
        int counter = 1;
        for (Offering course : courses) {
            System.out.println(counter + "----------------");
            counter++;
            course.print();
            try {
                courseEnrolment.addOffering(course);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void importStudentsFromWeb(String uri) throws Exception {
        String StudentsJsonString = HTTPRequestHandler.getRequest(uri);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Student> students = gson.fromJson(StudentsJsonString, new TypeToken<List<Student>>() {
        }.getType());
        int counter = 1;
        for (Student student : students) {
            System.out.println(counter + "----------------");
            counter++;
            student.print();
            try {
                courseEnrolment.addStudent(student);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void importStudentGradesFromWeb(String uri, Student student) throws Exception {
        String GradesJsonString = HTTPRequestHandler.getRequest(uri);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Grade> grades = gson.fromJson(GradesJsonString, new TypeToken<List<Grade>>() {
        }.getType());
        int counter = 1;
        for (Grade grade : grades) {
            System.out.println(counter + "----------------");
            counter++;
            grade.print();
            try {
                student.addGrade(grade);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void stop() {
        app.stop();
    }
}
