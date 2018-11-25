package com.qchery.kada;

import com.qchery.kada.builder.hibernate.HibernateMappingFileBuilder;
import com.qchery.kada.builder.hibernate.OriginalHibernateContentBuilder;
import com.qchery.kada.builder.java.*;
import com.qchery.kada.builder.mybatis.MybatisMappingFileBuilder;
import com.qchery.kada.builder.mybatis.TemplateMybatisContentBuilder;
import com.qchery.kada.convertor.IgnorePrefixNameConvertor;
import com.qchery.kada.db.ConnectParam;
import com.qchery.kada.db.DBHelperFactory;
import com.qchery.kada.descriptor.java.AuthorInfo;
import org.junit.Before;
import org.junit.Test;

import static com.qchery.kada.db.ConnectParam.ORACLE;

public class DBOrmerTest {
    
    private DBHelperFactory dbHelperFactory;
    private ConnectParam mysqlConnectParam;
    private ConnectParam oracleConnectParam;

    @Before
    public void init() {
        this.dbHelperFactory = new DBHelperFactory();
        ConnectParam.ConnectParamBuilder connectParamBuilder = ConnectParam.create();
        mysqlConnectParam = connectParamBuilder.databaseName("jeesite")
                .userName("root").password("123456").build();
        oracleConnectParam = connectParamBuilder.type(ORACLE)
                .host("172.30.3.114").databaseName("devorcl")
                .userName("data").password("dev123").build();
    }

    @Test
    public void testDbWithMysql() {
        IgnorePrefixNameConvertor nameConvertor = new IgnorePrefixNameConvertor();
        OriginalJavaContentBuilder contentBuilder;
        boolean useLombok = false;
        if (useLombok) {
            contentBuilder = new OriginalLombokJavaContentBuilder();
        } else {
            contentBuilder = new OriginalCommonJavaContentBuilder();
        }
        AuthorInfo authorInfo = new AuthorInfo();
        authorInfo.setAuthor("chinrui1016@163.com");
        authorInfo.setCompany("www.chery.com");

        DBOrmer dbOrmer = DBOrmer.create()
                .dbHelper(dbHelperFactory.getDbHelper(mysqlConnectParam))
                .addFileBuilder(new JavaMappingFileBuilder(contentBuilder)).packageName("com.qchery.kada")
                .tableNameFilter(tableName -> tableName.startsWith("cms"))
                .authorInfo(authorInfo)
                .nameConvertor(nameConvertor).build();
        dbOrmer.generateFile();
    }
    
    @Test
    public void testDbWithOracle() {
        DBOrmer dbOrmer = DBOrmer.create()
                .dbHelper(dbHelperFactory.getDbHelper(oracleConnectParam))
                .nameConvertor(new IgnorePrefixNameConvertor())
                .addFileBuilder(new JavaMappingFileBuilder(new TemplateJavaContentBuilder()))
                .build();
        dbOrmer.generateFile("ph_credit_accountbaseinfo_py");
    }
    
    @Test
    public void testDbWithMybatisMysql() {
        IgnorePrefixNameConvertor nameConvertor = new IgnorePrefixNameConvertor();
        nameConvertor.excludeSuffix("ACT").excludeSuffix("gen");
        DBOrmer dbOrmer = DBOrmer.create()
                .dbHelper(dbHelperFactory.getDbHelper(mysqlConnectParam))
                .addFileBuilder(new MybatisMappingFileBuilder(new TemplateMybatisContentBuilder())).packageName("com.qchery.kada")
                .tableNameFilter(tableName -> tableName.startsWith("cms"))
                .nameConvertor(nameConvertor).build();
        dbOrmer.generateFile();
    }
    
    @Test
    public void testDbWithHibernateMysql() {
        DBOrmer dbOrmer = DBOrmer.create()
                .dbHelper(dbHelperFactory.getDbHelper(mysqlConnectParam))
                .nameConvertor(new IgnorePrefixNameConvertor())
                .addFileBuilder(new HibernateMappingFileBuilder(new OriginalHibernateContentBuilder()))
                .build();
        dbOrmer.generateFile();
    }
}
