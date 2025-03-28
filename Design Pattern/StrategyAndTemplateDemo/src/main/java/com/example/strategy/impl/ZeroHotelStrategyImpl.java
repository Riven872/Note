package com.example.strategy.impl;

import com.example.annotation.WelfareCode;
import com.example.enums.WelfareCodeEnum;
import com.example.strategy.AbstractWelfareStrategyBase;
import com.example.strategy.WelfareStrategy;
import org.springframework.stereotype.Service;

/**
 * @author riven
 * @date 2025/3/28 0028 19:18
 * @description 0 元酒店业务类
 */
@WelfareCode(welfareCode = WelfareCodeEnum.ZERO_HOTEL)
@Service
public class ZeroHotelStrategyImpl extends AbstractWelfareStrategyBase implements WelfareStrategy {
    /**
     * 异步插入一条执行记录
     *
     * @param welfareCode 权益标识
     * @param account     用户标识
     */
    @Override
    public void asyncInsertHistory(String welfareCode, String account) {

    }

    /**
     * 发送权益资源（核心业务流程）
     *
     * @param welfareCode 权益标识
     * @param type        资源类型
     */
    @Override
    public void sendResource(String welfareCode, Integer type) {
        actionBeforeSend("phone", "account");
    }

    /**
     * 发送资源前置行为
     *
     * @param phone   用户手机号
     * @param account 用户账号
     */
    @Override
    public void actionBeforeSend(String phone, String account) {
        // ...
    }
}
