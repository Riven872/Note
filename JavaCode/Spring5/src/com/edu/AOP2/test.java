package com.edu.AOP2;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
    @Test
    public void test1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("AspectJConfig.xml");
        User user = context.getBean("user", User.class);
        user.add();
    }
}
