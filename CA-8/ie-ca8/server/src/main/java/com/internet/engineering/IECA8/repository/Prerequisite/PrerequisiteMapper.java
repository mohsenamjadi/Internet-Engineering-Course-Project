package com.internet.engineering.IECA8.repository.Prerequisite;

import com.internet.engineering.IECA8.domain.CourseEnrolment.System.Prerequisite;
import com.internet.engineering.IECA8.repository.ConnectionPool;
import com.internet.engineering.IECA8.repository.Mapper;
import com.internet.engineering.IECA8.utils.CustomPair;
import com.internet.engineering.IECA8.utils.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrerequisiteMapper extends Mapper<Prerequisite, CustomPair> implements IPrerequisiteMapper {

    private static final String COLUMNS = " code, classCode, prerequisite ";
    private static final String TABLE_NAME = "PREREQUISITES";

    public PrerequisiteMapper(Boolean doManage) throws SQLException {
        if (doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "create table %s (\n" +
                            "    code varchar(255) not null,\n" +
                            "    classCode varchar(255) not null,\n" +
                            "    prerequisite varchar(255) not null,\n" +
                            "    primary key (code, classCode, prerequisite)\n" +
                            ");",
                    TABLE_NAME));
            st.close();
            con.close();
        }
    }

    public PrerequisiteMapper() throws SQLException {
    }

    @Override
    protected String getFindStatement(CustomPair id) {
        return String.format("select * from %s where %s.%s = %s and %s.%s = %s", TABLE_NAME, TABLE_NAME, "code",
                StringUtils.quoteWrapper(id.getArgs().get(0)), TABLE_NAME, "classCode", StringUtils.quoteWrapper(id.getArgs().get(1)));
    }

    @Override
    protected String getInsertStatement(Prerequisite prerequisite) {
        return String.format("INSERT INTO %s ( %s ) values (%s, %s, %s);", TABLE_NAME, COLUMNS,
                StringUtils.quoteWrapper(prerequisite.getCode()),
                StringUtils.quoteWrapper(prerequisite.getClassCode()),
                StringUtils.quoteWrapper(prerequisite.getPrerequisite()));
    }

    @Override
    protected String getDeleteStatement(CustomPair id) {
        return String.format("delete from %s where %s.%s = %s and %s.%s = %s", TABLE_NAME, TABLE_NAME, "code",
                StringUtils.quoteWrapper(id.getArgs().get(0)), TABLE_NAME, "classCode", StringUtils.quoteWrapper(id.getArgs().get(1)));
    }

    @Override
    protected Prerequisite convertResultSetToObject(ResultSet rs) throws SQLException {
        return new Prerequisite(
                rs.getString("code"),
                rs.getString("classCode"),
                rs.getString("prerequisite")
        );
    }

    @Override
    public List<Prerequisite> getAll() throws SQLException {
        List<Prerequisite> result = new ArrayList<Prerequisite>();
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

    public List<Prerequisite> search(CustomPair id) throws SQLException{
        List<Prerequisite> result = new ArrayList<Prerequisite>();
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