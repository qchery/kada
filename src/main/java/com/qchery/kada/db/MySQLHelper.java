package com.qchery.kada.db;


/**
 * Mysql 数据辅助类
 *
 * @author Chery
 * @date 2016年5月15日 - 下午7:51:03
 */
public class MySQLHelper extends DBHelper {

    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";

    public MySQLHelper(ConnectParam connectParam) {
        super(connectParam);
    }

    @Override
    protected String getLink(String ipAddr, Integer port, String dbName) {
        return String.format("jdbc:mysql://%s:%s/%s", ipAddr, port, dbName);
    }

    @Override
    protected String getDriverName() {
        return DRIVER_NAME;
    }

}
