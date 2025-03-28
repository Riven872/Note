package com.example.demo;

import com.example.controller.DemoController;
import com.example.strategy.WelfareStrategyContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class DemoTest {
    @Resource
    private WelfareStrategyContext welfareStrategyContext;

    @Resource
    private DemoController demoController;

    @Test
    void test() {
        // WelfareStrategy welfareStrategy = welfareStrategyContext.getWelfareStrategy(WelfareCodeEnum.ZERO_HOTEL);
        //
        // welfareStrategy.sendResource(null, null);

        demoController.bar();
    }
}
