package com.automannn.springPractice.factory;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * @author chenkh
 * @time 2021/8/12 17:36
 */
public class DefaultListableBeanFactoryTest {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerSingleton("myA",new A());
        beanFactory.registerSingleton("myB",new B());
        beanFactory.registerSingleton("myC",new C());

        System.out.println(beanFactory.getBean("myA"));
        System.out.println(beanFactory.getBean("myB"));
        System.out.println(beanFactory.getBean("myC"));

        BeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClassName(MyBean.class.getName());
        beanFactory.registerBeanDefinition("myBean",beanDefinition);


        beanFactory.getBean("myBean");

        System.out.println(beanFactory.getBean("myBean"));


    }

    static class A{ }
    static class B{ }
    static class C{ }
    static class MyBean{}
}
