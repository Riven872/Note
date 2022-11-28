package com.edu.springcloud.controller;

import com.edu.springcloud.entities.CommonResult;
import com.edu.springcloud.entities.Payment;
import com.edu.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 支付模块控制器
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        if (result > 0) {
            return new CommonResult(200, "插入数据成功，serverPort：" + serverPort, result);
        } else {
            return new CommonResult<>(444, "数据插入失败", null);
        }
    }

    @GetMapping("/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        if (payment != null) {
            return new CommonResult(200, "查询成功，serverPort：" + serverPort, payment);
        } else {
            return new CommonResult(444, "没有对应的数据，查询id为：" + id, null);
        }
    }

    @GetMapping("/lb")
    public String getPaymentLB(){
        return this.serverPort;
    }
}
