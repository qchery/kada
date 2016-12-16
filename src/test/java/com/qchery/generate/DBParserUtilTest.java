package com.qchery.generate;

import com.qchery.generate.builder.HibernateBuilder;
import com.qchery.generate.builder.JavaBuilder;
import com.qchery.generate.builder.MybatisBuilder;
import com.qchery.generate.db.ConnectParam;
import com.qchery.generate.db.DBHelper;
import com.qchery.generate.db.DBHelperFactory;
import org.junit.Before;
import org.junit.Test;

import static com.qchery.generate.db.ConnectParam.ORACLE;

public class DBParserUtilTest {
    
    private DBHelperFactory driverFactory;
    private DBOrmer grnerator;
    
    @Before
    public void init() {
        this.driverFactory = new DBHelperFactory();
        ConnectParam param = new ConnectParam("localhost", 3306, "crm", "root", "qinrui");
        this.grnerator = new DBOrmer(driverFactory.getDbHelper(param), null);
    }
    
    @Test
    public void testDbWithMysql() {
        grnerator.setFileBuilder(new JavaBuilder());
        grnerator.generateFile("sys_user");
    }
    
    @Test
    public void testDbWithOracle() {
        ConnectParam param = new ConnectParam(ORACLE, "172.30.3.114", 1521, "devorcl", "yjsdata", "dev123");
        DBHelper dbDriver = driverFactory.getDbHelper(param);
        grnerator = new DBOrmer(dbDriver, new JavaBuilder());
        grnerator.generateFile("ph_credit_accountbaseinfo_py");
    }
    
    @Test
    public void testDbWithMybatisMysql() {
        grnerator.setFileBuilder(new MybatisBuilder());
        grnerator.generateFile("sys_user");
    }
    
    @Test
    public void testDbWithHibernateMysql() {
        grnerator.setFileBuilder(new HibernateBuilder());
        grnerator.generateFile("sys_user");
    }
}
