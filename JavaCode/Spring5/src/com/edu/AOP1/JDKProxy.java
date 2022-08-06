package com.edu.AOP1;


import java.lang.reflect.Proxy;

//JDK动态代理
public class JDKProxy {
    public static void main(String[] args) {
        //接口的集合
        Class[] interfaces = {IUserDao.class};
        //UserDaoProxy默认是有参的构造函数，因此实例化一个实现类，传递动态代理的对象（代理谁就传谁）
        UserDaoImpl userDao = new UserDaoImpl();
        //返回代理对象
        IUserDao dao = (IUserDao) Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new UserDaoProxy(userDao));
        //调用方法
        System.out.println(dao.add(1, 2));
    }
}
