package com.internet.engineering.IECA8.repository;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionPool {
    private static BasicDataSource ds = new BasicDataSource();
    private final static String dbURL = "jdbc:mysql://localhost:3306/bolbolestandb?characterEncoding=utf8";
    private final static String dbUserName = "bolbol";
    private final static String dbPassword = "";

    private ConnectionPool() {
    }

    static {
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        // remote db
        ds.setUrl(dbURL);
        ds.setUsername(dbUserName);
        ds.setPassword(dbPassword);
        ds.setMinIdle(1);
        ds.setMaxIdle(2000);
        ds.setMaxOpenPreparedStatements(2000);
        setEncoding();
    }

    public static Connection getConnection() throws SQLException {
        try {
            return ds.getConnection();
        }catch (Exception e){
            ds.setPassword(dbPassword);
            return ds.getConnection();
        }
    }

    public static void setEncoding(){
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute("ALTER DATABASE bolbolestandb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;");
            connection.close();
            statement.close();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
