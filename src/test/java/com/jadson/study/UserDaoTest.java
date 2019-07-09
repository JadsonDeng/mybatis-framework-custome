package com.jadson.study;

import com.jadson.study.mybatis.framework.SqlSessionFactory;
import com.jadson.study.mybatis.framework.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

public class UserDaoTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() {

        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("SqlMapConfig.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

    }

    @Test
    public void testQueryOneById(){
//        UserDao userDao = new UserDaoImpl();
//        User user = userDao.queryOneById(1);
//        System.out.println(user );
    }
}
