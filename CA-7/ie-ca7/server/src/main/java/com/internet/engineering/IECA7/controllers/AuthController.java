package com.internet.engineering.IECA7.controllers;

import com.google.gson.Gson;
import com.internet.engineering.IECA7.controllers.Exceptions.ExceptionBadCharacters;
import com.internet.engineering.IECA7.domain.CourseEnrolment.CourseEnrolment;
import com.internet.engineering.IECA7.domain.CourseEnrolment.Student.Student;
import com.internet.engineering.IECA7.repository.Repository;
import com.internet.engineering.IECA7.security.JWTAuthorizationFilter;
import com.internet.engineering.IECA7.utils.HTTPRequestHandler.HTTPRequestHandler;
import com.internet.engineering.IECA7.utils.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/signup")
    public String signup(@RequestBody String jsonString, final HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(jsonString, Properties.class);
        String id = properties.getProperty("id");
        String name = properties.getProperty("name");
        String secondName = properties.getProperty("secondName");
        String birthDate = properties.getProperty("birthDate");
        String field = properties.getProperty("field");
        String faculty = properties.getProperty("faculty");
        String level = properties.getProperty("level");
        String email = properties.getProperty("email");
        String password = properties.getProperty("password");
        try {
            if(StringUtils.hasIllegalChars(email) || StringUtils.hasIllegalChars(name) || StringUtils.hasIllegalChars(secondName) || StringUtils.hasIllegalChars(password)){
                throw new ExceptionBadCharacters();
            }
            try {
                Repository.getInstance().getStudentByEmail(email);
                response.sendError(HttpStatus.BAD_REQUEST.value());
                return null;
            } catch (SQLException sqlException) {
                // ALL GOOD! PROCEED TO NEXT LINE
            }

            password = StringUtils.hashString(password);
            CourseEnrolment.getInstance().addStudent(
                    new Student(
                            id,
                            name,
                            secondName,
                            birthDate,
                            field,
                            faculty,
                            level,
                            "مشغول به تحصیل",
                            "",
                            email,
                            password
                    )
            );
            return JWTAuthorizationFilter.getJWTToken(id);
        } catch (Exception e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
        return null;
    }

    @PostMapping("/login")
    public String login(@RequestBody String jsonString, final HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(jsonString, Properties.class);
        String email = properties.getProperty("email");
        String password = properties.getProperty("password");
        try {
            if(StringUtils.hasIllegalChars(email) || StringUtils.hasIllegalChars(password)){
                throw new ExceptionBadCharacters();
            }
            password = StringUtils.hashString(password);
            Student student = CourseEnrolment.getInstance().loginStudent(email, password);
            return JWTAuthorizationFilter.getJWTToken(student.getStudentId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
        }
        return null;
    }

    @PostMapping("/forgetPassword")
    public String forgetPassword(@RequestBody String jsonString, final HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(jsonString, Properties.class);
        String email = properties.getProperty("email");
        try {
            if(StringUtils.hasIllegalChars(email)){
                throw new ExceptionBadCharacters();
            }
            Repository.getInstance().getStudentByEmail(email);
            HTTPRequestHandler.getRequest(
                    "http://138.197.181.131:5200/api/send_mail",
                    "{\"email\":\"" + email + "\",\"url\":\"http://localhost:3000/resetpassword?token=" + JWTAuthorizationFilter.getJWTToken(email) + "\"}"
            );
            return Config.OK_RESPONSE;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }

        return null;
    }


    @PostMapping("/resetPassword/{token}")
    public String resetPassword(@PathVariable String token, @RequestBody String jsonString, final HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(jsonString, Properties.class);
        String password = properties.getProperty("password");
        try {
            if(StringUtils.hasIllegalChars(password)){
                throw new ExceptionBadCharacters();
            }
            Student student = JWTAuthorizationFilter.getStudent(token);
            student.setPassword(StringUtils.hashString(password));
            Repository.getInstance().updateStudentPassword(student);
            return Config.OK_RESPONSE;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }

        return null;
    }


}
