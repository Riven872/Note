package com.edu.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * TTL队列，配置文件类
 */
@Configuration
public class TtlQueueConfig {
    //普通交换机
    public static final String X_EXCHANGE = "X";
    //死信交换机
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    //普通队列
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    //死信队列
    public static final String DEAD_LETTER_QUEUE = "QD";

    //声明X交换机
    @Bean
    public DirectExchange xExchange() {
        return new DirectExchange(X_EXCHANGE);
    }

    //声明Y交换机
    @Bean
    public DirectExchange yExchange() {
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    //声明QA队列，TTL为10s
    @Bean
    public Queue queueA() {
        //设置队列参数
        Map<String, Object> args = new HashMap<>();
        //设置死信交换机
        args.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        //设置死信routingKey
        args.put("x-dead-letter-routing-key", "YD");
        //设置TTL
        args.put("x-message-ttl", 10 * 1000);
        return QueueBuilder.durable(QUEUE_A).withArguments(args).build();
    }

    //声明QB队列，TTL为40s
    @Bean
    public Queue queueB() {
        //设置队列参数
        Map<String, Object> args = new HashMap<>();
        //设置死信交换机
        args.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        //设置死信routingKey
        args.put("x-dead-letter-routing-key", "YD");
        //设置TTL
        args.put("x-message-ttl", 10 * 1000);
        return QueueBuilder.durable(QUEUE_B).withArguments(args).build();
    }

    //声明死信队列
    @Bean
    public Queue queueD() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    //X交换机与QA绑定
    @Bean
    public Binding queueABindingX(@Qualifier("queueA") Queue queueA,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }

    //X交换机与QB绑定
    @Bean
    public Binding queueBBindingX(@Qualifier("queueA") Queue queueB,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueB).to(xExchange).with("XA");
    }

    //Y交换机与QD绑定
    @Bean
    public Binding queueDBindingY(@Qualifier("queueA") Queue queueD,
                                  @Qualifier("yExchange") DirectExchange yExchange){
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }
}
