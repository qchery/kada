# Gradle Kada Plugin

Gradle Kada Plugin　用于通过数据库表结构逆向生成对应的实体类。

## Usage

想要使用此插件，首先，在 buildscript 引入。

```groovy
buildscript {
    repositories {
        mavenLocal()
    }

    dependencies {
        classpath "com.qchery:kada-gradle-plugin:x.x.x"
    }
}
```

其次，定义生成任务，配置好数据库在相关参数，及生成后文件的包信息，其中 `host` 与 `port` 为非必填，默认为 `localhost` 与 `3306`。

```groovy
import com.qchery.kada.plugin.KadaTask

task generateEntity(type: KadaTask) {
    packageName = "com.qchery.kada"
    connect {
        host = "localhost"
        port = 3306
        userName = "root"
        password = "123456"
        dbName = "jeesite"
    }
}
```

此时，会扫描全库所有表生成实体类，如若只需要选择其中的部分表进行生成，可自定义 tableNameFileter　进行过滤，如：只对以 `cms_` 开头的表进行操作。

```groovy
import com.qchery.kada.plugin.KadaTask
import com.qchery.kada.filter.TableNameFilter

task generateEntity(type: KadaTask) {
    ...

    tableNameFilter = new TableNameFilter() {
        @Override
        boolean accept(String tableName) {
            return tableName.startsWith("cms_")
        }
    }
}
```

如若对命名转换不满意，可自定义 NameConvertor，如：为所有实体增加 `Entity` 后缀。

```groovy
import com.qchery.kada.plugin.KadaTask
import com.qchery.kada.convertor.DefaultNameConvertor

task generateEntity(type: KadaTask) {
    ...

    nameConvertor = new DefaultNameConvertor() {
        @Override
        String toClassName(String tableName) {
            super.toClassName(tableName) + "Entity"
        }
    }
}
```