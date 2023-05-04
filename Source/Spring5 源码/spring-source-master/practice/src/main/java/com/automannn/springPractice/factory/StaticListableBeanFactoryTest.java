package com.automannn.springPractice.factory;

import org.springframework.beans.factory.support.StaticListableBeanFactory;

/**
 * @author chenkh
 * @time 2021/8/16 11:11
 */
public class StaticListableBeanFactoryTest {
    public static void main(String[] args) {
        StaticListableBeanFactory factory = new StaticListableBeanFactory();
        factory.addBean("aa",new AA());
        System.out.println(factory.getBean("aa"));
        System.out.println(factory.getBean("aa1"));
    }

    static class AA{}
}
