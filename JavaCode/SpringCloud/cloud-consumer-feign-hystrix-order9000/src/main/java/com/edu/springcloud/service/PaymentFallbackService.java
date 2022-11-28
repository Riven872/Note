package com.edu.springcloud.service;

import org.springframework.stereotype.Component;

@SuppressWarnings("SpringMVCViewInspection")
@Component
public class PaymentFallbackService implements PaymentHystrixService{
    @Override
    public String paymentInfo_OK(Integer id) {
        return "PaymentFallbackService fallback - paymentInfo_OK";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "PaymentFallbackService fallback - paymentInfo_TimeOut";
    }
}
