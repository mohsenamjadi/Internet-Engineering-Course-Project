package com.internet.engineering.IECA8.repository.Day;

import com.internet.engineering.IECA8.domain.CourseEnrolment.System.Day;
import com.internet.engineering.IECA8.repository.ConnectionPool;
import com.internet.engineering.IECA8.repository.Mapper;
import com.internet.engineering.IECA8.utils.CustomPair;
import com.internet.engineering.IECA8.utils.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DayMapper extends Mapper<Day, CustomPair> implements IDayMapper {

    private static final String COLUMNS = " code, classCode, day ";
    private static final String TABLE_NAME = "DAYS";

    public DayMapper(Boolean doManage) throws SQLException {
        if (doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "create table %s (\n" +
                            "    code varchar(255) not null,\n" +
                            "    classCode varchar(255) not null,\n" +
                            "    day varchar(255) not null,\n" +
                            "    primary key (code, classCode, day)\n" +
                            ");",
                    TABLE_NAME));
            st.close();
            con.close();
        }
    }

    public DayMapper() throws SQLException {
    }

    @Override
    protected String getFindStatement(CustomPair id) {
        return String.format("select * from %s where %s.%s = %s and %s.%s = %s", TABLE_NAME, TABLE_NAME, "code",
                StringUtils.quoteWrapper(id.getArgs().get(0)), TABLE_NAME, "classCode", StringUtils.quoteWrapper(id.getArgs().get(1)));
    }

    @Override
    protected String getInsertStatement(Day day) {
        return String.format("INSERT INTO %s ( %s ) values (%s, %s, %s);", TABLE_NAME, COLUMNS,
                StringUtils.quoteWrapper(day.getCode()),
                StringUtils.quoteWrapper(day.getClassCode()),
                StringUtils.quoteWrapper(day.getDay()));
    }

    @Override
    protected String getDeleteStatement(CustomPair id) {
        return String.format("delete from %s where %s.%s = %s and %s.%s = %s", TABLE_NAME, TABLE_NAME, "code",
                StringUtils.quoteWrapper(id.getArgs().get(0)), TABLE_NAME, "classCode", StringUtils.quoteWrapper(id.getArgs().get(1)));
    }

    @Override
    protected Day convertResultSetToObject(ResultSet rs) throws SQLException {
        return new Day(
                rs.getString("code"),
                rs.getString("classCode"),
                rs.getString("day")
        );
    }

    @Override
    public List<Day> getAll() throws SQLException {
        List<Day> result = new ArrayList<Day>();
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

    public List<Day> search(CustomPair id) throws SQLException {
        List<Day> result = new ArrayList<Day>();
        String statement = String.format("select * from %s where %s.%s = %s and %s.%s = %s", TABLE_NAME, TABLE_NAME, "code",
                StringUtils.quoteWrapper(id.getArgs().get(0)), TABLE_NAME, "classCode", StringUtils.quoteWrapper(id.getArgs().get(1)));

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
}