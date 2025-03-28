package com.example.strategydemo.edu.impl;

import com.example.strategydemo.edu.IEvent;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

@Component
public class FooEventService implements IEvent {
    /**
     * foo 类自己的处理逻辑
     */
    @Override
    public void eventHandler(String json) {
        System.out.println("处理 Foo 的逻辑");
    }

    /**
     * 标识类
     *
     * @return
     */
    @Override
    public String getEventType() {
        return "我是 foo 类型";
    }
}
