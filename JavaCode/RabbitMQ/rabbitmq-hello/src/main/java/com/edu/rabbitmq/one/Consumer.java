package com.edu.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 消费者，接收消息
 */
public class Consumer {
    public static final String QUEUE_NAME = "hello";//队列名称

    public static void main(String[] args) throws Exception {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //工厂IP连接RabbitMQ队列
        factory.setHost("192.168.63.100");
        factory.setUsername("root");
        factory.setPassword("123");
        //创建连接
        Connection connection = factory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();
        /**
         * 消费者接收消息
         * 1、消费哪个队列
         * 2、消费成功之后是否要自动应答
         * 3、消费者未成功消费的回调
         * 4、消费者取消消费的回调
         */
        channel.basicConsume(
                QUEUE_NAME,
                true,
                (consumerTag, msg) -> System.out.println(new String(msg.getBody())),
                (consumerTag) -> System.out.println("消费消息被中断"));

    }
}
