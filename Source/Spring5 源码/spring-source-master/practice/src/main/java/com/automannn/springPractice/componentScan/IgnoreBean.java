package com.automannn.springPractice.componentScan;

import java.lang.annotation.*;

/**
 * @author chenkh
 * @time 2021/9/26 15:41
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreBean {
}
