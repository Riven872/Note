package com.automannn.springPractice.beanDefinitionReader;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.*;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.Arrays;

/**
 * @author chenkh
 * @time 2021/9/28 9:13
 */
public class BeanDefinitionReaderTest {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        /*xxx: 默认的resourceLoader实现为： PathMatchingResourcePatternResolver */
        System.out.println(reader.getResourceLoader());
        reader.loadBeanDefinitions("classpath:xmlApplication.xml");
        System.out.println(Arrays.asList(beanFactory.getBeanDefinitionNames()));


    }
}
