package com.automannn.springPractice.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author chenkh
 * @time 2021/9/7 17:03
 */
public class MyAopTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AopConfiguration.class);
        BusinessBean businessBean= applicationContext.getBean(BusinessBean.class);
        businessBean.doBusiness();
    }

    @EnableAspectJAutoProxy
    @Configuration
    static class AopConfiguration{
        @Bean
        public AopAspect aopAspect(){
            return new AopAspect();
        }

        @Bean
        public BusinessBean businessBean(){
            return new BusinessBean();
        }
    }

    @Aspect
    static class AopAspect{

        @Pointcut("execution(public * com.automannn.springPractice.aop.BusinessBean.*(..))")
        private void pointCut(){};

        @Around("pointCut()")
//        @Around("execution(public * com.automannn.springPractice.aop.BusinessBean.*(..))")
        private void around(ProceedingJoinPoint joinpoint){
            System.out.println("business around method!");
            try {
                joinpoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

    }
}
