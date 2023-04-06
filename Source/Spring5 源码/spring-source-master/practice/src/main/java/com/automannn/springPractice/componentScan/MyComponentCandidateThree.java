package com.automannn.springPractice.componentScan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

/**
 * @author chenkh
 * @time 2021/9/26 10:03
 */
@AutomannBean
@Conditional(value = {MyCondition.class})
public class MyComponentCandidateThree {

    @Bean //第六优先级
    public Object myBeanTest(){
        return new Object();
    }
}
