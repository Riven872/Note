package com.edu.IOC02;

import com.edu.IOC02.service.UserService;
import com.edu.bean.Emp;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
    @Test
    public void test1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean2.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.add();
    }

    @Test
    public void test2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean4.xml");
        Emp emp = context.getBean("emp", Emp.class);
        System.out.println(emp);
    }
}
