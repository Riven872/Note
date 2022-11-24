package com.edu.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@Slf4j
@EnableFeignClients
public class OrderFeignMain9000 {

    public static void main(String[] args) {
        SpringApplication.run(OrderFeignMain9000.class, args);
        log.info("OrderFeignMain9000启动成功");
    }

}
