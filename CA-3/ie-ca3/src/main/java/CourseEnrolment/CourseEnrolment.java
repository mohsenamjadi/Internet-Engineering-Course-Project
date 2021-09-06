package CourseEnrolment;

import CourseEnrolment.Student.Grade;
import CourseEnrolment.Student.Student;
import CourseEnrolment.Student.WeeklyScheduleItem;
import CourseEnrolment.System.Offering;
import CourseEnrolment.System.SystemManager;
import HTTPRequestHandler.HTTPRequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class CourseEnrolment {
    private SystemManager systemManager = new SystemManager();

    private static CourseEnrolment instance;


    private CourseEnrolment() { }

    public static CourseEnrolment getInstance() {
        if (instance == null){
            instance = new CourseEnrolment();

            try {
                importStudentsFromWeb();
                importCoursesFromWeb();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return instance;
    }


    public void addOffering(Offering offering) throws Exception {
        systemManager.addOffering(offering);
    }

    public void addStudent(Student student) throws Exception {
        systemManager.addStudent(student);
    }

    public void addGrade(Student student) throws Exception {
        systemManager.addStudent(student);
    }

    public void printOfferings() {
        systemManager.printOfferings();
    }

    public Offering getOffering(String offeringCode, String classCode) throws Exception {
        return systemManager.getOffering(offeringCode, classCode);
    }

    public Offering getOffering(String offeringCode) throws Exception {
        return systemManager.getOffering(offeringCode);
    }

    public List<Offering> getCourses() throws Exception {
        return systemManager.getCourses();
    }

    public Student getStudent(String studnetId) throws Exception {
        return systemManager.getStudent(studnetId);
    }

    public Student getStudent() {
        return systemManager.getStudent();
    }

    public void setStudent(String studentId) throws Exception {
        systemManager.setStudent(studentId);

        if (getStudent().getGrades().isEmpty())
            importStudentGradesFromWeb();
    }

    public void nullStudent() {
        systemManager.nullStudent();
    }

    public void addToWeeklySchedule(String studentId, String code, String classCode) throws Exception {
        Student student = getStudent(studentId);
        Offering offering = getOffering(code, classCode);
        WeeklyScheduleItem weeklyScheduleItem = new WeeklyScheduleItem(student, offering);
        systemManager.addToWeeklySchedule(weeklyScheduleItem);
    }

    public void removeFromWeeklySchedule(String studentId, String code, String classCode) throws Exception{
        Student student = getStudent(studentId);
        Offering offering = getOffering(code, classCode);
        systemManager.removeFromWeeklySchedule(student , offering);
    }

    public List<WeeklyScheduleItem> getWeeklySchedule(String studentId) throws Exception{
        Student student = getStudent(studentId);
        return systemManager.getWeeklySchedule(student);
    }

    public void finalizeWeeklySchedule(String studentId) throws Exception{
        Student student = getStudent(studentId);
        systemManager.finalizeWeeklySchedule(student);
    }

    public List<Offering> searchCourses(String str) {
        return systemManager.searchCourses(str);
    }

    public void emptyWeeklySchedule (String studentId) throws Exception {
        Student student = getStudent(studentId);
        systemManager.emptyWeeklySchedule(student);
    }

    private static void importStudentGradesFromWeb() throws Exception {
        String GradesJsonString = HTTPRequestHandler.getRequest("http://138.197.181.131:5000/api/grades/"+CourseEnrolment.getInstance().getStudent().getStudentId());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Grade> grades = gson.fromJson(GradesJsonString, new TypeToken<List<Grade>>() {
        }.getType());
        for (Grade grade : grades) {
            try {
                CourseEnrolment.getInstance().getStudent().addGrade(grade);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void importCoursesFromWeb() throws Exception {
        String CoursesJsonString = HTTPRequestHandler.getRequest("http://138.197.181.131:5000/api/courses");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Offering> courses = gson.fromJson(CoursesJsonString, new TypeToken<List<Offering>>() {
        }.getType());
        for (Offering course : courses) {
            try {
                CourseEnrolment.getInstance().addOffering(course);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void importStudentsFromWeb() throws Exception {
        String StudentsJsonString = HTTPRequestHandler.getRequest("http://138.197.181.131:5000/api/students");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Student> students = gson.fromJson(StudentsJsonString, new TypeToken<List<Student>>() {
        }.getType());
        for (Student student : students) {
            try {
                CourseEnrolment.getInstance().addStudent(student);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}