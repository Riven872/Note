package com.edu.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@Slf4j
@EnableEurekaClient
public class PaymentHystrixMain9001 {

    public static void main(String[] args) {
        SpringApplication.run(PaymentHystrixMain9001.class, args);
        log.info("PaymentHystrixMain9001启动成功");
    }

}
