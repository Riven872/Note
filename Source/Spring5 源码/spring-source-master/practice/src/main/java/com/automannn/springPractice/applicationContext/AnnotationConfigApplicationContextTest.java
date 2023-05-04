package com.automannn.springPractice.applicationContext;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author chenkh
 * @time 2021/8/20 11:35
 */
public class AnnotationConfigApplicationContextTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.automannn.springPractice.applicationContext");

        System.out.println(context.getBean("annotationConfigApplicationContextTest.MyABean"));
        System.out.println(context.getBean("annotationConfigApplicationContextTest.MyBBean"));

    }

    @Component
    static class MyABean{}
    @Component
    static class MyBBean{}
}
