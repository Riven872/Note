package com.edu.AOP1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

//创建代理对象代码
public class UserDaoProxy implements InvocationHandler {
    //创建的是谁的代理对象，就把谁传过来
    //使用有参构造器

    private Object obj;

    //为了通用性，因此传入Object类型
    public UserDaoProxy(Object obj){
        this.obj = obj;
    }

    /**
     * 增强的逻辑
     * @param proxy 代理对象
     * @param method 被增强的方法，
     *               可以根据method.getName()判断当前调用的是哪个方法，进而判断是否增强此方法
     * @param args 参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //被增强的方法之前
        System.out.println("被增强的方法之前执行" + method.getName() + " 传递的参数" + Arrays.toString(args));
        //被增强的方法
        Object res = method.invoke(obj, args);
        //被增强的方法之后
        System.out.println("被增强的方法之后执行" + obj);

        return res;
    }
}
