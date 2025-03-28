package com.example.strategy;

/**
 * @author riven
 * @date 2025/3/28 0028 15:48
 * @description 定义策略的基础行为
 */
public abstract class AbstractWelfareStrategyBase {
    /**
     * 异步插入一条执行记录
     * @param welfareCode 权益标识
     * @param account 用户标识
     */
    public abstract void asyncInsertHistory(String welfareCode, String account);

    /**
     * 基础行为
     * @param welfareCode 权益标识
     */
    public void baseAction(String welfareCode) {

    }
}
