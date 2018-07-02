package com.qchery.kada.db;

import com.qchery.kada.exception.ConfigException;

/**
 * 连接参数
 *
 * @author Chery
 * @date 2016年5月15日 - 下午9:27:30
 */
public class ConnectParam {
    public static final String MYSQL = "mysql";
    public static final String ORACLE = "oracle";

    private String type = MYSQL;        // 类型
    private String host = "localhost";  // 主机地址
    private Integer port;       // 端口号
    private String database;      // 数据库名
    private String username;    // 用户名
    private String password;    // 用户密码

    public static ConnectParamBuilder create() {
        return new ConnectParamBuilder();
    }

    public String getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        Integer resultPort = null;
        if (null == port) {
            if (MYSQL.equals(type)) {
                resultPort = 3306;
            } else if (ORACLE.equals(type)) {
                resultPort = 1521;
            }
        } else {
            resultPort = port;
        }
        return resultPort;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static class ConnectParamBuilder {
        private String type;        // 类型
        private String host;        // 主机地址
        private Integer port;       // 端口号
        private String database;      // 数据库名
        private String username;    // 用户名
        private String password;    // 用户密码

        private ConnectParamBuilder() {
        }

        public ConnectParam build() {
            ConnectParam connectParam = new ConnectParam();
            if (null != type) {
                connectParam.type = type;
            }

            if (null != host) {
                connectParam.host = host;
            }

            if (port != null) {
                connectParam.port = port;
            }

            if (null == database || null == username || null == password) {
                throw new ConfigException("数据库名、用户名、密码不能为空");
            }
            connectParam.database = database;
            connectParam.username = username;
            connectParam.password = password;
            return connectParam;
        }

        public ConnectParamBuilder type(String type) {
            this.type = type;
            return this;
        }

        public ConnectParamBuilder host(String host) {
            this.host = host;
            return this;
        }

        public ConnectParamBuilder port(Integer port) {
            this.port = port;
            return this;
        }

        public ConnectParamBuilder databaseName(String databaseName) {
            this.database = databaseName;
            return this;
        }

        public ConnectParamBuilder userName(String userName) {
            this.username = userName;
            return this;
        }

        public ConnectParamBuilder password(String password) {
            this.password = password;
            return this;
        }
    }
}
