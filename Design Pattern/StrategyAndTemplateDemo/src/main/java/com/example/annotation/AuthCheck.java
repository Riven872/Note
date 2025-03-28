package com.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author riven
 * @date 2025/3/28 0028 20:07
 * @description 权限校验
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {
    /**
     * 是否需要权限校验
     * @return 【true - 校验 | false - 不校验】
     */
    boolean check() default true;
}
