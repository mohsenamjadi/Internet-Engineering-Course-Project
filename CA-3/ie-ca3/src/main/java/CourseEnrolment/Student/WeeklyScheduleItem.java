package CourseEnrolment.Student;

import CourseEnrolment.System.Offering;

public class WeeklyScheduleItem {
    private Offering offering;
    private Student student;
    private String status;

    public WeeklyScheduleItem(Student student, Offering offering) {
        this.offering = offering;
        this.student = student;
        this.status = "non-finalized";
    }

    public Student getStudent() {
        return student;
    }

    public Offering getOffering() {
        return offering;
    }

    public String getStatus() {
        return status;
    }

    public void finalizeWeeklyScheduleItem() {
        status = "finalized";
    }

    public void print() {
        System.out.println("studentId: " + this.getStudent().getStudentId() + "\n" +
                "offerings: " );
        this.getOffering().print();
        System.out.println("status: " + status);
    }
}