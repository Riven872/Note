package com.edu.rabbitmq.two;

import com.edu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.edu.rabbitmq.utils.RabbitMqUtils.QUEUE_NAME;

/**
 * 工作线程（消费者1）
 */
public class Worker01 {
    //接收消息
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();

        System.out.println("C3等待接收消息");

        channel.basicConsume(
                QUEUE_NAME,
                true,
                (consumerTag, msg) -> System.out.println("接收到的消息为：" + new String(msg.getBody())),
                (consumerTag) -> System.out.println(consumerTag + " 消费者取消消息回调"));
    }
}
