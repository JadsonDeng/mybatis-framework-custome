package com.jadson.study.framework;

import com.jadson.study.config.Configration;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configration configration;

    public DefaultSqlSessionFactory(Configration configration) {
        this.configration = configration;
    }

    @Override
    public SqlSession openSession() {
        return null;
    }
}
