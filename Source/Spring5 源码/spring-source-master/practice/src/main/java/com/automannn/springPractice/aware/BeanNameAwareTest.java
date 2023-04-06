package com.automannn.springPractice.aware;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author chenkh
 * @time 2021/9/2 15:08
 */
public class BeanNameAwareTest {
    public static void main(String[] args) {
        //原始容器验证
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(MyBeanAwareTest.class);
        factory.registerBeanDefinition("myAware",beanDefinition);
        factory.getBean(MyBeanAwareTest.class);
        //高级容器验证
        GenericApplicationContext context = new GenericApplicationContext();
        GenericBeanDefinition environmentBeanDefinition = new GenericBeanDefinition();
        environmentBeanDefinition.setBeanClass(MyBeanAwareEnvironmentTest.class);
        context.registerBeanDefinition("myEnvironmentBean",environmentBeanDefinition);
        context.refresh();
        context.getBean(MyBeanAwareEnvironmentTest.class);
    }

    static class MyBeanAwareTest implements BeanNameAware{

        public void setBeanName(String name) {
            System.out.println("注册的bean名称是:"+name);
        }
    }

    static class MyBeanAwareEnvironmentTest implements EnvironmentAware{

        public void setEnvironment(Environment environment) {
            System.out.println("环境感知:"+environment.getActiveProfiles());
        }
    }
}
