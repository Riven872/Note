package com.automannn.springPractice.applicationContext;

import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * @author chenkh
 * @time 2021/8/19 12:37
 */
public class GenericXmlApplicationContextTest {
    public static void main(String[] args) {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext("classpath:xmlApplication.xml");

        context.getBean("myA");
        context.getBean("myB");
        context.getBean("myC");
        System.out.println(context.getBean("myA"));
        System.out.println(context.getBean("myB"));
        System.out.println(context.getBean("myC"));


    }

    static class A{}
    static class B{}
    static class C{}
}
