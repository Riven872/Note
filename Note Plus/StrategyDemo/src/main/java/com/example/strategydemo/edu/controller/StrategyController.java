package com.example.strategydemo.edu.controller;

import com.example.strategydemo.edu.EventHandlerStrategy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class StrategyController {
    @Resource
    private EventHandlerStrategy eventHandlerStrategy;

    @PostMapping("/test")
    public void test(String json) {
        eventHandlerStrategy.EventHandle("我是 foo 类型", json);
    }
}
