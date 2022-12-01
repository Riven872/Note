package com.edu.rabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 发送延迟消息
 */
@RestController
@RequestMapping("/ttl")
@Slf4j
public class SendMsgController {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{msg}")
    public void sendMsg(@PathVariable("msg") String msg){
        log.info("当前时间：{}，发送一条消息给两个TTL队列：{}", new Date().toString(), msg);

        rabbitTemplate.convertAndSend("X", "XA", "消息来自ttl为10s的队列" + msg);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自ttl为40s的队列" + msg);
    }
}
