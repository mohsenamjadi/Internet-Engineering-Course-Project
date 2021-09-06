package com.internet.engineering.IECA5.repository.Device;

import com.internet.engineering.IECA5.domain.CourseEnrolment.System.Device;
import com.internet.engineering.IECA5.repository.ConnectionPool;
import com.internet.engineering.IECA5.repository.Mapper;
import com.internet.engineering.IECA5.utils.CustomPair;
import com.internet.engineering.IECA5.utils.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeviceMapper extends Mapper<Device, CustomPair> implements IDeviceMapper {

    private static final String COLUMNS = " id, name, version, model ";
    private static final String TABLE_NAME = "DEVICES";

    public DeviceMapper(Boolean doManage) throws SQLException {
        if (doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "create table %s (\n" +
                            "    id varchar(255) primary key,\n" +
                            "    name tinytext not null,\n" +
                            "    version tinytext not null,\n" +
                            "    model tinytext not null\n" +
                            ");",
                    TABLE_NAME));
            st.close();
            con.close();
        }
    }

    public DeviceMapper() throws SQLException {
    }


    @Override
    protected String getFindStatement(CustomPair id) {
        return String.format("select * from %s where %s.%s = %s;",
                TABLE_NAME, TABLE_NAME, "id", StringUtils.quoteWrapper(id.getArgs().get(0)));
    }


    @Override
    protected String getInsertStatement(Device device) {
        return String.format("INSERT INTO %s ( %s ) values (%s, %s, %s,%s);", TABLE_NAME, COLUMNS,
                StringUtils.quoteWrapper(device.getId()), StringUtils.quoteWrapper(device.getName()),
                StringUtils.quoteWrapper(device.getVersion()), StringUtils.quoteWrapper(device.getModel())
        );
    }

    @Override
    protected String getDeleteStatement(CustomPair id) {
        return String.format("delete from %s where %s.%s = %s", TABLE_NAME, TABLE_NAME, "id",
                StringUtils.quoteWrapper(id.getArgs().get(0)));
    }

    @Override
    protected Device convertResultSetToObject(ResultSet rs) throws SQLException {
        return new Device(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("version"),
                rs.getString("model")
        );
    }

    @Override
    public Device getDeviceById(CustomPair id) throws SQLException {
        Device result = null;
        String statement = String.format("select * from %s where %s.%s = %s", TABLE_NAME, TABLE_NAME, "id",
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
    public List<Device> getAll() throws SQLException {
        List<Device> result = new ArrayList<Device>();
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

    public List<Device> search(String searchPhrase) throws SQLException{
        List<Device> result = new ArrayList<Device>();
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


}
