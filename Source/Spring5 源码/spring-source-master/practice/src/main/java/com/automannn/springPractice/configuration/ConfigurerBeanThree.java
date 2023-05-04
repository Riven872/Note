package com.automannn.springPractice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

/**
 * @author chenkh
 * @time 2021/9/27 10:13
 */
@Component
@ComponentScan(basePackages = "com.automannn.springPractice.componentScan") //第三优先级
public class ConfigurerBeanThree {

    @Autowired
    private ConfigurerBeanOne configurerBeanOne;

    @Value("${name}")
    private String name;
}
