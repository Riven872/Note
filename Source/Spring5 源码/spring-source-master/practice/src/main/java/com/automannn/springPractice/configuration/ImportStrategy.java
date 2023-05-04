package com.automannn.springPractice.configuration;

import java.lang.annotation.*;

/**
 * @author chenkh
 * @time 2021/9/27 15:30
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ImportStrategy {
    boolean highPerformance();
}
