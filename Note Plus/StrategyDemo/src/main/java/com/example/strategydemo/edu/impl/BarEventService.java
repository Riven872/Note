package com.example.strategydemo.edu.impl;

import com.example.strategydemo.edu.IEvent;

public class BarEventService implements IEvent {
    @Override
    public void eventHandler(String json) {
        System.out.println("处理 bar 的逻辑");
    }

    @Override
    public String getEventType() {
        return "我是 bar 类型";
    }
}
