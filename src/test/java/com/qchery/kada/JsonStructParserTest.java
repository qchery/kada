package com.qchery.kada;

import com.google.gson.Gson;
import com.qchery.kada.convertor.DefaultNameConvertor;
import com.qchery.kada.descriptor.java.IClassDescriptor;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author Chery
 * @date 2018/4/4 20:53
 */
public class JsonStructParserTest {

    @Test
    public void parse() throws IOException {
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/test.json");
        String json = FileUtils.readFileToString(file, Charset.forName("UTF-8"));
        JsonStructParser jsonStructParser = new JsonStructParser(new DefaultNameConvertor());
        IClassDescriptor classDescriptor = jsonStructParser.parse("com.qchery", "User", json);
        System.out.println(new Gson().toJson(classDescriptor));
    }
}