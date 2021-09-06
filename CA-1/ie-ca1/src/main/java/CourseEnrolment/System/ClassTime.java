package CourseEnrolment.System;

import java.util.ArrayList;
import java.util.List;

public class ClassTime {
    private List<String> days = new ArrayList<>();
    private String time;

    public ClassTime(List<String> days, String time) {
        this.days = days;
        this.time = time;
    }

    public void print(){
        System.out.println("days: " + days + " | time: " + time);
    }

    public List<String> getDays() {
        return days;
    }

    public String getTime() {
        return time;
    }
}