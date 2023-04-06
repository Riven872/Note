package com.automannn.springPractice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author chenkh
 * @time 2021/9/27 15:36
 */
public class StrategyBean {
    @Component
    public static class HighConfiger{
        @Bean
        public Object myBean1(){
            return new Object();
        }
    }

    @Component
    public static class LowConfiger{

        @Bean
        public Object myBean2(){
            return new Object();
        }
    }
}
