package com.jadson.study.spring.resource;

public interface ResourceLoader {

    boolean canRead(String location);

    Resource load(String location);
}
