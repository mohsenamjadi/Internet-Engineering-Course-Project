package CourseEnrolment.Student;

import CourseEnrolment.Utility;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String id;
    private String name;
    private String secondName;
    private String birthDate;
    private List<Grade> grades;

    public Student(String id, String name, String secondName, String birthDate) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.birthDate = birthDate;
    }

    public Student() {
         this.grades = new ArrayList<>();
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
            int courseUnits = Utility.Memory.getCourseEnrolment().getOffering(grade.getCode()).getUnits();
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
        for (WeeklyScheduleItem weeklyScheduleItem : Utility.Memory.getCourseEnrolment().getWeeklySchedule(id))
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
