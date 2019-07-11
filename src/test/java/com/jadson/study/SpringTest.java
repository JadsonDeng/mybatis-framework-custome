package com.jadson.study;

import com.jadson.study.spring.BeanFactory;
import com.jadson.study.spring.DefaultListableBeanFactory;
import org.junit.Test;

public class SpringTest {


    @Test
    public void test() {
        BeanFactory beanFactory = new DefaultListableBeanFactory("classpath:beans.xml");
//        Object course = beanFactory.getBean("course");
//        System.out.println(course);
        Object student = beanFactory.getBean("student");
        System.out.println(student);
    }
}
