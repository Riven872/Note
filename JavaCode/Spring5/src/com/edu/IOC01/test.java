package com.edu.IOC01;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
    @Test
    public void testUser1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        User user = context.getBean("user", User.class);
        user.testDemo();
    }

    @Test
    public void testOrder1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        Order order = context.getBean("order", Order.class);
        order.testOrderDemo();
    }
}
