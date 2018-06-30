package com.qchery.kada.db;

import java.util.HashMap;
import java.util.Map;

public class DBHelperFactory {
    
    private Map<ConnectParam, DBHelper> helperCache = new HashMap<>();
    
    public DBHelper getDbHelper(ConnectParam connectParam) {
        
        if (null == helperCache.get(connectParam)) {
            synchronized (this) {
                if (null == helperCache.get(connectParam)) {
                    if (ConnectParam.MYSQL.equals(connectParam.getType())) {
                        helperCache.put(connectParam, new MySQLHelper(connectParam));
                    } else if (ConnectParam.ORACLE.equals(connectParam.getType())) {
                        helperCache.put(connectParam, new OracleHelper(connectParam));
                    }
                }
            }
        }
        
        return helperCache.get(connectParam);
    }
}
