package com.edu.AOP2;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration //作为配置类，替代XML配置文件
@ComponentScan(basePackages = {"com.edu.AOP2"}) //相当于配置文件中开启的组件扫描
@EnableAspectJAutoProxy(proxyTargetClass = true) //开始Aspect生成代理对象
public class config {
}
