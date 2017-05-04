package com.qchery.kada.scanner;

import com.qchery.kada.TableDescriptor;
import com.qchery.kada.db.ConnectParam;
import com.qchery.kada.db.DBHelperFactory;
import org.junit.Test;

import static com.qchery.kada.db.ConnectParam.ORACLE;
import static org.junit.Assert.*;

/**
 * @author Chery
 * @date 2017/5/4 - 下午10:50
 */
public class DefaultDBScannerTest {
    @Test
    public void scannerTable() throws Exception {
        DBHelperFactory dbHelperFactory = new DBHelperFactory();
        ConnectParam.ConnectParamBuilder connectParamBuilder = new ConnectParam.ConnectParamBuilder();
        ConnectParam connectParam = connectParamBuilder.databaseName("jeesite")
                .userName("root").password("123456").build();
        TableDescriptor tableDescriptor = new DefaultDBScanner().scannerTable(dbHelperFactory.getDbHelper(connectParam).getConnection(), "sys_user");
        System.out.println(tableDescriptor);
    }

}