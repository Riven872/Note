package com.edu.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者，发送消息
 */
public class Producer {
    public static final String QUEUE_NAME = "hello";//队列名称

    public static void main(String[] args) throws IOException, TimeoutException {
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
         * 生成一个队列
         * 1、队列名称
         * 2、队列中的消息是否持久化（存放在磁盘内），否则存放在内存中
         * 3、该队列是否进行消息共享，false：多个消费者消费 true：只能一个消费者消费
         * 4、最后一个消费者断开连接之后，该队列是否自动删除
         * 5、其他参数
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //定义要发送的消息
        String msg = "hello world";
        /**
         * 使用信道发送消息
         * 1、发送到哪个交换机
         * 2、路由的key值，本次是队列名称
         * 3、其他参数
         * 4、发送消息的消息体
         */
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        System.out.println("消息发送完毕");
    }
}
