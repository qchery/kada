package com.qchery.generate.db;

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
     * @param ipAddr
     * @param port
     * @param dbName
     * @return
     */
    protected abstract String getLink(String ipAddr , Integer port , String dbName);
    
    /**
     * 获取驱动名
     * @return
     */
    protected abstract String getDriverName();
    
    public abstract String getTableNames();

    /**
     * 获取连接
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        String url = getLink(connectParam.getHost(), connectParam.getPort(), connectParam.getDbName());
        try {
            Class.forName(getDriverName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url , connectParam.getUserName() , connectParam.getPassword());
    }
}
