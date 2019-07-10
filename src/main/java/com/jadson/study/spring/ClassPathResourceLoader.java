package com.jadson.study.spring;

public class ClassPathResourceLoader implements ResourceLoader {

    @Override
    public boolean canRead(String location) {
        if (location == null || "".equals(location)) {
            return false;
        }
        return location.startsWith("classpath:");
    }

    @Override
    public Resource load(String location) {
        return new ClassPathResource(location.replace("classpath:",""));
    }
}
