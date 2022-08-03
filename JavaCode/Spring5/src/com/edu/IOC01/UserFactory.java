package com.edu.IOC01;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserFactory {

    @Test
    public void test() {
        //加载Spring配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");

        //获取配置并创建对象
        User user = context.getBean("user", User.class);

        System.out.println(user);
    }

}
