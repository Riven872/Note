package com.edu.test;


import com.edu.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
    @Test
    public void test1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        UserService service = context.getBean("userService", UserService.class);
        service.exchange();
    }
}
