package com.qchery.kada.scanner;

import com.qchery.kada.descriptor.db.ColumnDescriptor;
import com.qchery.kada.descriptor.db.TableDescriptor;
import com.qchery.kada.db.ConnectParam;
import com.qchery.kada.db.DBHelperFactory;
import org.junit.Test;

import java.util.List;

/**
 * @author Chery
 * @date 2017/5/4 - 下午10:50
 */
public class DefaultDBScannerTest {
    @Test
    public void scannerTables() throws Exception {
        DBHelperFactory dbHelperFactory = new DBHelperFactory();
        ConnectParam.ConnectParamBuilder connectParamBuilder = new ConnectParam.ConnectParamBuilder();
        ConnectParam connectParam = connectParamBuilder.databaseName("innereval")
                .host("172.30.3.112").userName("innerevaldata").password("1*mysql").build();
        List<TableDescriptor> tableDescriptors = new DefaultDBScanner().scannerTables(dbHelperFactory.getDbHelper(connectParam).getConnection());
        System.out.println(tableDescriptors);
    }

    @Test
    public void scannerTable() throws Exception {
        DBHelperFactory dbHelperFactory = new DBHelperFactory();
        ConnectParam.ConnectParamBuilder connectParamBuilder = new ConnectParam.ConnectParamBuilder();
        ConnectParam connectParam = connectParamBuilder.databaseName("jeesite")
                .userName("root").password("123456").build();
        List<ColumnDescriptor> columnDescriptors = new DefaultDBScanner().scannerColumns(dbHelperFactory.getDbHelper(connectParam).getConnection(), "sys_user");
        System.out.println(columnDescriptors);
    }

}