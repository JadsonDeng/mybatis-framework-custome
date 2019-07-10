package com.jadson.study.spring;

public interface ResourceLoader {

    boolean canRead(String location);

    Resource load(String location);
}
