package com.qchery.kada.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Chery
 * @date 2018/6/30 21:02
 */
public class DbUtils {

    public static void closeQuietly(ResultSet resultSet) {
        try {
            close(resultSet);
        } catch (SQLException e) {
            // quiet
        }
    }

    private static void close(ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
    }

    public static void closeQuietly(Connection conn) {
        try {
            close(conn);
        } catch (SQLException e) { // NOPMD
            // quiet
        }
    }

    public static void close(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

}
