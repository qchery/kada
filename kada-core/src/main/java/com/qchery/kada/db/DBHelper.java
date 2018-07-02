package com.qchery.kada.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DBHelper {

    ConnectParam connectParam;

    private Logger logger = LoggerFactory.getLogger(DBHelper.class);

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
     * @throws SQLException 获取数据库异常
     */
    public Connection getConnection() throws SQLException {
        String url = getLink(connectParam.getHost(), connectParam.getPort(), connectParam.getDatabase());
        try {
            Class.forName(getDriverName());
        } catch (ClassNotFoundException e) {
            logger.error("msg={} | driverName={}", "驱动类配置错误", getDriverName());
            throw new IllegalArgumentException("驱动类配置错误");
        }
        return DriverManager.getConnection(url, connectParam.getUsername(), connectParam.getPassword());
    }
}
