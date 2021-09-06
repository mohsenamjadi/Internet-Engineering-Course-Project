package com.internet.engineering.IECA5.repository.Student;

import com.internet.engineering.IECA5.domain.CourseEnrolment.Student.Student;
import com.internet.engineering.IECA5.repository.ConnectionPool;
import com.internet.engineering.IECA5.repository.Mapper;
import com.internet.engineering.IECA5.utils.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentMapper extends Mapper<Student, String> implements IStudentMapper {

    private static final String COLUMNS = " id, name, secondName, birthDate, field, faculty, level, status, img ";
    private static final String TABLE_NAME = "STUDENTS";

    public StudentMapper(Boolean doManage) throws SQLException {
        if (doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "create table %s (\n" +
                            "    id varchar(255) primary key,\n" +
                            "    name tinytext not null,\n" +
                            "    secondName tinytext not null,\n" +
                            "    birthDate tinytext not null,\n" +
                            "    field tinytext not null,\n" +
                            "    faculty tinytext not null,\n" +
                            "    level tinytext not null,\n" +
                            "    status tinytext not null,\n" +
                            "    img tinytext\n" +
                            ");",
                    TABLE_NAME));
            st.close();
            con.close();
        }
    }

    public StudentMapper() throws SQLException {
    }

    @Override
    protected String getFindStatement(String id) {
        return String.format("select * from %s where %s.%s = %s;",
                TABLE_NAME, TABLE_NAME, "id", StringUtils.quoteWrapper(id));
    }

    @Override
    protected String getInsertStatement(Student student) {
        return String.format("INSERT INTO %s ( %s ) values (%s, %s, %s, %s, %s, %s, %s, %s, %s);", TABLE_NAME, COLUMNS,
                StringUtils.quoteWrapper(student.getStudentId()), StringUtils.quoteWrapper(student.getName()),
                StringUtils.quoteWrapper(student.getSecondName()), StringUtils.quoteWrapper(student.getBirthDate()),
                StringUtils.quoteWrapper(student.getField()), StringUtils.quoteWrapper(student.getFaculty()),
                StringUtils.quoteWrapper(student.getLevel()), StringUtils.quoteWrapper(student.getStatus()),
                StringUtils.quoteWrapper(student.getImage()));
    }

    @Override
    protected String getDeleteStatement(String id) {
        return String.format("delete from %s where %s.%s = %s", TABLE_NAME, TABLE_NAME, "id", StringUtils.quoteWrapper(id));
    }

    @Override
    protected Student convertResultSetToObject(ResultSet rs) throws SQLException {
        return new Student(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("secondName"),
                rs.getString("birthDate"),
                rs.getString("field"),
                rs.getString("faculty"),
                rs.getString("level"),
                rs.getString("status"),
                rs.getString("img")
                );
    }

    @Override
    public List<Student> getAll() throws SQLException {
        List<Student> result = new ArrayList<Student>();
        String statement = "SELECT * FROM " + TABLE_NAME;
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                while (resultSet.next())
                    result.add(convertResultSetToObject(resultSet));
                return result;
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findAll query.");
                throw ex;
            }
        }
    }
}