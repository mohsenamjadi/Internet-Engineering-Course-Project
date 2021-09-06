package com.internet.engineering.IECA5.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.internet.engineering.IECA5.domain.CourseEnrolment.CourseEnrolment;
import com.internet.engineering.IECA5.domain.CourseEnrolment.System.Device;


import com.internet.engineering.IECA5.repository.MzRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @GetMapping("")
    public List<Device> getDevices(final HttpServletResponse response) throws IOException {
        try {
            if (CourseEnrolment.getInstance().getStudent() == null){
                response.sendRedirect("http://localhost:8080/login");
                return null;
            }
            else {
                return CourseEnrolment.getInstance().searchDevices(
                        CourseEnrolment.getInstance().getStudent().getSearchFilter()
                );
            }
        } catch (Exception e) {
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return null;
        }
    }

    @GetMapping("/{deviceId}")
    public Device getDeviceById(@PathVariable String deviceId, final HttpServletResponse response) throws IOException {
        try {
            Device device = CourseEnrolment.getInstance().getDevice(deviceId);
            return device;
        } catch (Exception e) {
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return null;
        }
    }

    @GetMapping("/search")
    public List<Device> getDevicesByName(final HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        try {
            if (CourseEnrolment.getInstance().getStudent() == null){
                response.sendRedirect("http://localhost:8080/login");
                return null;
            }
            else {
                return CourseEnrolment.getInstance().searchDevices(
                        CourseEnrolment.getInstance().getStudent().getSearchFilter()
                );
            }
        } catch (Exception e) {
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return null;
        }
    }

    @PostMapping("/search")
    public String searchFilter(@RequestBody(required = true) String jsonString, final HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        try {
            if (CourseEnrolment.getInstance().getStudent() == null){
                response.sendRedirect("http://localhost:8080/login");
                return null;
            }
            else {
                Properties properties = gson.fromJson(jsonString, Properties.class);
                String filter = properties.getProperty("filter");
                CourseEnrolment.getInstance().getStudent().setSearchFilter(filter);
                return Config.OK_RESPONSE;
            }
        } catch (Exception e) {
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return null;
        }
    }


    @PostMapping(path = "/addDevice", consumes = "application/json")
    public String addDevice(@RequestBody(required = true) String jsonString, final HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        try {
            Properties properties = gson.fromJson(jsonString, Properties.class);
            String deviceId = properties.getProperty("deviceId");
            String name = properties.getProperty("name");
            String version = properties.getProperty("version");
            String model = properties.getProperty("model");
            Device device = new Device(deviceId,name,version,model);
            CourseEnrolment.getInstance().addDevice(device);
            return Config.OK_RESPONSE;
        } catch (Exception e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return null;
        }
    }



    @DeleteMapping("/deleteDevice")
    public String removeDevice(@RequestBody String jsonString, final HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        try {
            Properties properties = gson.fromJson(jsonString, Properties.class);
            String deviceId = properties.getProperty("deviceId");
            CourseEnrolment.getInstance().removeDevice(deviceId);
            return Config.OK_RESPONSE;
        } catch (Exception e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return null;
        }
    }



}
