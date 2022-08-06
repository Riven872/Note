package com.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration  //作为配置类，替代XML配置文件
@ComponentScan(basePackages = {"com.edu.IOC05"})  //相当于配置文件中开启的组件扫描
public class SpringConfig {
}
