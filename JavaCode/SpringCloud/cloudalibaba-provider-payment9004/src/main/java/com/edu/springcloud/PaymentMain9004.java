package com.edu.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class PaymentMain9004 {

    public static void main(String[] args) {
        SpringApplication.run(PaymentMain9004.class, args);
        log.info("PaymentMain9004启动成功");
    }

}
