package com.edu.rabbitmq.three;

import com.edu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import static com.edu.rabbitmq.utils.RabbitMqUtils.TASK_QUEUE_NAME;

public class Task02 {


    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
        //从控制台接收消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String msg = scanner.next();
            channel.basicPublish("", TASK_QUEUE_NAME, null, msg.getBytes());
            System.out.println("消息发送完成：" + msg);
        }
    }
}
