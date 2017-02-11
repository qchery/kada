package com.qchery.generate.db;

import com.qchery.generate.exception.ConfigException;

/**
 * 连接参数
 *
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

    private ConnectParam() {
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

    public static class ConnectParamBuilder {
        private String type;        // 类型
        private String host;        // 主机地址
        private Integer port;       // 端口号
        private String dbName;      // 数据库名
        private String userName;    // 用户名
        private String password;    // 用户密码

        public ConnectParam build() {
            ConnectParam connectParam = new ConnectParam();
            if (null == type) {
                type = MYSQL;
            }
            connectParam.type = type;

            if (null == host) {
                host = "localhost";
            }
            connectParam.host = host;

            if (null == port) {
                if (MYSQL.equals(type)) {
                    port = 3306;
                } else if (ORACLE.equals(type)) {
                    port = 1521;
                }
            }
            connectParam.port = port;

            if (null == dbName || null == userName || null == password) {
                throw new ConfigException("数据库名、用户名、密码不能为空");
            }
            connectParam.dbName = dbName;
            connectParam.userName = userName;
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
            this.dbName = databaseName;
            return this;
        }

        public ConnectParamBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public ConnectParamBuilder password(String password) {
            this.password = password;
            return this;
        }
    }
}
