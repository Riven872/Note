package com.automannn.springPractice.beanPostProcessor;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.beans.PropertyDescriptor;

/**
 * @author chenkh
 * @time 2021/9/4 11:07
 */
public class InstantiationBeanPostProcessorTest {
    public static void main(String[] args) {
        InstantiationAwareBeanPostProcessor myBeanPostProcessor =new InstantiationAwareBeanPostProcessor() {
            public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
                if (beanClass ==MyBean.class){
                    try {
                        return beanClass.newInstance();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
                return false;
            }

            public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
                return null;
            }

            public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
                return null;
            }

            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                return null;
            }

            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return null;
            }
        };

        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
        beanNameAutoProxyCreator.setBeanNames("myBeanDefinition");

        DefaultListableBeanFactory beanFactory =new DefaultListableBeanFactory();
        beanFactory.addBeanPostProcessor(myBeanPostProcessor);
        beanFactory.addBeanPostProcessor(beanNameAutoProxyCreator);

        GenericBeanDefinition beanDefinition =new GenericBeanDefinition();
        beanDefinition.setBeanClass(MyBean.class);
        beanFactory.registerBeanDefinition("myBeanDefinition",beanDefinition);

        beanFactory.getBean(MyBean.class);
    }

    static class MyBean{}
}
