package com.internet.engineering.IECA5.repository.Student;

import com.internet.engineering.IECA5.domain.CourseEnrolment.Student.Student;
import com.internet.engineering.IECA5.repository.IMapper;

import java.sql.SQLException;
import java.util.List;

public interface IStudentMapper extends IMapper<Student, String> {
        List<Student> getAll() throws SQLException;
}
