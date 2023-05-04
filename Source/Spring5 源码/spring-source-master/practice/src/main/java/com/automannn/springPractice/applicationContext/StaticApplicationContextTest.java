package com.automannn.springPractice.applicationContext;

import org.springframework.context.support.StaticApplicationContext;

/**
 * @author chenkh
 * @time 2021/8/19 10:26
 */
public class StaticApplicationContextTest {
    public static void main(String[] args) {
        StaticApplicationContext context =new StaticApplicationContext();
        context.registerSingleton("myAA",AA.class);
        context.registerPrototype("myBB",BB.class);

        //启动上下文
        context.refresh();

        System.out.println(context.getId());
        System.out.println(context.getDisplayName());
        System.out.println(context.getApplicationName());
        System.out.println(context.getStartupDate());

        context.getBean("myAA");
        context.getBean("myBB");
        System.out.println(context.getBean("myAA"));
        System.out.println(context.getBean("myBB"));
        System.out.println(context.getBean("myBB"));

    }

    static class AA{}
    static class BB{}
}
