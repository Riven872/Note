package com.edu.IOC02.dao;

public class UserDaoImpl implements IUserDao{
    @Override
    public void update() {
        System.out.println("调用UserDao类中的update方法");
    }
}
