package CourseEnrolment.Student;

public class Student {
    private String studentId;
    private String name;
    private String enteredAt;

    public Student(String studentId, String name, String enteredAt) {
        this.studentId = studentId;
        this.name = name;
        this.enteredAt = enteredAt;
    }

    public String getStudentId() {
        return studentId;
    }

    public boolean isCopy(String studentId) {
        return this.studentId.equals(studentId);
    }

    public void print() {
        System.out.println("studentId: " + studentId + "\n" +
                "name: " + name + "\n" +
                "enteredAt: " + enteredAt);
    }
}
