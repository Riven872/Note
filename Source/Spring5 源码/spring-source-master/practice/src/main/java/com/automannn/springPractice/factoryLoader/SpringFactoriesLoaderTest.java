package com.automannn.springPractice.factoryLoader;

import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

/**
 * @author chenkh
 * @time 2021/9/30 11:03
 */
public class SpringFactoriesLoaderTest {

    public static void main(String[] args) {
        /*xxx: 根据某个类型，查询实现类*/
        List automannnExtensionAbilityList= SpringFactoriesLoader.loadFactoryNames(AutomannnExtensionAbility.class,SpringFactoriesLoaderTest.class.getClassLoader());
        System.out.println(automannnExtensionAbilityList);
    }
}
