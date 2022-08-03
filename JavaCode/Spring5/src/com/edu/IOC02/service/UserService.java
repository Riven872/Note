package com.edu.IOC02.service;

import com.edu.IOC02.dao.UserDaoImpl;

public class UserService {
    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    private UserDaoImpl userDao;

    public void add() {
        System.out.println("调用UserService类的add方法");

        //原始方式
        //IUserDao userDao = new UserDaoImpl();
        //userDao.update();

        userDao.update();
    }
}
