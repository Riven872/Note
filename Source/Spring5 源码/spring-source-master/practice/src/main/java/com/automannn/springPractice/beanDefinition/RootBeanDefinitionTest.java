package com.automannn.springPractice.beanDefinition;

import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * @author chenkh
 * @time 2021/8/21 11:31
 */
public class RootBeanDefinitionTest {
    public static void main(String[] args) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition(AAAB.class);
        System.out.println(beanDefinition.getResolvableType());

    }

    static class AAAB{}
}
