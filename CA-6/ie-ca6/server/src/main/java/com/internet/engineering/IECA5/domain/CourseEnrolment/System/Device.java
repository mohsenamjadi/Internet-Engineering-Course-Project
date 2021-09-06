
package com.internet.engineering.IECA5.domain.CourseEnrolment.System;

import com.google.gson.annotations.Expose;
import com.internet.engineering.IECA5.domain.CourseEnrolment.CourseEnrolment;
import com.internet.engineering.IECA5.domain.CourseEnrolment.Student.Student;
import com.internet.engineering.IECA5.domain.CourseEnrolment.Student.WeeklyScheduleItem;
import com.internet.engineering.IECA5.repository.MzRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Device {
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String version;
    @Expose
    private String model;


    public Device(String id, String name, String version, String model) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.model = model;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getVersion() {
        return version;
    }
    public String getModel() {
        return model;
    }

    public boolean isCopy(Device device) {
        return this.id.equals(device.id);
    }


}
