package com.internet.engineering.IECA7.repository.Course;

import com.internet.engineering.IECA7.domain.CourseEnrolment.System.ExamTime;
import com.internet.engineering.IECA7.domain.CourseEnrolment.System.Offering;
import com.internet.engineering.IECA7.repository.ConnectionPool;
import com.internet.engineering.IECA7.repository.Mapper;
import com.internet.engineering.IECA7.utils.CustomPair;
import com.internet.engineering.IECA7.utils.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseMapper extends Mapper<Offering, CustomPair> implements ICourseMapper {

    private static final String COLUMNS = " code, classCode, name, units, type, instructor, capacity, signedUp, start, end, time ";
    private static final String TABLE_NAME = "COURSES";

    public CourseMapper(Boolean doManage) throws SQLException {
        if (doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "create table %s (\n" +
                            "    code varchar(255) not null,\n" +
                            "    classCode varchar(255) not null,\n" +
                            "    name tinytext not null,\n" +
                            "    units int not null,\n" +
                            "    type tinytext not null,\n" +
                            "    instructor tinytext not null,\n" +
                            "    capacity int not null,\n" +
                            "    signedUp int not null default 0,\n" +
                            "    start tinytext not null,\n" +
                            "    end tinytext not null,\n" +
                            "    time tinytext not null,\n" +
                            "    primary key(code, classCode)\n" +
                            ");",
                    TABLE_NAME));
            st.close();
            con.close();
        }
    }

    public CourseMapper() throws SQLException {
    }

    @Override
    protected String getFindStatement(CustomPair id) {
        return String.format("select * from %s where %s.%s = %s and %s.%s = %s", TABLE_NAME, TABLE_NAME, "code",
                StringUtils.quoteWrapper(id.getArgs().get(0)), TABLE_NAME, "classCode", StringUtils.quoteWrapper(id.getArgs().get(1)));
    }

    @Override
    public Offering getOfferingByCode(CustomPair id) throws SQLException {
        Offering result = null;
        String statement = String.format("select * from %s where %s.%s = %s", TABLE_NAME, TABLE_NAME, "code",
                StringUtils.quoteWrapper(id.getArgs().get(0)));

        try {
            Connection con = ConnectionPool.getConnection();
            PreparedStatement st = con.prepareStatement(statement);
            ResultSet resultSet = st.executeQuery();
            resultSet.next();
            result = convertResultSetToObject(resultSet);

            con.close();
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("error in Mapper.findAll query.");
            throw ex;
        }
    }

    @Override
    protected String getInsertStatement(Offering course) {
        return String.format("INSERT INTO %s ( %s ) values (%s, %s, %s, %d, %s, %s, %d, %d, %s, %s, %s);", TABLE_NAME, COLUMNS,
                StringUtils.quoteWrapper(course.getCode()), StringUtils.quoteWrapper(course.getClassCode()),
                StringUtils.quoteWrapper(course.getName()), course.getUnits(), StringUtils.quoteWrapper(course.getType()),
                StringUtils.quoteWrapper(course.getInstructor()), course.getCapacity(), course.getSignedUp(),
                StringUtils.quoteWrapper(course.getExamTime().getStart()), StringUtils.quoteWrapper(course.getExamTime().getEnd()),
                StringUtils.quoteWrapper(course.getTime())
        );
    }

    @Override
    protected String getDeleteStatement(CustomPair id) {
        return String.format("delete from %s where %s.%s = %s and %s.%s = %s", TABLE_NAME, TABLE_NAME, "code",
                StringUtils.quoteWrapper(id.getArgs().get(0)), TABLE_NAME, "classCode", StringUtils.quoteWrapper(id.getArgs().get(1)));
    }

    @Override
    protected Offering convertResultSetToObject(ResultSet rs) throws SQLException {
        return new Offering(
                rs.getString("code"),
                rs.getString("classCode"),
                rs.getString("name"),
                rs.getString("instructor"),
                rs.getInt("units"),
                rs.getString("type"),
                null,
                new ExamTime(rs.getString("start"), rs.getString("end")),
                rs.getInt("capacity"),
                rs.getInt("signedUp"),
                null,
                rs.getString("time")
        );
    }

    @Override
    public List<Offering> getAll() throws SQLException {
        List<Offering> result = new ArrayList<Offering>();
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

    public List<Offering> search(String searchPhrase) throws SQLException{
        List<Offering> result = new ArrayList<Offering>();
        String statement = "SELECT * FROM " + TABLE_NAME + " WHERE name LIKE '%" + searchPhrase + "%'";

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
    public void updateCapacity(Offering offering) throws SQLException {
        String statement = String.format("update %s set %s = %d where %s = %s and %s = %s;", TABLE_NAME,
                "capacity", offering.getCapacity(),
                "code", StringUtils.quoteWrapper(offering.getCode()),
                "classCode", StringUtils.quoteWrapper(offering.getClassCode())
        );
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.updateFood query.");
                throw ex;
            }
        }
    }

    @Override
    public void updateSignedUp(Offering offering) throws SQLException {
        String statement = String.format("update %s set %s = %d where %s = %s and %s = %s;", TABLE_NAME,
                "signedUp", offering.getSignedUp(),
                "code", StringUtils.quoteWrapper(offering.getCode()),
                "classCode", StringUtils.quoteWrapper(offering.getClassCode())
        );
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.updateFood query.");
                throw ex;
            }
        }
    }


}