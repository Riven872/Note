package com.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author riven
 * @date 2025/3/28 0028 15:32
 * @description 权益标识枚举类
 */
@Getter
@AllArgsConstructor
public enum WelfareCodeEnum {
    /**
     * 贵宾厅
     */
    VIP_ROOM("MSMI221124c8edc15d2cee41baa4363b2bc0bb913b"),

    /**
     * 里程商城
     */
    MILEAGE_SHOP("MSBC241113fefff441258d4db0b795c59ed46e3f5d"),

    /**
     * 0 元酒店
     */
    ZERO_HOTEL("MSMI241211cf46d1dd34a94b869365b4ad797549f4");

    private final String code;
}
