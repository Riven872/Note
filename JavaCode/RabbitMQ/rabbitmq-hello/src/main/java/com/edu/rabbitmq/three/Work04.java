package com.edu.rabbitmq.three;

import com.edu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.edu.rabbitmq.utils.RabbitMqUtils.TASK_QUEUE_NAME;

public class Work04 {
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("C2等待接收消息，等待30s");

        //手动应答
        channel.basicConsume(
                TASK_QUEUE_NAME,
                false,
                (consumerTag, msg) -> {
                    try {
                        Thread.sleep(30*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("接收到的消息为：" + new String(msg.getBody()));
                    //1、消息的标记 2、是否批量应答
                    channel.basicAck(msg.getEnvelope().getDeliveryTag(), false);
                    },
                (consumerTag) -> System.out.println(consumerTag + " 消费者取消消息回调"));
    }
}
