package com.jadson.study.spring.convertor;

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
