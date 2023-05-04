package com.automannn.springPractice.applicationContext;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author chenkh
 * @time 2021/8/18 17:48
 */
public class GenericApplicationContextTest {
    public static void main(String[] args) {
        //使用常规的上下文
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(A.class);
        BeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClassName(B.class.getName());
        context.registerBeanDefinition("myB",beanDefinition);
        System.out.println(context.getApplicationName());
        System.out.println(context.getId());
        System.out.println(context.getStartupDate());

        //该方法必须被调用
        context.refresh();


        context.getBean(A.class);
        context.getBean("myB");
        System.out.println(context.getBean(A.class));
        System.out.println(context.getBean("myB"));

    }

    static class A{}
    static class B{}
}
