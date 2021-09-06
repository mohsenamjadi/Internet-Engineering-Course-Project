package com.internet.engineering.IECA7.repository.Student;

import com.internet.engineering.IECA7.domain.CourseEnrolment.Student.Student;
import com.internet.engineering.IECA7.repository.IMapper;

import java.sql.SQLException;
import java.util.List;

public interface IStudentMapper extends IMapper<Student, String> {
        List<Student> getAll() throws SQLException;
        Student getStudentByEmail(String email) throws SQLException;
        void updateStudentPassword(Student student) throws SQLException;
}
