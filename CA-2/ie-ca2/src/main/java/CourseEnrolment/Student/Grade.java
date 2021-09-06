package CourseEnrolment.Student;

public class Grade {
    private String code;
    private double grade;

    public Grade(String code, int grade){
        this.code = code;
        this.grade = grade;
    }

    public String getCode(){return code;}
    public double getGrade(){return grade;}

    public void print() {
        System.out.println("code: " + code + "\n" +
            "grade: " + grade);
    }
}
