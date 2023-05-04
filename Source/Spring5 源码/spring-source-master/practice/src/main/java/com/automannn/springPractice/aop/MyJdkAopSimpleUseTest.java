package com.automannn.springPractice.aop;

import org.springframework.aop.Advisor;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultIntroductionAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.aop.testfixture.advice.TimestampIntroductionAdvisor;

import java.lang.reflect.Method;

/**
 * @author chenkh
 * @time 2021/9/7 18:50
 */
public class MyJdkAopSimpleUseTest {
    public static void main(String[] args) {
        //aop 主要设计到的类及接口:
        // ProxyFactory
        // AopProxyFactory -> DefaultAopProxyFactory
        // AopProxy  -> ObjenesisCglibAopProxy, 以及 JdkDynamicAopProxy

        ProxyFactory proxyFactory = new ProxyFactory(new BusinessBean());

        NameMatchMethodPointcutAdvisor nameMatchMethodPointcutAdvisor = new NameMatchMethodPointcutAdvisor();
        nameMatchMethodPointcutAdvisor.addMethodName("sayHello");
        nameMatchMethodPointcutAdvisor.setAdvice(new MethodBeforeAdvice() {
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println("方法执行前，打印!");
                System.out.println("方法名称:"+method.getName());
                System.out.println("被代理的对象:"+target);
            }
        });
        proxyFactory.addAdvisor(nameMatchMethodPointcutAdvisor);

        TimestampIntroductionAdvisor timestampIntroductionAdvisor = new TimestampIntroductionAdvisor();
        proxyFactory.addAdvisor(timestampIntroductionAdvisor);

        DefaultIntroductionAdvisor defaultIntroductionAdvisor = new DefaultIntroductionAdvisor(new AfterReturningAdvice(){

            public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
                System.out.println("方法执行后，打印!");
                System.out.println("方法名称:"+method.getName());
                System.out.println("被代理的对象:"+target);
            }
        });

        proxyFactory.addAdvisor(defaultIntroductionAdvisor);


       Object proxy= proxyFactory.getProxy();
       if (proxy instanceof ISayHello){
           ISayHello iSayHello = (ISayHello) proxy;
           iSayHello.sayHello();
       }

    }

    static class MyAopBusinessBean implements ISayHello{
        public void sayHello(){
            System.out.println("hello,world! ");
        }
    }

    static interface ISayHello{
        void sayHello();
    }
}
