package com.jadson.study.spring.convertor;

public interface TypedConvertor {

    boolean isType(Class<?> clazz);

    Object convert(String source);
}
