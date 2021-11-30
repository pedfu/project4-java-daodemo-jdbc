package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
    private static Connection conn;

    public static Connection getConnection() {
        try {
            Properties props = loadProperties();
            String url = props.getProperty("dburl");
            conn = DriverManager.getConnection(url, props);
            return conn;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    public static void closeConnection() {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeStatement(Statement s) {
        if(s != null) {
            try {
                s.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    private static Properties loadProperties() {
        try(FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }
}
