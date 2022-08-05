package com.edu.IOC4;

import com.edu.IOC03.Stu;
import com.edu.bean.Emp;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
    @Test
    public void test1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("MyBean.xml");
        Emp emp = context.getBean("myBean", Emp.class);
        System.out.println(emp);
    }

    @Test
    public void test2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("MyBean.xml");
        Cat cat1 = context.getBean("cat", Cat.class);
        Cat cat2 = context.getBean("cat", Cat.class);
        System.out.println(cat1.hashCode());
        System.out.println(cat2.hashCode());
    }
}
