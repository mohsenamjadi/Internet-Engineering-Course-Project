package com.internet.engineering.IECA8.domain.CourseEnrolment.System;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class ClassTime {
    @Expose
    private List<String> days = new ArrayList<>();
    @Expose
    private String time;

    public ClassTime(List<String> days, String time) {
        this.days = days;
        this.time = time;
    }

    public List<String> getDays() {
        return days;
    }

    public String getTime() {
        return time;
    }
}