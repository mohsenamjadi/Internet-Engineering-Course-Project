package com.internet.engineering.IECA8.repository.WeeklyScheduleItem;

import com.internet.engineering.IECA8.domain.CourseEnrolment.CourseEnrolment;
import com.internet.engineering.IECA8.domain.CourseEnrolment.Student.Student;
import com.internet.engineering.IECA8.domain.CourseEnrolment.Student.WeeklyScheduleItem;
import com.internet.engineering.IECA8.domain.CourseEnrolment.System.Offering;
import com.internet.engineering.IECA8.repository.ConnectionPool;
import com.internet.engineering.IECA8.repository.Mapper;
import com.internet.engineering.IECA8.utils.CustomPair;
import com.internet.engineering.IECA8.utils.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WeeklyScheduleItemMapper extends Mapper<WeeklyScheduleItem, CustomPair> implements IWeeklyScheduleItemMapper {

    private static final String COLUMNS = " code, classCode, studentId, status, visibility ";
    private static final String TABLE_NAME = "WEEKLYSHCEDULEITEMS";

    public WeeklyScheduleItemMapper(Boolean doManage) throws SQLException {
        if (doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "create table %s (\n" +
                            "    code varchar(255) not null,\n" +
                            "    classCode varchar(255) not null,\n" +
                            "    studentId varchar(255),\n" +
                            "    status tinytext not null,\n" +
                            "    visibility tinytext not null,\n" +
                            "    primary key (code, classCode , studentId)\n" +
                            ");",
                    TABLE_NAME));
            st.close();
            con.close();
        }
    }

    public WeeklyScheduleItemMapper() throws SQLException {
    }

    @Override
    protected String getFindStatement(CustomPair id) {
        return String.format("select * from %s where %s.%s = %s and %s.%s = %s and %s.%s = %s;",
                TABLE_NAME, TABLE_NAME, "code", StringUtils.quoteWrapper(id.getArgs().get(0)),
                TABLE_NAME, "classCode", StringUtils.quoteWrapper(id.getArgs().get(1)),
                TABLE_NAME, "studentId", StringUtils.quoteWrapper(id.getArgs().get(2))
                );
    }

    @Override
    protected String getInsertStatement(WeeklyScheduleItem weeklyScheduleItem) {
        return String.format("INSERT INTO %s ( %s ) values (%s, %s, %s, %s, %s);", TABLE_NAME, COLUMNS,
                StringUtils.quoteWrapper(weeklyScheduleItem.getOffering().getCode()), StringUtils.quoteWrapper(weeklyScheduleItem.getOffering().getClassCode()),
                StringUtils.quoteWrapper(weeklyScheduleItem.getStudent().getStudentId()), StringUtils.quoteWrapper(weeklyScheduleItem.getStatus()),
                StringUtils.quoteWrapper(weeklyScheduleItem.getVisibility())
        );
    }

    @Override
    protected String getDeleteStatement(CustomPair id) {
        return String.format("delete from %s where %s.%s = %s and %s.%s = %s  and %s.%s = %s", TABLE_NAME, TABLE_NAME, "studentId",
                StringUtils.quoteWrapper(id.getArgs().get(0)), TABLE_NAME, "code", StringUtils.quoteWrapper(id.getArgs().get(1)),
                TABLE_NAME, "classCode", StringUtils.quoteWrapper(id.getArgs().get(2))
        );
    }

    @Override
    protected WeeklyScheduleItem convertResultSetToObject(ResultSet rs) throws SQLException {
        Student student = null;
        Offering offering = null;

        try{
            student = CourseEnrolment.getInstance().getStudent(rs.getString("studentId"));
            offering = CourseEnrolment.getInstance().getOffering(rs.getString("code"), rs.getString("classCode"));

        } catch(Exception e) {

        }

        return new WeeklyScheduleItem(
                student,
                offering,
                rs.getString("status"),
                rs.getString("visibility")
        );
    }

    @Override
    public List<WeeklyScheduleItem> getAll() throws SQLException {
        List<WeeklyScheduleItem> result = new ArrayList<WeeklyScheduleItem>();
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

    @Override
    public void updateWeeklyScheduleItem(WeeklyScheduleItem weeklyScheduleItem) throws SQLException {
        String statement = String.format("update %s set %s = %s where %s = %s and %s = %s and %s = %s;", TABLE_NAME,
                "status", StringUtils.quoteWrapper(weeklyScheduleItem.getStatus()),
                "code", StringUtils.quoteWrapper(weeklyScheduleItem.getOffering().getCode()),
                "classCode", StringUtils.quoteWrapper(weeklyScheduleItem.getOffering().getClassCode()),
                "studentId", StringUtils.quoteWrapper(weeklyScheduleItem.getStudent().getStudentId())
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
    public void updateWeeklyScheduleItemVisibility(WeeklyScheduleItem weeklyScheduleItem) throws SQLException {
        String statement = String.format("update %s set %s = %s where %s = %s and %s = %s and %s = %s;", TABLE_NAME,
                "visibility", StringUtils.quoteWrapper(weeklyScheduleItem.getVisibility()),
                "code", StringUtils.quoteWrapper(weeklyScheduleItem.getOffering().getCode()),
                "classCode", StringUtils.quoteWrapper(weeklyScheduleItem.getOffering().getClassCode()),
                "studentId", StringUtils.quoteWrapper(weeklyScheduleItem.getStudent().getStudentId())
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
    public List<WeeklyScheduleItem> getLastSubmit(String studentId) throws SQLException {
        List<WeeklyScheduleItem> result = new ArrayList<WeeklyScheduleItem>();
        String statement = String.format("select * from %s where %s.%s = %s and %s.%s = %s;", TABLE_NAME, TABLE_NAME, "studentId",
                StringUtils.quoteWrapper(studentId), TABLE_NAME, "status", "'finalized'"
        );

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
    public List<WeeklyScheduleItem> getNonFinalized(CustomPair id) throws SQLException {
        List<WeeklyScheduleItem> result = new ArrayList<WeeklyScheduleItem>();
//
//        return String.format("select * from %s where %s.%s = %s;",
//                TABLE_NAME, TABLE_NAME, "id", StringUtils.quoteWrapper(id.getArgs().get(0)));

        String statement = String.format("select * from %s where %s.%s = %s and %s.%s = %s;", TABLE_NAME, TABLE_NAME, "studentId",
                StringUtils.quoteWrapper(id.getArgs().get(0)), TABLE_NAME, "status", "'non-finalized'"
        );
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
    public void emptyWeeklySchedule(CustomPair id) throws SQLException {
        String statement = String.format("delete from %s where %s.%s = %s;", TABLE_NAME, TABLE_NAME, "studentId",
                StringUtils.quoteWrapper(id.getArgs().get(0))
        );
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findAll query.");
                throw ex;
            }
        }
    }


}