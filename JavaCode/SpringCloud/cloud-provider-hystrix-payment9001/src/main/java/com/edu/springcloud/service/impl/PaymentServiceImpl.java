package com.edu.springcloud.service.impl;

import com.edu.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "线程池：" + Thread.currentThread() +
                " paymentInfo_OK，id：" + id;
    }


    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler")
    @Override
    public String paymentInfo_TimeOut(Integer id) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread() +
                " paymentInfo_OK，id：" + id +
                " 耗时: 5s";
    }

    private String paymentInfo_TimeOutHandler(Integer id) {
        return "线程池：" + Thread.currentThread() +
                " paymentInfo_TimeOutHandler，id：" + id + "服务器繁忙，请稍后再试";
    }
}
