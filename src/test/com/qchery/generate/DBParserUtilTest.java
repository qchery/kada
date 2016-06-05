package test.com.qchery.generate;

import static com.qchery.generate.db.ConnectParam.ORACLE;

import org.junit.Before;
import org.junit.Test;

import com.qchery.generate.DBOrmer;
import com.qchery.generate.builder.HibernateBuilder;
import com.qchery.generate.builder.JavaBuilder;
import com.qchery.generate.builder.MybatisBuilder;
import com.qchery.generate.db.ConnectParam;
import com.qchery.generate.db.DBHelper;
import com.qchery.generate.db.DBHelperFactory;

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
        ConnectParam param = new ConnectParam(ORACLE, "127.0.0.1", 1521, "xe", "scott", "tiger");
        DBHelper dbDriver = driverFactory.getDbHelper(param);
        grnerator = new DBOrmer(dbDriver, new JavaBuilder());
        grnerator.generateFile("spring_user");
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
