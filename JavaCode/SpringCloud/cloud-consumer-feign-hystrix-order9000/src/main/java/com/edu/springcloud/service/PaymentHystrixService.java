package com.edu.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@FeignClient("CLOUD-PROVIDER-HYSTRIX-PAYMENT")
@RequestMapping("/payment/hystrix")
public interface PaymentHystrixService {
    @GetMapping("/ok/{id}")
    String paymentInfo_OK(@PathVariable("id") Integer id);

    @GetMapping("/timeout/{id}")
    String paymentInfo_TimeOut(@PathVariable("id") Integer id);
}
