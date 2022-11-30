##### 一、消息队列

###### 1、MQ相关概念

- 是一种存放消息的队列，还是一种跨进程的通信机制，是常见的上下游“逻辑解耦” +“物理解耦”的消息通信服务，使消息发送上游只需要依赖MQ，不用依赖其他服务
- 功能：流量削峰、应用解耦、异步处理

###### 2、安装MQ

- 安装Erlang：`rpm -ivh erlang-21.3-1.el7.x86_64.rpm`
- 安装依赖包：`yum install socat -y`
- 安装mq：`rpm -ivh rabbitmq-server-3.8.8-1.el7.noarch.rpm`

- 添加开机启动mq服务：`chkconfig rabbitmq-server on`
- 先关闭mq服务，再安装图形化界面：`rabbitmq-plugins enable rabbitmq_management`
    - 访问图形化界面：ip+15672
- 添加用户并设置权限
    - 创建账号：`rabbitmqctl add_user root 123`
    - 设置用户角色：`rabbitmqctl set_user_tags root administrator`
    - 设置用户权限：`rabbitmqctl set_permissions -p "/" root ".*" ".*" ".*"`
    - 查看当前用户和角色：`rabbitmqctl list_users`



##### 二、RabbitMQ

###### 1、RabbitMQ概念

- 是一个消息中间件，接受、存储和转发消息数据
- MQ由数个交换机和队列组成

###### 2、四大核心概念

- 生产者：产生数据发送消息的程序
- 交换机：接受来自生产者的消息并将消息推送到队列中，一个交换机可以对应多个队列
- 队列：RabbitMQ内的一种数据结构，本质是一个大的消息缓冲区，生产者可以将消息发送到一个队列，消费者可以从一个队列中接收数据
- 消费者：一个等待接收消息的程序
- 注：
    - 同一个程序可以既是生产者也是消费者
    - 一个队列对应一个消费者，多个消费者对应同一个队列时，首位消费者可以获取到消息，而后面的取不到

###### 3、RabbitMQ的六种模式

- 简单模式（Hello World）
- 工作模式（Work Queues）
- 发布订阅模式（Publish/Subscribe）
- 路由模式（Routing）
- 主题模式（Topics）
-  发布确认模式（Publisher Confirms）



##### 三、简单模式（hello world）

###### 1、生产者代码

```java
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
```

###### 2、消费者代码

```java
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
```



##### 四、工作队列模式（Work Queues）

- 工作队列(又称任务队列)的主要思想是避免立即执行资源密集型任务，而不得不等待它完成。 相反我们安排任务在之后执行。我们把任务封装为消息并将其发送到队列。在后台运行的工作进程将弹出任务并最终执行作业。当有多个工作线程时，这些工作线程将一起处理这些任务

###### 1、轮询分发消息

- 生产者发布大量消息到队列后，多个工作线程（消费者）轮询接收消息
- 多个消费者是竞争的关系
- 一个消息只能被处理一次，不可以处理多次

###### 2、消息应答

- 消费者在接收到信息并且**处理完该消息之后**，告诉Rabbitmq已经处理了，Rabbitmq就可以把该消息删除了
- 自动应答：仅在消费者可以高效并以某种速率能够处理这些消息的情况下使用（一般是接收到消息时就会ack，不去管是否处理完成）
- 手动应答：
    - 肯定确认：basicAck，rabbitmq已知该消息并且成功的处理消息，可以将其丢弃
    - 否定确认：不处理该信息了并直接拒绝，可以将其丢弃
        - basicNack
        - basicReject：比上者少一个批量处理的参数

###### 3、Multiple参数

- true代表批量应答channel上未应答的消息
    - 如channel上有传送的消息，5,6,7,8，且当前flag为8，那么此时5-8的这些还未应答的消息都会被确认收到消息应答
- false相反
    - 只会应答flag=8的消息，5,6，7三个消息依然不会被确认收到消息应答

###### 4、消息重新入队

- 如果消费者由于某些原因失去连接(其通道已关闭，连接已关闭或 TCP 连接丢失)，导致消息未发送 ACK 确认，RabbitMQ 将了解到消息未完全处理，并将对其重新排队。

##### 五、RabbitMQ持久化

- Rabbitmq在退出或崩溃时，会丢失队列和其中的消息，因此确保不会丢失，需要将队列和消息都标记为持久化

###### 1、队列实现持久化

- 已有的队列如果不是持久化，需要先删除队列才可以更改成持久化，否则会报错
- 在`queueDeclare`创建队列时，持久化参数设为true

###### 2、消息实现持久化

- 生产者在发布消息时，添加参数`MessageProperties.PERSISTENT_TEXT_PLAIN`
- 但不能保持一定持久化成功，比如在刚准备存储在disk还没存储完，消息还在缓存的一个间隔点时，此时并没有真正写入磁盘

###### 3、不公平分发

- 在消费者方设置不公平分发模式`channel.basicQos(1);`如果是0的话也是公平分发（轮询）
- 意思就是如果这个任务我还没有处理完或者我还没有应答你，你先别分配给我，我目前只能处理一个 任务，然后 rabbitmq 就会把该任务分配给没有那么忙的那个空闲消费者，当然如果所有的消费者都没有完成手上任务，队列还在不停的添加新任务，队列有可能就会遇到队列被撑满的情况，这个时候就只能添加 新的 worker 或者改变其他存储任务的策略。

