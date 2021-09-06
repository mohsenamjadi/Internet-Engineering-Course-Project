package CourseEnrolment.Student;

import CourseEnrolment.CourseEnrolment;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Student {
    private String id;
    private String name;
    private String secondName;
    private String birthDate;
    private String searchFilter;
    private List<Grade> grades;
    private List<WeeklyScheduleItem> lastSubmit;

    public Student(String id, String name, String secondName, String birthDate) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.birthDate = birthDate;
    }

    public Student() {
        this.grades = new ArrayList<>();
        this.lastSubmit = new ArrayList<>();
        this.searchFilter = "";
    }

    public void setSearchFilter (String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public String getSearchFilter () {
        return searchFilter;
    }

    public List<WeeklyScheduleItem> getSelectedCourses() throws Exception {
        return CourseEnrolment.getInstance().getWeeklySchedule(id);
    }

    public String resolveCourse(String day, String time) throws Exception {
        try {
            return getLastSubmit().stream().
                filter(weeklyScheduleItem -> weeklyScheduleItem.getOffering().getClassTime().getDays().contains(day) && weeklyScheduleItem.getOffering().getClassTime().getTime().equals(time)).
                findFirst().get().
                getOffering().getName();
        } catch (NoSuchElementException e) {
        } catch (NullPointerException e) {
        }

        return "";
    }

    public void setSelectedCourses(List<WeeklyScheduleItem> selectedCourses) throws Exception {
        CourseEnrolment.getInstance().emptyWeeklySchedule(id);
        for (WeeklyScheduleItem weeklyScheduleItem: selectedCourses)
            CourseEnrolment.getInstance().addToWeeklySchedule(
                    id,
                    weeklyScheduleItem.getOffering().getCode(),
                    weeklyScheduleItem.getOffering().getClassCode()
            );
    }

    public List<WeeklyScheduleItem> getLastSubmit() throws Exception {
        return lastSubmit;
    }

    public void setLastSubmit(List<WeeklyScheduleItem> lastSubmit) throws Exception {
        this.lastSubmit = lastSubmit;
    }

    public String getStudentId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public boolean isCopy(String studentId) {
        return this.id.equals(studentId);
    }

    public double getGpa() throws Exception{
        double sum = 0;
        int coursesUnits = 0;

        for (Grade grade : grades){
            int courseUnits = CourseEnrolment.getInstance().getOffering(grade.getCode()).getUnits();
            coursesUnits += courseUnits;
            sum += grade.getGrade()*courseUnits;
        }
        double gpa = sum/coursesUnits;

        return gpa;
    }

    public void addGrade(Grade grade){this.grades.add(grade);}

    public int getTotalPassedUnits()throws Exception{
        int totalPassedUnits = 0;
        for (Grade grade : grades)
            if(grade.getGrade()>=10)
                totalPassedUnits+=1;

        return totalPassedUnits;
    }

    public int getTotalUnits()throws Exception{
        int totalUnits = 0;
        for (WeeklyScheduleItem weeklyScheduleItem : CourseEnrolment.getInstance().getWeeklySchedule(id))
            totalUnits+=weeklyScheduleItem.getOffering().getUnits();

        return totalUnits;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void print() {
        System.out.println("studentId: " + id + "\n" +
                "name: " + name + "\n" +
                "secondName: " + secondName + "\n" +
                "birthDate: " + birthDate);
    }
}