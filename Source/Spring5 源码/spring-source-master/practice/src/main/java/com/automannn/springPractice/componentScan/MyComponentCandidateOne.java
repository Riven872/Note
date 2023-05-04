package com.automannn.springPractice.componentScan;

import org.springframework.context.annotation.ImportResource;

/**
 * @author chenkh
 * @time 2021/9/26 10:03
 */
@IgnoreBean
@AutomannBean
@ImportResource(locations = {"classpath:xmlApplication.xml"}) //第五优先级
public class MyComponentCandidateOne {
}
