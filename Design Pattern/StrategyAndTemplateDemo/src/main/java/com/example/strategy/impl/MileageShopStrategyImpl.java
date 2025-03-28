package com.example.strategy.impl;

import com.example.annotation.WelfareCode;
import com.example.enums.WelfareCodeEnum;
import com.example.strategy.AbstractWelfareStrategyBase;
import com.example.strategy.WelfareStrategy;
import org.springframework.stereotype.Service;

/**
 * @author riven
 * @date 2025/3/28 0028 19:17
 * @description 里程商城业务类
 */
@Service
@WelfareCode(welfareCode = WelfareCodeEnum.MILEAGE_SHOP)
public class MileageShopStrategyImpl extends AbstractWelfareStrategyBase implements WelfareStrategy {
    /**
     * 异步插入一条执行记录
     *
     * @param welfareCode 权益标识
     * @param account     用户标识
     */
    @Override
    public void asyncInsertHistory(String welfareCode, String account) {
        // 调用里程的 mapper 插入记录
    }

    /**
     * 发送权益资源（核心业务流程）
     *
     * @param welfareCode 权益标识
     * @param type        资源类型
     */
    @Override
    public void sendResource(String welfareCode, Integer type) {
        // 里程自己的业务流程
    }
}
