package com.edu.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@Slf4j
public class OrderHystrixMain9000 {

    public static void main(String[] args) {
        SpringApplication.run(OrderHystrixMain9000.class, args);
        log.info("OrderHystrixMain9000启动成功");
    }

}
