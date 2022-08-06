package com.edu.IOC05.DAO;

import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements IUserDao {
    @Override
    public void update() {
        System.out.println("调用UserDao类中的update方法");
    }
}
