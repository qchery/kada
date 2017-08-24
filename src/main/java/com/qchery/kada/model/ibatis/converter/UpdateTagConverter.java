package com.qchery.kada.model.ibatis.converter;

import com.qchery.kada.model.ibatis.UpdateTag;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * @author Chery
 * @date 2017/8/24 - 下午10:33
 */
public class UpdateTagConverter implements Converter {

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        UpdateTag updateTag = (UpdateTag) source;

        String updateTagId = updateTag.getId();
        if (null != updateTagId) {
            writer.addAttribute("id", updateTagId);
        }
        writer.setValue("\n    "+updateTag.getPrefix()+"\n    ");
        context.convertAnother(updateTag.getSetTag());
        writer.setValue("\n    "+updateTag.getSuffix()+"\n  ");
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        return null;
    }

    @Override
    public boolean canConvert(Class type) {
        return type == UpdateTag.class;
    }
}
