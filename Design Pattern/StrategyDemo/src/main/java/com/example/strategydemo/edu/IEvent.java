package com.example.strategydemo.edu;

/**
 * 定义策略中的方法
 */
public interface IEvent {
    /**
     * 不同的实现类有自己的处理方式
     */
    void eventHandler(String json);

    /**
     * 不同的实现类有自己的类型
     * @return
     */
    String getEventType();
}
