package com.jadson.study.spring;

public class ClassPathResourceLoader implements ResourceLoader {

    @Override
    public Resource load(String location) {
        return new ClassPathResource(location);
    }
}
