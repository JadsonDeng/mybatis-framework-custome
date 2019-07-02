package com.jadson.study.config;

public class SqlSource {

    private String sqlText;

    public SqlSource(String sqlText) {
        this.sqlText = sqlText;
    }

    public BoundSql getBoundSql() {
        // 解析sql文本

        // 将解析之后的sql语句，和解析出来的sql参数，使用组合模式绑定到一个类中
        return new BoundSql();
    }
}
