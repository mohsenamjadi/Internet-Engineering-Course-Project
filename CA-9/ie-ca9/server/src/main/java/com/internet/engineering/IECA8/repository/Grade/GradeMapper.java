package com.internet.engineering.IECA8.repository.Grade;

import com.internet.engineering.IECA8.domain.CourseEnrolment.Student.Grade;
import com.internet.engineering.IECA8.repository.ConnectionPool;
import com.internet.engineering.IECA8.repository.Mapper;
import com.internet.engineering.IECA8.utils.CustomPair;
import com.internet.engineering.IECA8.utils.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeMapper extends Mapper<Grade, CustomPair> implements IGradeMapper {

    private static final String COLUMNS = " code, student_id, grade, term ";
    private static final String TABLE_NAME = "GRADES";

    public GradeMapper(Boolean doManage) throws SQLException {
        if (doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "create table %s (\n" +
                            "    code varchar(255) not null,\n" +
                            "    student_id varchar(255) not null,\n" +
                            "    grade double not null,\n" +
                            "    term int,\n" +
                            "    primary key(code, student_id , term)\n" +
                            ");",
                    TABLE_NAME));
            st.close();
            con.close();
        }
    }

    public GradeMapper() throws SQLException {
    }

    @Override
    protected String getFindStatement(CustomPair id) {
        return String.format("select * from %s where %s.%s = %s and %s.%s = %s", TABLE_NAME, TABLE_NAME, "code",
                StringUtils.quoteWrapper(id.getArgs().get(0)), TABLE_NAME, "student_id", StringUtils.quoteWrapper(id.getArgs().get(1)));
    }

    @Override
    protected String getInsertStatement(Grade grade) {
        return String.format("INSERT INTO %s ( %s ) values (%s, %s, %f, %d);", TABLE_NAME, COLUMNS,
                StringUtils.quoteWrapper(grade.getCode()), StringUtils.quoteWrapper(grade.getStudentId()),
                grade.getGrade(), grade.getTerm());
    }

    @Override
    protected String getDeleteStatement(CustomPair id) {
        return String.format("delete from %s where %s.%s = %s and %s.%s = %s", TABLE_NAME, TABLE_NAME, "code",
                StringUtils.quoteWrapper(id.getArgs().get(0)), TABLE_NAME, "classCode", StringUtils.quoteWrapper(id.getArgs().get(1)));
    }

    @Override
    protected Grade convertResultSetToObject(ResultSet rs) throws SQLException {
        return new Grade(
                rs.getString("code"),
                rs.getString("student_id"),
                rs.getDouble("grade"),
                rs.getInt("term")
        );
    }

    @Override
    public List<Grade> getAllGradesByStudentId(String student_id) throws SQLException{
        List<Grade> result = new ArrayList<Grade>();
        String statement = String.format("select * from %s where %s.%s = %s", TABLE_NAME, TABLE_NAME, "student_id",
                StringUtils.quoteWrapper(student_id));

        try {
            Connection con = ConnectionPool.getConnection();
            PreparedStatement st = con.prepareStatement(statement);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next())
                result.add(convertResultSetToObject(resultSet));

            con.close();
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("error in Mapper.findAll query.");
            throw ex;
        }
    }

    @Override
    public List<Grade> getAll() throws SQLException {
        List<Grade> result = new ArrayList<Grade>();
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