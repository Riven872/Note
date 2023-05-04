package com.automannn.springPractice.aop;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultIntroductionAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.aop.testfixture.advice.TimestampIntroductionAdvisor;

import java.lang.reflect.Method;

/**
 * @author chenkh
 * @time 2021/9/8 16:27
 */
public class MyCglibAopSimpleUseTest {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory(new MyCglibAopBusinessBean());

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
        /*xxx: 默认情况下，jdkProxy。 只有在制定了代理实例类的情况下，才会进行再次判断，是否是使用 cglib,还是 jdkProxy */
        proxyFactory.setProxyTargetClass(true);

        Object proxy= proxyFactory.getProxy();
        if (proxy instanceof MyCglibAopBusinessBean){
            MyCglibAopBusinessBean businessBean = (MyCglibAopBusinessBean) proxy;
            businessBean.sayHello();
        }
    }

    static class MyCglibAopBusinessBean{
        public void sayHello(){
            System.out.println("hello,world!");
        }
    }
}
