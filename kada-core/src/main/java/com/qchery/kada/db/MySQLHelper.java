package com.qchery.kada.db;


import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> properties = new HashMap<>();
        properties.put("remarks", "true");
        properties.put("useInformationSchema", "true");
        properties.put("useUnicode", "true");
        properties.put("characterEncoding", "utf-8");

        StringBuilder link = new StringBuilder();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            link.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        return String.format("jdbc:mysql://%s:%s/%s?%s", ipAddr, port, dbName, link.substring(0, link.length() - 1));
    }

    @Override
    protected String getDriverName() {
        return DRIVER_NAME;
    }

}
