package com.example.aop;

import com.example.annotation.AuthCheck;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author riven
 * @date 2025/3/28 0028 20:15
 * @description 自定义权限校验 AOP
 */
@Component
@Aspect
@Slf4j
public class AuthInterceptor {
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        boolean check = authCheck.check();

        // 不需要校验
        if (!check) {
            log.info("不需要校验");
            return joinPoint.proceed();
        }

        // 校验各种逻辑...
        log.info("校验完成");
        return joinPoint.proceed();
    }
}
