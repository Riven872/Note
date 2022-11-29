package com.edu.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@Slf4j
@EnableConfigServer
public class CloudConfigCenter3344 {

    public static void main(String[] args) {
        SpringApplication.run(CloudConfigCenter3344.class, args);
        log.info("CloudConfigCenter3344启动成功");
    }

}
