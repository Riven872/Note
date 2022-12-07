package com.edu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.edu.springcloud.entities.CommonResult;
import com.edu.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("/consumer")
public class CircleBreakerController {
    public static final String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/fallback/{id}")
    //@SentinelResource(value = "fallback")
    @SentinelResource(value = "fallback",fallback = "handlerFallback") //fallback负责业务异常
    public CommonResult<Payment> fallback(@PathVariable Long id){
        CommonResult<Payment> result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, CommonResult.class, id);

        if (id == 4) {
            throw new IllegalArgumentException("IllegalArgumentException，非法参数异常");
        } else if(result.getData() == null) {
            throw new NullPointerException("NullPointerException，该id没有对应记录，空指针异常");
        }

        return result;
    }

    public CommonResult handlerFallback(@PathVariable  Long id,Throwable e) {
        Payment payment = new Payment(id,"null");
        return new CommonResult<>(444,"兜底异常handlerFallback,exception内容  "+e.getMessage(),payment);
    }
}
