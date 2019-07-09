package com.jadson.study.spring;

import java.io.InputStream;

public class ClassPathResource implements Resource {

    private String location;

    public ClassPathResource(String location) {
        this.location = location;
    }

    @Override
    public InputStream getInpusStream() {
        return this.getClass().getClassLoader().getResourceAsStream(location);
    }
}
