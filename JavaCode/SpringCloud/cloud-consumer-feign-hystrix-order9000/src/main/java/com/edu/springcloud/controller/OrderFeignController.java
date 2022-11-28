package com.edu.springcloud.controller;

import com.edu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/consumer/payment/hystrix")
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderFeignController {
    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        return paymentHystrixService.paymentInfo_OK(id);
    }

    @GetMapping("/timeout/{id}")
    //@HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",
    //        commandProperties = {@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "1500")})
    @HystrixCommand
    String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        return paymentHystrixService.paymentInfo_TimeOut(id);
    }

    private String paymentInfo_TimeOutHandler(){
        return "消费者9000，对方支付系统繁忙，请10s后再重试或对方运行出错，请检查";
    }

    private String payment_Global_FallbackMethod(){
        return "Global异常处理信息，请稍后再试";
    }
}
