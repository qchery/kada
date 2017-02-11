package com.qchery.generate;

import com.qchery.generate.builder.HibernateBuilder;
import com.qchery.generate.builder.JavaBuilder;
import com.qchery.generate.builder.MybatisBuilder;
import com.qchery.generate.convertor.IgnoreSuffixNameConvertor;
import com.qchery.generate.db.ConnectParam;
import com.qchery.generate.db.DBHelperFactory;
import org.junit.Before;
import org.junit.Test;

import static com.qchery.generate.db.ConnectParam.ORACLE;

public class DBParserUtilTest {
    
    private DBHelperFactory dbHelperFactory;
    private ConnectParam mysqlConnectParam;
    private ConnectParam oracleConnectParam;

    @Before
    public void init() {
        this.dbHelperFactory = new DBHelperFactory();
        mysqlConnectParam = new ConnectParam("localhost", 3306, "jeesite", "root", "123456");
        oracleConnectParam = new ConnectParam(ORACLE, "172.30.3.114", 1521, "devorcl", "yjsdata", "dev123");
    }
    
    @Test
    public void testDbWithMysql() {
        IgnoreSuffixNameConvertor nameConvertor = new IgnoreSuffixNameConvertor();
        nameConvertor.excludeSuffix("ACT").excludeSuffix("gen");
        DBOrmer dbOrmer = new DBOrmer.DBOrmerBuilder()
                .setDbHelper(dbHelperFactory.getDbHelper(mysqlConnectParam))
                .setFileBuilder(new JavaBuilder())
                .setNameConvertor(nameConvertor).build();
        dbOrmer.generateFile();
    }
    
    @Test
    public void testDbWithOracle() {
        DBOrmer dbOrmer = new DBOrmer.DBOrmerBuilder()
                .setDbHelper(dbHelperFactory.getDbHelper(oracleConnectParam))
                .setNameConvertor(new IgnoreSuffixNameConvertor())
                .setFileBuilder(new JavaBuilder())
                .build();
        dbOrmer.generateFile("ph_credit_accountbaseinfo_py");
    }
    
    @Test
    public void testDbWithMybatisMysql() {
        DBOrmer dbOrmer = new DBOrmer.DBOrmerBuilder()
                .setDbHelper(dbHelperFactory.getDbHelper(oracleConnectParam))
                .setNameConvertor(new IgnoreSuffixNameConvertor())
                .setFileBuilder(new MybatisBuilder())
                .build();
        dbOrmer.generateFile("sys_user");
    }
    
    @Test
    public void testDbWithHibernateMysql() {
        DBOrmer dbOrmer = new DBOrmer.DBOrmerBuilder()
                .setDbHelper(dbHelperFactory.getDbHelper(oracleConnectParam))
                .setNameConvertor(new IgnoreSuffixNameConvertor())
                .setFileBuilder(new HibernateBuilder())
                .build();
        dbOrmer.generateFile("sys_user");
    }
}
