package com.jadson.study.framework;

import java.util.List;

public interface SqlSession {

    <T> T selectOne(String statement, Object param);

    <T> List<T> selectList(String statement, Object param);
}
