package com.qchery.kada.db;

import java.util.HashMap;
import java.util.Map;

public class DBHelperFactory {
    
    private Map<ConnectParam, DBHelper> heplerCache = new HashMap<>();
    
    public DBHelper getDbHelper(ConnectParam connectParam) {
        
        if (null == heplerCache.get(connectParam)) {
            synchronized (this) {
                if (null == heplerCache.get(connectParam)) {
                    if (ConnectParam.MYSQL.equals(connectParam.getType())) {
                        heplerCache.put(connectParam, new MySQLHelper(connectParam));
                    } else if (ConnectParam.ORACLE.equals(connectParam.getType())) {
                        heplerCache.put(connectParam, new OracleHelper(connectParam));
                    }
                }
            }
        }
        
        return heplerCache.get(connectParam);
    }
}
