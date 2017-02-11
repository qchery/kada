package com.qchery.generate.db;

/**
 * 连接参数
 * @author Chery
 * @date 2016年5月15日 - 下午9:27:30
 */
public class ConnectParam {
    public static final String MYSQL = "mysql";
    public static final String ORACLE = "oracle";
    
    private String type;        // 类型
    private String host;        // 主机地址
    private Integer port;       // 端口号
    private String dbName;      // 数据库名
    private String userName;    // 用户名
    private String password;    // 用户密码
    
    public ConnectParam(String host, Integer port, String dbName, String userName, String password) {
        this(MYSQL, host, port, dbName, userName, password);
    }
    
    public ConnectParam(String type, String host, Integer port, 
            String dbName, String userName, String password) {
        this.type = type;
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.userName = userName;
        this.password = password;
    }
    
    public String getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getDbName() {
        return dbName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
