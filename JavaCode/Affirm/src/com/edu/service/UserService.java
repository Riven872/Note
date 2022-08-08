package com.edu.service;

import com.edu.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, timeout = -1)
public class UserService {
    @Autowired
    private UserDao userDao;

    public void exchange(){
        userDao.reduceMoney();
        userDao.addMoney();
    }
}
