package com.jadson.study;

import com.jadson.study.spring.DefaultListableBeanFactory;
import org.junit.Test;

public class SpringTest {


    @Test
    public void test() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory("classpath:beans.xml");
    }
}
