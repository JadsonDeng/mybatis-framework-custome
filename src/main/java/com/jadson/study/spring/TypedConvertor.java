package com.jadson.study.spring;

public interface TypedConvertor {

    boolean isType(Class<?> clazz);

    Object convert(String source);
}
