package com.jadson.study;

import com.jadson.study.framework.SqlSession;
import com.jadson.study.framework.SqlSessionFactory;

public class UserDaoImpl implements UserDao {


    private SqlSessionFactory sqlSessionFactory;

    public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public User queryOneById(Integer id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = sqlSession.selectOne("test.queryOneById", id);
        return user;
    }
}
