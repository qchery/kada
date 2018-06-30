package com.qchery.kada.builder.mybatis;

import com.qchery.kada.descriptor.Mapping;

/**
 * @author Chery
 * @date 2018/4/15 11:11
 */
public interface MybatisContentBuilder {

    /**
     * <mapper namespace="cn.chery.cmail.antispam.dao.AsHoneypotDao">
     *     <resultMap type="asHoneypot" id="FullResultMap">
     *         <id property="honeypotId" column="honeypot_id"/>
     *         <result property="emailAddr" column="email_addr"/>
     *     </resultMap>
     * </mapper>
     */
    String build(Mapping mapping);

}
