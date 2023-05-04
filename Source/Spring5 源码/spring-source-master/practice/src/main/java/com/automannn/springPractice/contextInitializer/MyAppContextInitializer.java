package com.automannn.springPractice.contextInitializer;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.StaticListableBeanFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.context.support.StaticWebApplicationContext;

/**
 * @author chenkh
 * @time 2021/9/1 9:48
 */
public class MyAppContextInitializer implements ApplicationContextInitializer {
    public void initialize(ConfigurableApplicationContext applicationContext) {
        applicationContext.setEnvironment(new StandardEnvironment());
        applicationContext.addApplicationListener(new ApplicationListener<ApplicationEvent>() {
            public void onApplicationEvent(ApplicationEvent event) {
                System.out.println("上下文监听器初始化器，可以新增监听器");
            }
        });

        applicationContext.addBeanFactoryPostProcessor(new BeanFactoryPostProcessor() {
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
                System.out.println("上下文初始化器，可以新增容器后置处理器");
            }
        });

        applicationContext.addProtocolResolver(new ProtocolResolver() {
            public Resource resolve(String location, ResourceLoader resourceLoader) {
                System.out.println("上下文初始化器，可以新增协议解析器，加载资源句柄");
                return resourceLoader.getResource(location);
            }
        });
    }

    public static void main(String[] args) {
        //xxx:上下文初始化器，并未直接存在于原始容器中,只在 web应用应用环境有所应用，且是通过事件机制触发
        StaticWebApplicationContext context = new StaticWebApplicationContext();
    }
}
