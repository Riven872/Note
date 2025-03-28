package com.example.strategydemo.edu;

import javax.annotation.Resource;

public class EventContext {
    /**
     * 持有接口
     */
    private final IEvent event;

    /**
     * 接收该接口的实现类
     *
     * @param event
     */
    public EventContext(IEvent event) {
        this.event = event;
    }

    /**
     * 执行特定实现类的处理方式
     * @param json
     */
    public void executeStrategy(String json){
        event.eventHandler(json);
    }
}
