package com.edu.provider.impl;

import com.edu.provider.api.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String userName) {
        return "hello world " + userName;
    }
}
