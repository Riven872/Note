package com.example.controller;

import com.example.annotation.AuthCheck;
import com.example.enums.WelfareCodeEnum;
import com.example.strategy.WelfareStrategy;
import com.example.strategy.WelfareStrategyContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@Slf4j
@RequestMapping("/demo")
public class DemoController {
    @Resource
    private WelfareStrategyContext welfareStrategyContext;


    public void foo() {
        // 根据权益标识枚举获取对应的策略
        WelfareStrategy welfareStrategy = welfareStrategyContext.getWelfareStrategy(WelfareCodeEnum.ZERO_HOTEL);

        // 执行策略中的方法
        welfareStrategy.sendResource("foo", 1);
    }

    @AuthCheck()
    public void bar() {
        log.info("bar");
    }
}
