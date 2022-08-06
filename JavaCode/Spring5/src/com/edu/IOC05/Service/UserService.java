package com.edu.IOC05.Service;

import com.edu.IOC05.DAO.UserDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDaoImpl userDao;

    @Value(value = "foobar")
    private String name;

    public void add(){
        System.out.println("UserService类的add方法");
        System.out.println(this.name);
        userDao.update();
    }
}
