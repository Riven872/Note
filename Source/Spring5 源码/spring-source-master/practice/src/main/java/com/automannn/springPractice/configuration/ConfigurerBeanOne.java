package com.automannn.springPractice.configuration;

import com.automannn.springPractice.componentScan.MyCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

/**
 * @author chenkh
 * @time 2021/9/27 10:13
 */
@Component
@PropertySources(value = {@PropertySource(value = "classpath:myTest.yml")})  //第二优先级
//@Conditional(value = MyCondition.class)
public class ConfigurerBeanOne implements MemberClassTest{
}
