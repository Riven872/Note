package com.automannn.springPractice.aop;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author chenkh
 * @time 2021/9/8 16:17
 */
/*xxx: cglib动态代理的使用案例*/
public class CglibTest {
    public static void main(String[] args) {
        MyCglibProxy myCglibProxy = new MyCglibProxy();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(MyBusiness.class);
        enhancer.setCallback(myCglibProxy);

       MyBusiness myBusiness = (MyBusiness) enhancer.create();
       myBusiness.sayHello();
    }

    static class MyBusiness{
        public void sayHello(){
            System.out.println("hello,world! ");
        }
    }

    static class MyCglibProxy implements MethodInterceptor {

        /*xxx: object: 要进行增强的对象，method: 要拦截的方法, objects: 参数列表, proxy: 对方法的代理*/
        public Object intercept(Object object, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
            System.out.println("方法执行前打印.");
            /*xxx: invokeSuper 表示对被代理对象的调用*/
            proxy.invokeSuper(object,objects);
            System.out.println("方法执行后打印.");
            return object;
        }
    }
}
