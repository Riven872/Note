package com.edu.AOP2;

import org.springframework.stereotype.Component;

//被增强的类
@Component
public class User {
    public void add() {
        System.out.println("User类中的add方法");
    }
}
