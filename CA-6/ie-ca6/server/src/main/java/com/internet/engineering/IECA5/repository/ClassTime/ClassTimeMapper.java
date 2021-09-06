//package com.internet.engineering.IECA5.repository.ClassTime;
//
//import com.internet.engineering.IECA5.domain.CourseEnrolment.System.ClassTime;
//import com.internet.engineering.IECA5.repository.ConnectionPool;
//import com.internet.engineering.IECA5.repository.Mapper;
//import com.internet.engineering.IECA5.utils.CustomPair;
//import com.internet.engineering.IECA5.utils.StringUtils;
//
//import java.sql.*;
//
//
//public class ClassTimeMapper extends Mapper<ClassTime, CustomPair> implements IClassTimeMapper {
//
//    private static final String COLUMNS = " code, classCode, time ";
//    private static final String TABLE_NAME = "CLASSTIME";
//
//    public ClassTimeMapper(Boolean doManage) throws SQLException {
//        if (doManage) {
//            Connection con = ConnectionPool.getConnection();
//            Statement st = con.createStatement();
//            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
//            st.executeUpdate(String.format(
//                    "create table %s (\n" +
//                            "    code varchar(255) not null,\n" +
//                            "    classCode varchar(255) not null,\n" +
//                            "    time tinytext not null,\n" +
//                            "    primary key (code, classCode)\n" +
//                            ");",
//                    TABLE_NAME));
//            st.close();
//            con.close();
//        }
//    }
//
//    public ClassTimeMapper() throws SQLException {
//    }
//
//    @Override
//    protected String getFindStatement(CustomPair id) {
//        return String.format("select * from %s where %s.%s = %s and %s.%s = %s", TABLE_NAME, TABLE_NAME, "code",
//                StringUtils.quoteWrapper(id.getArgs().get(0)), TABLE_NAME, "classCode", StringUtils.quoteWrapper(id.getArgs().get(1)));
//    }
//
//    @Override
//    protected String getInsertStatement(ClassTime classTime) {
//        return String.format("INSERT INTO %s ( %s ) values (%s, %s, %s);", TABLE_NAME, COLUMNS,
//                StringUtils.quoteWrapper(classTime.getCode()),
//                StringUtils.quoteWrapper(classTime.getClassCode()),
//                StringUtils.quoteWrapper(classTime.getTime()));
//    }
//
//    @Override
//    protected String getDeleteStatement(CustomPair id) {
//        return String.format("delete from %s where %s.%s = %s and %s.%s = %s", TABLE_NAME, TABLE_NAME, "code",
//                StringUtils.quoteWrapper(id.getArgs().get(0)), TABLE_NAME, "classCode", StringUtils.quoteWrapper(id.getArgs().get(1)));
//    }
//
//    @Override
//    protected ClassTime convertResultSetToObject(ResultSet rs) throws SQLException {
//        return new ClassTime(
//                rs.getString("code"),
//                rs.getString("classCode"),
//                null,
//                rs.getString("time")
//        );
//    }
//}