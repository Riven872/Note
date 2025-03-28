package com.example.strategy;

/**
 * @author riven
 * @date 2025/3/28 0028 18:47
 * @description 规范策略的扩展行为
 */
public interface WelfareStrategy {
    /**
     * 发送权益资源（核心业务流程）
     *
     * @param welfareCode 权益标识
     * @param type        资源类型
     */
    void sendResource(String welfareCode, Integer type);

    /**
     * 发送资源前置行为
     *
     * @param phone   用户手机号
     * @param account 用户账号
     */
    default void actionBeforeSend(String phone, String account) {

    }

    /**
     * 权益注销
     *
     * @return 【0 - 注销成功 | 1 - 注销中 | 2 - 注销失败】
     */
    default int invalid() {
        return 0;
    }
}
