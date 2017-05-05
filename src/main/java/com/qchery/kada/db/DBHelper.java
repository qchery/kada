package com.qchery.kada.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DBHelper {

    ConnectParam connectParam;

    DBHelper(ConnectParam connectParam) {
        this.connectParam = connectParam;
    }

    /**
     * 获取数据库连接
     *
     * @param ipAddr IP地址
     * @param port   端口号
     * @param dbName 数据库名称
     * @return 数据库链接地址
     */
    protected abstract String getLink(String ipAddr, Integer port, String dbName);

    /**
     * 获取驱动名
     *
     * @return 驱动名称
     */
    protected abstract String getDriverName();

    /**
     * 获取连接
     *
     * @return 数据库连接
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        String url = getLink(connectParam.getHost(), connectParam.getPort(), connectParam.getDbName());
        try {
            Class.forName(getDriverName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, connectParam.getUserName(), connectParam.getPassword());
    }
}
