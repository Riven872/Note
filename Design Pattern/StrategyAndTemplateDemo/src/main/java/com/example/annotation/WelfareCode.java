package com.example.annotation;

import com.example.enums.WelfareCodeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author riven
 * @date 2025/3/28 0028 15:29
 * @description 根据不同的权益标识代表不同的策略
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WelfareCode {
    WelfareCodeEnum welfareCode();
}
