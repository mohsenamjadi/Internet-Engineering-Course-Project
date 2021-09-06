package CourseEnrolment.System;

import java.util.ArrayList;
import java.util.List;

public class Offering {
    private String code;
    private String classCode;
    private String name;
    private String instructor;
    private int units;
    private String type;
    private ClassTime classTime;
    private ExamTime examTime;
    private int capacity;
    private List<String> prerequisites = new ArrayList<>();
    private int attendeesCount;

    public Offering(String code, String classCode, String name, String instructor, int units, String type, ClassTime classTime, ExamTime examTime, int capacity, List<String> prerequisites) {
        this.code = code;
        this.classCode = classCode;
        this.name = name;
        this.instructor = instructor;
        this.units = units;
        this.type = type;
        this.classTime = classTime;
        this.examTime = examTime;
        this.capacity = capacity;
        this.prerequisites = prerequisites;
        this.attendeesCount = 0;
    }

    public String getCode() {
        return code;
    }
    public String getClassCode() {
        return classCode;
    }
    public String getName() {
        return name;
    }
    public int getUnits() {
        return units;
    }
    public String getType() {
        return type;
    }
    public ClassTime getClassTime() {
        return classTime;
    }
    public ExamTime getExamTime() {
        return examTime;
    }
    public List<String> getPrerequisites() {
        return prerequisites;
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
            "classCode: " + classCode + "\n" +
            "name: " + name + "\n" +
            "instructor: " + instructor + "\n" +
            "units: " + units + "\n" +
            "type: " + type );
        classTime.print();
        examTime.print();
        System.out.println("capacity: " + capacity);
        for (String prerequisite:prerequisites) {
            System.out.println("prerequisites :");
            System.out.println(prerequisite);
        }
    }

    public boolean isCopy(Offering offering) {
        return this.name.equals(offering.name);
    }
}
