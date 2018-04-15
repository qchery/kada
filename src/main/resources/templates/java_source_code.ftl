<#assign className = classInfo.className>
<#assign fieldInfos = classInfo.fieldInfos>
package ${classInfo.type};

<#list classInfo.importTypes as importType>
import ${importType};
</#list>
import java.io.Serializable;

/**
 * ${classInfo.comment!className}
 *
 * @author Chery
 * @date ${.now}
 */
public class ${className} implements Serializable {

<#list fieldInfos as fieldInfo>
    <#if fieldInfo.comment??>
    /**
     * ${fieldInfo.comment}
     */
    </#if>
    private ${fieldInfo.simpleType} ${fieldInfo.fieldName};
</#list>
<#macro genGetSet fieldInfos>
<#list fieldInfos as fieldInfo>
    <#local fieldName = fieldInfo.fieldName>
    <#local fcuFieldName = fieldInfo.fcuFieldName>
    <#local simpleType = fieldInfo.simpleType>

    public void set${fcuFieldName}(${simpleType} ${fieldName}) {
        this.${fieldName} = ${fieldName};
    }

    public ${simpleType} get${fcuFieldName}() {
        return this.${fieldName};
    }
</#list>
</#macro>
<@genGetSet fieldInfos />

    @Override
    public String toString() {
        return "${className} ["
        <#list fieldInfos as fieldInfo>
            + "<#if fieldInfo?index != 0>, </#if>${fieldInfo.fieldName} = " + ${fieldInfo.fieldName}
        </#list>
        + "]";
    }
}
