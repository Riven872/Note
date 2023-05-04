package com.automannn.springPractice.environment;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

/**
 * @author chenkh
 * @time 2021/8/12 11:56
 */
public class StandardEnvironmentTest {
    public static void main(String[] args) {
        ConfigurableEnvironment environment= new StandardEnvironment();
        System.out.println("=========active profiles=========");
        System.out.println(environment.getActiveProfiles());

        System.out.println("=========default profiles=========");
        System.out.println();
        System.out.println(environment.getDefaultProfiles());

        System.out.println("=========system environment============");
        System.out.println(environment.getSystemEnvironment());
        System.out.println();
        System.out.println();

        System.out.println("=========system properties============");
        System.out.println(environment.getSystemProperties());
    }
}
