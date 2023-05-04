package com.automannn.springPractice.beanDefinition;

import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.classreading.MetadataReader;

/**
 * @author chenkh
 * @time 2021/8/21 11:48
 */
public class AnnotatedGenericBeanDefinitionTest {
    public static void main(String[] args) {
        /*xxx: 读取器由asm访问者模式提供，无法进行扩展或者重写。  同时也是官方不建议重写和扩展的*/
        /*xxx: 指定metaDataReader的时候，同时要给定一个 资源句柄*/
//        MetadataReader metadataReader=null;
//        ScannedGenericBeanDefinition definition = new ScannedGenericBeanDefinition(null);

        AnnotatedGenericBeanDefinition annotatedGenericBeanDefinition = new AnnotatedGenericBeanDefinition(MyBean.class);

        System.out.println(annotatedGenericBeanDefinition.getMetadata());

    }
    static class MyBean{}
}
