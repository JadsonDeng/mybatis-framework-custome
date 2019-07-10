package com.jadson.study.spring.convertor;

import com.jadson.study.spring.TypedConvertor;

public class StringTypedConvertor implements TypedConvertor {
    @Override
    public boolean isType(Class<?> clazz) {
        return clazz.equals(String.class);
    }

    @Override
    public Object convert(String source) {
        return source;
    }
}
