package com.qchery.kada.utils;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringWriter;

public class XMLUtils {

    public static String toXML(Document document) {
        String content;
        try (StringWriter stringWriter = new StringWriter()) {
            OutputFormat outputFormat = OutputFormat.createPrettyPrint();
            outputFormat.setTrimText(false);
            XMLWriter xmlWriter = new XMLWriter(stringWriter, outputFormat);
            xmlWriter.write(document);
            content = stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return content;
    }

}
