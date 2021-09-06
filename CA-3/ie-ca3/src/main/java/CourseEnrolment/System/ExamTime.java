package CourseEnrolment.System;

public class ExamTime {
    private String start;
    private String end;

    public ExamTime(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public void print(){
        System.out.println("start: " + start + " | end: " + end);
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}