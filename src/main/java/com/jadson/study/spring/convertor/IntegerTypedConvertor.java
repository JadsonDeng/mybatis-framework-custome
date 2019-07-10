package com.jadson.study.spring.convertor;

import com.jadson.study.spring.TypedConvertor;

public class IntegerTypedConvertor implements TypedConvertor {
    @Override
    public boolean isType(Class<?> clazz) {
        return clazz.equals(Integer.class) || clazz.equals(int.class);
    }

    @Override
    public Object convert(String source) {
        return Integer.valueOf(source);
    }
}
