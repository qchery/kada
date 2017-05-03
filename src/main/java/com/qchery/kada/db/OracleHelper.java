package com.qchery.kada.db;

/**
 * Oracle 数据辅助类
 * @author Chery
 * @date 2016年5月15日 - 下午7:50:49
 */
public class OracleHelper extends DBHelper {
    
    private static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
    
    public OracleHelper(ConnectParam connectParam) {
        super(connectParam);
    }
    
    @Override
    protected String getLink(String ipAddr, Integer port, String dbName) {
        return String.format("jdbc:oracle:thin:@//%s:%s/%s", ipAddr, port, dbName);
    }

    @Override
    public String getTableNames() {
        return "SELECT table_name FROM user_tables";
    }

    @Override
    protected String getDriverName() {
        return DRIVER_NAME;
    }

}
