package com.qchery.kada.builder.java;

import com.qchery.kada.descriptor.java.IClassInfo;

import java.util.List;

/**
 * Lombok Java 实体生成器
 *
 * @author Chery
 * @date 2018/11/25 11:05
 */
public class OriginalLombokJavaContentBuilder extends OriginalJavaContentBuilder {
    @Override
    protected String declareClassAnnotations() {
        return "@Setter\n@Getter\n@ToString\n";
    }

    @Override
    protected String getMainContent(IClassInfo classInfo) {
        return declareFields(classInfo).toString();
    }

    @Override
    protected List<String> getExtraImportTypes() {
        List<String> importTypes = super.getExtraImportTypes();
        importTypes.add("lombok.Setter");
        importTypes.add("lombok.Getter");
        importTypes.add("lombok.ToString");
        return importTypes;
    }
}
