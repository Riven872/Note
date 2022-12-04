package com.edu.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RefreshScope
@RestController
@RequestMapping("/config/info")
public class ConfigClientController {
    //读取Nacos配置中心内，指定配置文件的config.info配置信息
    @Value("${config.info}")
    private String configInfo;

    @GetMapping
    public String getConfigInfo() {
        return configInfo;
    }
}
