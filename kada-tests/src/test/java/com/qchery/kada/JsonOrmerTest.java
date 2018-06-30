package com.qchery.kada;

import com.qchery.kada.builder.java.TemplateJavaContentBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author Chery
 * @date 2018/4/6 11:53
 */
public class JsonOrmerTest {

    @Test
    public void generateFile() throws IOException {
        String path = this.getClass().getResource("/test.json").getPath();
        String json = FileUtils.readFileToString(new File(path), Charset.forName("UTF-8"));
        JsonOrmer jsonOrmer = new JsonOrmer(System.getProperty("user.dir"), new TemplateJavaContentBuilder());
        jsonOrmer.generateFile("com.qchery.kada", "User", json);
    }
}