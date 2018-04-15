package ${classInfo.type};

<#list classInfo.importTypes as importType>
import ${importType};
</#list>
import java.io.Serializable;

/**
 * ${classInfo.comment!classInfo.className}
 * @author Chery
 * @date ${.now}
 */
public class ${classInfo.className} implements Serializable {

<#list classInfo.fieldInfos as fieldInfo>
    <#if fieldInfo.comment??>
    /**
     * ${fieldInfo.comment}
     */
    </#if>
    private ${fieldInfo.simpleType} ${fieldInfo.fieldName};
</#list>
<#list classInfo.fieldInfos as fieldInfo>
    <#assign fieldName = fieldInfo.fieldName>
    <#assign fcuFieldName = fieldInfo.fcuFieldName>
    <#assign simpleType = fieldInfo.simpleType>

    public void set${fcuFieldName}(${simpleType} ${fieldName}) {
        this.${fieldName} = ${fieldName};
    }

    public ${simpleType} get${fcuFieldName}() {
        return this.${fieldName};
    }
</#list>

}
