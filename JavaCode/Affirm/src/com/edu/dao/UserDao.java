package com.edu.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    //加钱
    public void addMoney();
    //扣钱
    public void reduceMoney();
}
