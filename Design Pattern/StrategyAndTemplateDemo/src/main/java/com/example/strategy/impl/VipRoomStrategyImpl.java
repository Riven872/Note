package com.example.strategy.impl;

import com.example.annotation.WelfareCode;
import com.example.enums.WelfareCodeEnum;
import com.example.strategy.AbstractWelfareStrategyBase;
import com.example.strategy.WelfareStrategy;
import org.springframework.stereotype.Service;

/**
 * @author riven
 * @date 2025/3/28 0028 19:17
 * @description 贵宾厅业务类
 */
@WelfareCode(welfareCode = WelfareCodeEnum.VIP_ROOM)
@Service
public class VipRoomStrategyImpl extends AbstractWelfareStrategyBase implements WelfareStrategy {
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
        invalid();
    }

    /**
     * 权益注销
     *
     * @return 【0 - 注销成功 | 1 - 注销中 | 2 - 注销失败】
     */
    @Override
    public int invalid() {
        // ...
        return 1;
    }
}
