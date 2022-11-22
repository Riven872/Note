package com.edu.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class OrderMain9000 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain9000.class, args);
        log.info("OrderMain9000启动成功");
    }
}
