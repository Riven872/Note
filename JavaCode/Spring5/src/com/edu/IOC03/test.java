package com.edu.IOC03;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
    @Test
    public void test1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean_IOC3.xml");
        Stu stu = context.getBean("stu", Stu.class);
        System.out.println(stu);
    }

    @Test
    public void test2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("BookBean.xml");
        Stu stu = context.getBean("stu", Stu.class);
        System.out.println(stu);
    }

    @Test
    public void test3() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean_IOC3.xml");
        Stu stu = context.getBean("stu", Stu.class);
        System.out.println(stu);
    }
}
