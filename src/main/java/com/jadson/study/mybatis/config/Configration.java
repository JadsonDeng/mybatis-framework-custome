package com.jadson.study.mybatis.config;

import javax.sql.DataSource;

public class Configration {

    private DataSource dataSource;


    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
