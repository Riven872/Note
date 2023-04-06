package com.automannn.springPractice.componentScan;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author chenkh
 * @time 2021/9/26 15:14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface AutomannBean {
}
