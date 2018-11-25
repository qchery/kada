package com.qchery.kada.builder.java;

import com.qchery.kada.descriptor.java.FieldInfo;
import com.qchery.kada.descriptor.java.IClassInfo;

import java.util.List;

import static com.qchery.kada.descriptor.Space.ofEight;
import static com.qchery.kada.descriptor.Space.ofFour;
import static com.qchery.kada.descriptor.Space.ofSixteen;

/**
 * 常规Java实体生成器
 * 
 * @author Chery
 * @date 2018/11/25 10:45
 */
public class OriginalCommonJavaContentBuilder extends OriginalJavaContentBuilder {

    @Override
    protected String declareClassAnnotations() {
        return "";
    }

    @Override
    protected String getMainContent(IClassInfo classInfo) {
        StringBuilder fields = declareFields(classInfo);
        StringBuilder setGetMethods = declareSetGetMethods(classInfo);
        StringBuilder toStringMethod = declareToString(classInfo);
        return fields.append("\n").append(setGetMethods).append(toStringMethod).toString();
    }

    private StringBuilder declareSetGetMethods(IClassInfo classInfo) {
        StringBuilder methods = new StringBuilder();
        for (FieldInfo fieldInfo : classInfo.getFieldInfos()) {
            methods.append(formatGetMethod(fieldInfo));
            methods.append(formatSetMethod(fieldInfo));
        }
        return methods;
    }

    private String formatSetMethod(FieldInfo fieldInfo) {
        String fieldName = fieldInfo.getFieldName();
        String setMethod = String.format(ofFour() + "public void set%s(%s %s) {\n"
                        + ofEight() + "this.%s = %s;\n"
                        + ofFour() + "}\n\n", fieldInfo.getFcuFieldName(),
                fieldInfo.getSimpleType(), fieldName, fieldName, fieldName);
        return setMethod;
    }

    private String formatGetMethod(FieldInfo fieldInfo) {
        String getMethod = String.format(ofFour() + "public %s get%s() {\n"
                        + ofEight() + "return this.%s;\n"
                        + ofFour() + "}\n\n", fieldInfo.getSimpleType(),
                fieldInfo.getFcuFieldName(), fieldInfo.getFieldName());
        return getMethod;
    }

    private StringBuilder declareToString(IClassInfo classInfo) {
        List<FieldInfo> fieldInfos = classInfo.getFieldInfos();
        StringBuilder toStringMethod = new StringBuilder();
        toStringMethod.append(ofFour()).append("@Override\n")
                .append(ofFour()).append("public String toString() {\n")
                .append(ofEight()).append("return \"").append(classInfo.getClassName()).append(" [\"\n");
        for (int i = 0; i < fieldInfos.size(); i++) {
            FieldInfo fieldInfo = fieldInfos.get(i);
            toStringMethod.append(ofSixteen()).append("+ ").append("\"")
                    .append(fieldInfo.getFieldName()).append(" = \" + ")
                    .append(fieldInfo.getFieldName()).append(" + \"");
            if (i == fieldInfos.size() - 1) {
                toStringMethod.append("]\";\n");
            } else {
                toStringMethod.append(",\"\n");
            }
        }
        toStringMethod.append(ofFour()).append("}");
        return toStringMethod;
    }
}
