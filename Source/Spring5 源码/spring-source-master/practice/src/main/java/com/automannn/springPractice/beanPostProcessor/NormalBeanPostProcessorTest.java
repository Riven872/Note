package com.automannn.springPractice.beanPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * @author chenkh
 * @time 2021/9/7 11:10
 */
/*xxx: 普通 beanPostProcessor，practice demo */
public class NormalBeanPostProcessorTest {

    public static void main(String[] args) {
        BeanPostProcessor postProcessor = new BeanPostProcessor() {
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                return null;
            }

            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return null;
            }
        };

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        GenericBeanDefinition beanDefinition= new GenericBeanDefinition();
        beanDefinition.setBeanClass(MyBeanTest.class);
        beanFactory.registerBeanDefinition("myBeanTest",beanDefinition);
        beanFactory.addBeanPostProcessor(postProcessor);
        beanFactory.getBean(MyBeanTest.class);
    }

    static class MyBeanTest{}
}
