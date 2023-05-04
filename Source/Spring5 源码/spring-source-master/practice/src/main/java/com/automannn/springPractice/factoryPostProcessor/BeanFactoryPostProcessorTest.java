package com.automannn.springPractice.factoryPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author chenkh
 * @time 2021/9/7 12:11
 */
public class BeanFactoryPostProcessorTest {
    public static void main(String[] args) {
        BeanFactoryPostProcessor factoryPostProcessor = new BeanFactoryPostProcessor() {
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
                beanFactory.registerSingleton("myBeanTest",new Object());
                /*xxx: beanDefinitionProcessor只能优先注册，否则无法处理 */
                BeanDefinitionRegistryPostProcessor beanDefinitionRegistryPostProcessor = new BeanDefinitionRegistryPostProcessor() {
                    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
                        GenericBeanDefinition myGeanericBeanDefinition= new GenericBeanDefinition();
                        myGeanericBeanDefinition.setBeanClass(MyBean1.class);
                        registry.registerBeanDefinition("myBean1",myGeanericBeanDefinition);
                    }

                    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
                        BeanPostProcessor postProcessor = new BeanPostProcessor() {
                            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                                return null;
                            }

                            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                                return null;
                            }
                        };
                        beanFactory.addBeanPostProcessor(postProcessor);
                    }
                };
                beanFactory.registerSingleton("beanDefinitionRegistryPostProcessor",beanDefinitionRegistryPostProcessor);
            }
        };
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        applicationContext.addBeanFactoryPostProcessor(factoryPostProcessor);
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(MyBean.class);
        applicationContext.registerBeanDefinition("myBean",beanDefinition);
        applicationContext.refresh();
        applicationContext.getBean(MyBean.class);
    }

    static class MyBean{}
    static class MyBean1{}
}
