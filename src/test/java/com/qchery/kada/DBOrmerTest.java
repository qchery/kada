package com.qchery.kada;

import com.qchery.kada.builder.HibernateBuilder;
import com.qchery.kada.builder.JavaBuilder;
import com.qchery.kada.builder.MybatisBuilder;
import com.qchery.kada.convertor.IgnoreSuffixNameConvertor;
import com.qchery.kada.db.ConnectParam;
import com.qchery.kada.db.DBHelperFactory;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;

import static com.qchery.kada.db.ConnectParam.ORACLE;

public class DBOrmerTest {
    
    private DBHelperFactory dbHelperFactory;
    private ConnectParam mysqlConnectParam;
    private ConnectParam oracleConnectParam;

    @Before
    public void init() {
        this.dbHelperFactory = new DBHelperFactory();
        ConnectParam.ConnectParamBuilder connectParamBuilder = new ConnectParam.ConnectParamBuilder();
        mysqlConnectParam = connectParamBuilder.databaseName("jeesite")
                .userName("root").password("123456").build();
        oracleConnectParam = connectParamBuilder.type(ORACLE)
                .host("172.30.3.114").databaseName("devorcl")
                .userName("data").password("dev123").build();
    }

    @Test
    public void testDbWithMysql() {
        IgnoreSuffixNameConvertor nameConvertor = new IgnoreSuffixNameConvertor();
        nameConvertor.excludeSuffix("ACT").excludeSuffix("gen");
        DBOrmer dbOrmer = new DBOrmer.DBOrmerBuilder()
                .dbHelper(dbHelperFactory.getDbHelper(mysqlConnectParam))
                .fileBuilder(new JavaBuilder())
                .userJavaType(true)
                .nameConvertor(nameConvertor).build();
        dbOrmer.generateFile();
    }
    
    @Test
    public void testDbWithOracle() {
        DBOrmer dbOrmer = new DBOrmer.DBOrmerBuilder()
                .dbHelper(dbHelperFactory.getDbHelper(oracleConnectParam))
                .nameConvertor(new IgnoreSuffixNameConvertor())
                .fileBuilder(new JavaBuilder())
                .build();
        dbOrmer.generateFile("ph_credit_accountbaseinfo_py");
    }
    
    @Test
    public void testDbWithMybatisMysql() {
        IgnoreSuffixNameConvertor nameConvertor = new IgnoreSuffixNameConvertor();
        nameConvertor.excludeSuffix("ACT").excludeSuffix("gen");
        DBOrmer dbOrmer = new DBOrmer.DBOrmerBuilder()
                .dbHelper(dbHelperFactory.getDbHelper(mysqlConnectParam))
                .fileBuilder(new MybatisBuilder())
                .charset(Charset.forName("GBK"))
                .nameConvertor(nameConvertor).build();
        dbOrmer.generateFile();
    }
    
    @Test
    public void testDbWithHibernateMysql() {
        DBOrmer dbOrmer = new DBOrmer.DBOrmerBuilder()
                .dbHelper(dbHelperFactory.getDbHelper(mysqlConnectParam))
                .nameConvertor(new IgnoreSuffixNameConvertor())
                .fileBuilder(new HibernateBuilder())
                .build();
        dbOrmer.generateFile();
    }
}
