package com.edu.AOP2;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//增强的类
@Component
@Aspect //生成代理对象
@Order(1)
public class UserProxy {
    //抽取相同的切入点，并注解在一个方法上
    @Pointcut(value = "execution(* com.edu.AOP2.User.add(..))")
    public void pointDemo() {

    }

    //前置通知
    //@Before表示前置通知，表示增强的是哪个包下的哪个类下的哪个方法
    @Before(value = "pointDemo()")
    public void before() {
        System.out.println("Before...");
    }
}
