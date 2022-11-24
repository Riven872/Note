package com.edu.springcloud;

import com.edu.myrule.MySelfRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@Slf4j
@EnableEurekaClient
//@RibbonClient(name = "CLOUD-PAYMENT-SERVICE",configuration= MySelfRule.class)
public class OrderMain9000 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain9000.class, args);
        log.info("OrderMain9000启动成功");
    }
}
