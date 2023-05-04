package com.automannn.springPractice.factoryPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author chenkh
 * @time 2021/9/7 12:57
 */

public class BeanDefinitionRegistryPostProcessorTest {
    public static void main(String[] args) {
        /*xxx: 如果要在beanDefinitionRegistryPostprocessor的 postProcessBeanDefinitionRegistry方法,嵌套注册并使其生效的话，必须在 priorityOrdered或者ordered进行注册。 否则无法生效*/
        /*xxx: 换言之，在 beanFactoryPostProcessor中注册的 beanDefinitionRegistryPostprocessor的postProcessBeanDefinitionRegistry方法不能生效*/
        BeanDefinitionRegistryPostProcessor beanDefinitionRegistryPostProcessor = new BeanDefinitionRegistryPostProcessor() {
            public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
                GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
                genericBeanDefinition.setBeanClass(MyBeanTest.class);
                registry.registerBeanDefinition("myBeanTest",genericBeanDefinition);

                GenericBeanDefinition genericBeanDefinition1 = new GenericBeanDefinition();
                genericBeanDefinition1.setBeanClass(MyBeanDefinitionRegistryPostProcessor.class);
                registry.registerBeanDefinition("myBeanDefinitionRegistryPostProcessor",genericBeanDefinition1);
            }

            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

                BeanDefinitionRegistryPostProcessor beanDefinitionRegistryPostProcessor = new BeanDefinitionRegistryPostProcessor() {
                    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
                        GenericBeanDefinition myGeanericBeanDefinition= new GenericBeanDefinition();
                        myGeanericBeanDefinition.setBeanClass(BeanFactoryPostProcessorTest.MyBean1.class);
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
        applicationContext.addBeanFactoryPostProcessor(beanDefinitionRegistryPostProcessor);
        applicationContext.refresh();
        applicationContext.getBean(MyBeanTest.class);
    }

    static class MyBeanTest{}

    static class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor{
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
            System.out.println("注册beanDefinition");
        }

        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            System.out.println("注册到容器");
        }
    }
}
