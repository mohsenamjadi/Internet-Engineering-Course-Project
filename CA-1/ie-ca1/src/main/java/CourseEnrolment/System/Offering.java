package CourseEnrolment.System;

import java.util.ArrayList;
import java.util.List;

public class Offering {
    private String code;
    private String name;
    private String Instructor;
    private int units;
    private ClassTime classTime;
    private ExamTime examTime;
    private int capacity;
    private List<String> prerequisites= new ArrayList<>();;
    private int attendeesCount;

    public Offering(String code, String name, String Instructor, int units, ClassTime classTime, ExamTime examTime, int capacity, List<String> prerequisites) {
        this.code = code;
        this.name = name;
        this.Instructor = Instructor;
        this.units = units;
        this.classTime = classTime;
        this.examTime = examTime;
        this.capacity = capacity;
        this.prerequisites = prerequisites;
        this.attendeesCount = 0;
    }

    public String getCode() {
        return code;
    }
    public int getUnits() {
        return units;
    }
    public ClassTime getClassTime() {
        return classTime;
    }
    public ExamTime getExamTime() {
        return examTime;
    }
    public int getCapacity() {
        return capacity;
    }
    public int attendeesCount() {
        return attendeesCount;
    }

    public void attendeesCountInc() {
        attendeesCount++;
    }

    public void print() {
        System.out.println("code: " + code + "\n" +
                "name: " + name + "\n" +
                "Instructor: " + Instructor + "\n" +
                "units: " + units);
        classTime.print();
        examTime.print();
        System.out.println("capacity: " + capacity);
        for (String prerequisite:prerequisites) {
            System.out.println(prerequisite);
        }
    }

    public boolean isCopy(Offering offering) {
        return this.name.equals(offering.name);
    }
}
