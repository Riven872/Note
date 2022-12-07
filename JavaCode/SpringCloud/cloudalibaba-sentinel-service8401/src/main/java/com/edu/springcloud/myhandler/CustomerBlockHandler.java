package com.edu.springcloud.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.edu.springcloud.entities.CommonResult;

public class CustomerBlockHandler {
    public static CommonResult handlerException(BlockException exception) {
        return new CommonResult(4444, "客户自定义fallback-----1");
    }

    public static CommonResult handlerException2(BlockException exception) {
        return new CommonResult(4444, "客户自定义fallback-----2");
    }
}
