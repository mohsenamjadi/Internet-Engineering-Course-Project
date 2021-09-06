package com.internet.engineering.IECA8.domain.CourseEnrolment.System;

import com.google.gson.annotations.Expose;

public class ExamTime {
    @Expose
    private String start;
    @Expose
    private String end;

    public ExamTime(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}