package com.qchery.kada.builder;

import com.qchery.kada.descriptor.Mapping;

import java.nio.charset.Charset;

/**
 * @author Chery
 * @date 2018/7/3 21:56
 */
public interface ContentBuilder {

    String build(Charset charset, Mapping mapping);

}
