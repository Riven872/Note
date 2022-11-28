##### 一、SpringCloud-H版组件

###### 1、服务注册中心

- × Eureka
- √ zookeeper、Consul、Nacos

###### 2、服务调用1

- √ Ribbon、LoadBalancer

###### 3、服务调用2

- × Feign
- √ OpenFeign

###### 4、服务降级

- × Hystrix
- √ Resilience4j、sentinel

###### 5、服务网关

- × Zuul
- ? Zuul2
- √ GateWay

###### 6、服务配置

- × Config
- √ Nacos

###### 7、服务总线 

- × Bus
- √ Nacos



##### 二、微服务架构编码构建

###### 1、IDEA新建Project工作空间

- 微服务cloud整体聚合父工程Project

###### 2、父工程POM

- 进行依赖版本的约束

###### 3、Rest微服务工程构建

- cloud-provider-payment9001，微服务提供者支付Module模块
    - 建cloud-provider-payment9001模块
    - 改POM，将依赖引入到模块中
    - 写Yml，在类路径中新建配置文件
    - 新建SpringBoot主启动类
    - 业务类
        - 实体类Entity、Json封装体返回类
        - 数据库访问对象类dao、mybatis的映射文件
        - 模块功能接口service、实现类serviceImpl
        - 模块功能控制器controller
- cloud-consumer-order9000，微服务消费者订单Module模块（80端口被占用了）
    - 建cloud-consumer-order9000模块
    - 改POM，将依赖引入到模块中
    - 写Yml，在类路径中新建配置文件，只需要写端口即可
    - 新建SpringBoot主启动类
    - 业务类
        - 实体类Entity、Json封装体返回类
        - 模块功能控制器controller（只需要Controller即可，因为功能的实现在提供者内）
        - 配置RestTemplate，通过RestTemplate进行消费者对提供者的远程调用（使用RestTemplate远程调用一个HTTP接口）
- 工程重构
    - 有重复的类，新建cloud-api-commons，放服务接口、第三方接口、工具类、重复使用的类，不对外暴露，因此没有端口设置
    - maven命令：clean、install进行打包，供其他的项目使用
    - 每个模块的POM中，引入maven打包的自定义通用api的依赖，然后删除冗余的类即可



##### 三、Eureka服务注册与发现

###### 1、基础

- 服务治理：传统的RPC远程调用框架中，管理每个服务与服务之间的依赖关系比较复杂，所以需要服务治理，管理服务与服务之间的依赖关系，可以实现服务调用、负载均衡、容错等，实现服务发现与注册
- 服务注册与发现
    - Eureka Server 作为服务注册功能的服务器，它是服务注册中心。而系统中的其他微服务，使用 Eureka的客户端连接到 Eureka Server并维持心跳连接
    - 在服务注册与发现中，有一个注册中心。当服务器启动的时候，会把当前自己服务器的信息 比如 服务地址通讯地址等以别名方式注册到注册中心上。
    - 总结
        - 服务注册：将服务信息注册到注册中心
        - 服务发现：从注册中心上获取服务信息
- Eureka包含两个组件：Eureka Server和Eureka Client
    - Eureka Server提供服务注册服务：各个微服务节点通过配置启动后，会在EurekaServer中进行注册，这样EurekaServer中的服务注册表中将会存储所有可用服务节点的信息，服务节点的信息可以在界面中直观看到。
    - EurekaClient通过注册中心进行访问：是一个Java客户端，用于简化Eureka Server的交互，客户端同时也具备一个内置的、使用轮询(round-robin)负载算法的负载均衡器。在应用启动后，将会向Eureka Server发送心跳(默认周期为30秒)。如果Eureka Server在多个心跳周期内没有接收到某个节点的心跳，EurekaServer将会从服务注册表中把这个服务节点移除（默认90秒）

###### 2、单机构建Eureka

- IDEA生成EurekaServer服务端注册中心
    - 建module：cloud-eureka-server7001
    - 改pom：将Eureka的服务端的依赖添加进去
    - 写yml：配置端口以及Eureka服务端配置
    - 主main类：添加注解`@EnableEurekaServer`，表明本服务就是Eureka的服务端
- EurekaClient端cloud-provider-payment9001将注册进EurekaServer成为服务提供者provider
    - 改pom：将Eureka的客户端的依赖添加进去
    - 写yml：写Eureka服务端配置
    - 主main类：添加注解`@EnableEurekaClient`，表明本服务就是Eureka的客户端
- EurekaClient端cloud-consumer-order9000将注册进EurekaServer成为服务消费者consumer
    - 改pom：将Eureka的客户端的依赖添加进去
    - 写yml：写Eureka服务端配置
    - 主main类：添加注解`@EnableEurekaClient`，表明本服务就是Eureka的客户端

###### 3、集群构建Eureka

- 原理
    - 启动注册中心后，提供者会把自身信息注册到eureka
    - 消费者在需要调用接口时，使用服务别名去注册中心获取实际的RPC远程调用地址
    - 消费者获得调用地址后，底层实际使用httpClient技术实现远程调用，且获得调用地址后，会缓存到本地的JVM内存中，默认每间隔30s更新一次服务调用地址
- Eureka集群环境构建步骤
    - 建module：cloud-eureka-server7002
    - 改pom：将Eureka的服务端的依赖添加进去，并在父POM中添加子module
    - 修改host文件：使localhost不同的端口号映射同一个地址（不同域名用一个ip地址）
    - 写yml：遵循“相互守望、相互注册”的原则，将另一台Eureka注册到自己的yml中
    - 主main类：添加注解`@EnableEurekaServer`，表明本服务就是Eureka的服务端
- 将提供者和消费者发布到集群配置中
    - 修改yml中的defaultZone，指向两台服务器的地址
- 启动顺序：
    - eureka服务端
    - 服务提供者
    - 服务消费者
- 提供者服务集群环境构建
    - 建module：cloud-provider-payment9002
    - 改pom：将Eureka的客户端的依赖添加进去，并在父POM中添加子module
    - 写yml：改端口，其余跟9001相同
    - 主main：添加注解`@EnableEurekaClient`，表明本服务就是Eureka的客户端
    - 业务类：与9001相同
- 负载均衡
    - 订单访问服务地址不能写死成id+端口，而是通过在Eureka上注册过的微服务名称调用
    - @LoadBalanced注解在config中的RestTemplate上，使RestTemplate实现负载均衡的能力
    - Ribbon和Eureka整合后，消费者可以直接调用服务而不用关心地址和端口号，且该服务还有负载均衡的功能
- actuator微服务信息完善
    - 修改主机：服务名称
        - 在注册中心中，应用会显示主机名+服务名，不规范
        - 在提供者yml中，添加instance-id，设置的名称即在注册中心中显示的名称
    - 访问路径显示ip地址
        - 现在显示的是主机名+端口，不方便找具体的服务（规范应该是ip：port：服务名称）
        - 在提供者yml中，设置prefer-ip-address为true，即可在访问路径中显示ip地址
    - 总结：pom中的spring-boot-starter-actuator、spring-boot-starter-web和yml中的instance-id、prefer-ip-address完善了微服务的信息
- 服务发现Discovery
    - 对于注册进Eureka里面的微服务，可以通过服务发现来获得该服务的信息
    - 注入DiscoveryClient，来获得每个实例、服务的信息
    - 主启动类，使用注解`@EnableDiscoveryClient`
- Eureka自我保护机制
    - 现象和原因
        - 某时刻某一个微服务不可用了，Eureka不会立即清理，依旧会对该微服务的信息进行保存
        - 在自我保护模式中，Eureka服务器会保护服务注册表中的信息，不再注销任何服务实例
        - 因为微服务与Eureka服务器没有心跳连接的原因，可能只是网络故障，而非微服务实例本身健康度出现问题，，而不应该注销这个微服务。因此，当EurekaServer节点在短时间内丢失过多客户端时（可能发生了网络分区故障），那么这个节点就会进入自我保护模式。
        - 属于CAP里面的AP分支
    - 怎么禁止自我保护
        - 在Eureka服务端中，配置enable-self-preservation为false，eviction-interval-timer-in-ms为清理无效节点的频率，可以手动降低
        - 在Eureka客户端中，eureka.instance.lease-renewal-interval-in-seconds为客户端发送心跳间隔，eureka.instance.lease-expiration-duration-in-seconds为未发送心跳的超时时间



##### 四、Zookeeper服务注册与发现

- 使用SpringCloud整合zookeeper代替Eureka
- 服务配置注册中心



##### 五、Consul服务注册与发现

- 使用SpringCloud整合Consul代替Eureka
- 服务配置注册中心



##### 六、三个注册中心总结

###### 1、CAP理论

- C：Consistency（一致性）、A：Availability（可用性）、P：Partition Tolerance（分区容错性）
- CAP理论关注的颗粒度是数据，而不是整体系统设计的策略
- 微服务架构永远要保证P，因此微服务系统要么是AP，要么是CP
- 一个分布式系统不可能同时很好的满足一致性、可用性、分区容错性三个需求，最多只能同时较好的满足两个
- CA：单点集群，满足一致性、可用性的系统，通常在可扩展性上不太强大
- CP：满足一致性、分区容错性的系统，通常性能不是特别高，如Zookeeper、Consul、Redis
- AP：满足可用性、分区容错性的系统，通常对一致性要求低一点，如Eureka



##### 七、Ribbon负载均衡服务调用

###### 1、基础

- 是一套客户端负载均衡的工具，主要功能是提供客户端的软件负载均衡算法和服务调用

###### 2、负载均衡

- 将用户的请求平摊分配到多个服务器上，从而达到系统的HA（高可用）
- 与Nginx的区别
    - Nginx是服务器负载均衡，客户端的所有请求交给Nginx，然后通过转发请求实现负载均衡
    - Ribbon是本地（客户端）负载均衡，在调用微服务接口时，会在注册中心上获取注册信息服务列表之后缓存到本地JVM，从而在本地实现RPC远程调用
- 集中式LB
    - 在服务的消费方和提供方之间使用独立的LB设施，如Nginx，由该设施负责把访问请求通过某种策略转发给服务的提供方
- 进程内LB
    - 将LB逻辑集成到消费方，消费方从服务注册中心获知有哪些地址可用，然后自己再从这些地址中选择出一个合适的服务器，如Ribbon，只是一个类库，集成于消费方进程，消费方通过它来获取到服务提供方的地址
- 总结：Ribbon就是一个软负载均衡的客户端组件，可用和其他所需请求的客户端结合使用

###### 3、RestTemplate的使用

- getForObject，返回对象为响应体中数据转化成的对象，基本上可以理解为Json
- getForEntity，返回对象为ResponseEntity对象，包含了响应中的一些重要信息，比如响应头、响应状态码、响应体等

###### 4、Ribbon核心组件IRule

- 根据特定算法中从服务列表中选取一个要访问的服务
    - RoundRobinRule：轮询（默认）
    - RandomRule：随机
    - RetryRule：先按照轮询的策略获取服务，如果获取服务失败则在指定时间内进行重试，获取可用的服务
    - ResponseTimeRule：对轮询策略的扩展，响应速度越快的实例选择权重越大，越容易被选择
    - BestAvailableRule：会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务
    - AvailabilityFilteringRule：先过滤掉故障实例，再选择并发较小的实例
    - ZoneAvoidanceRule：复合判断server所在区域的性能和server的可用性选择服务器
- 如何替换规则
    - 自定义配置类不能处在@component的扫描范围内，即不能跟主启动类一个包，否则起不到特殊定制化的作用
    - 在主启动类添加注解`@RibbonClient(name = "CLOUD-PAYMENT-SERVICE",configuration=MySelfRule.class)`，用来指定使用自定义的Ribbon配置类，且访问的服务为CLOUD-`PAYMENT-SERVICE`微服务

###### 5、Ribbon负载均衡算法

- 轮询算法原理
    - rest接口第几次请求数 % 服务器集群总数 = 实际调用服务器位置下标
    - 如：
        - 名为CLOUD-PAYMENT-SERVICE的微服务有三台服务器，即instance实例数为3
        - 且假设instance[0]的地址为A，instance[1]的地址为B，instance[2]的地址为C
        - 请求数为1时：1 % 3 = 1，下标为1，则获得的服务地址为B
        - 类推。。。
        - 每次服务重启后，请求次数重置为1



##### 八、OpenFeign服务接口调用

###### 1、概述

- OpenFeign是一个声明式的Web服务客户端，让编写Web服务客户端变得非常容易，只需创建一个接口并在接口上添加注解即可
- 使用Ribbon+RestTemplate时，是利用RestTemplate对http请求进行封装处理。而OpenFeign是进一步封装，只需要创建一个接口并使用注解的方式来配置，就可以完成对服务提供方的接口绑定
- 同时OpenFeign集成了Ribbon，因此实现了客户端的负载均衡

###### 2、OpenFeign使用步骤

- 新建cloud-consumer-feign-order9000
    - 在yml中，连接到Eureka注册中心
    - 在主启动类上，添加注解@EnableFeignClients，激活OpenFeign
    - 新建接口PaymentFeignService，并添加注解`@Component`使IOC容器能扫描到、添加`@FeignClient("CLOUD-PAYMENT-SERVICE")`表明这个是Feign组件使用的接口并指定服务提供者
    - Controller中的接口去调用本消费者的Feign接口，然后通过Feign接口去注册中心找到对应的提供者，再调用提供者对应的暴露出的接口（即中间隔了一层Feign）

###### 3、OpenFeign超时控制

- 远程调用提供者接口时，因为OpenFeign默认支持Ribbon，因此OpenFeign也会默认等待1s钟，提供者接口没有返回时，报超时错误 
- 简单来说，客户端只会等待1s，但是服务端处理需要超过1s钟，此时就会导致OpenFeign客户端超时等待，直接返回报错
- 可以在消费端yml文件中，设置ribbon:ReadTimeout来指定建立连接后从服务器读取到可用资源所用的时间，ribbon:ConnectTimeout来指定建立连接所用的最大时间

###### 4、OpenFeign日志打印功能

- 通过配置来调整日志级别，了解OpenFeign中Http请求的细节，从而对OpenFeign接口调用的情况进行监控和输出
- 在yml中配置OpenFeign日志以什么级别监控哪个接口



##### 九、Hystrix断路器

###### 1、概述

- 一个模块下某个实例失败后，这个模块依然还会接收流量，然后这个有问题的模块还调用了其他的模块，这样就会发生级联故障
- Hystrix是一个用于分布式系统的延迟和容错的开源库 ，能保证在一个依赖出现问题的情况下，不会导致整体服务失败，避免级联故障，以提高分布式系统的弹性
- 断路器是一种开关装置，当某个服务单元发生故障之后，通过断路器的故障监控，向调用方返回一个符合预期的、可处理的备选响应（FallBack），而不是长时间的等待或者抛出调用方无法处理的异常，保证服务调用方的线程不会被长时间、不必要的占用，从而避免故障在分布式系统中的蔓延
- 功能：服务降级、服务熔断、实时监控等

###### 2、Hystrix重要概念

- 服务降级（fallback）：
    - 服务出现故障或不可用时，向调用方返回一个符合预期的、可处理的备选响应
    - 触发降级的情况：程序运行异常、超时、服务熔断触发服务降级、线程池\信号量打满
- 服务熔断（break）
    - 类似保险丝达到最大服务访问后，直接拒绝访问（然后调用服务降级并返回友好提示）
- 服务限流（flowlimit）
    - 秒杀等高并发操作等，防止海量的请求直接打在服务器上

###### 3、Hystrix案例

- 构建hystrix服务提供方
    - 新建cloud-provider-hystrix-payment9001
- 构建hystrix服务消费方
    - 新建cloud-consumer-feign-hystrix-order9000
- 高并发问题
    - 如暴露出的Controller中有A、B两个接口，其中调用A接口的只有1个线程，而调用B接口的有巨量线程，此时就会拖慢A接口的返回速度（即大多数线程都去处理B线程了，可能暂时没有空闲的线程去处理A线程，需要线程空闲才行）
    - 是因为同一层此次的其他接口服务被困死，服务器线程池里面的工作线程已经被挤占完毕，没有多余的线程来分解压力和处理
- 解决方案：
    - 超时导致服务器变慢（请求转圈）：超时不再等待
    - 出错（宕机或程序运行出错）：出错要有兜底
    - 解决：
        - 服务超时、宕机，调用者不能一直卡死等待，必须有服务降级
        - 服务ok，调用者自己出故障或有自我要求（自己的等待时间小于服务提供者），自己处理降级

###### 4、服务降级

- 使用降级配置注解

    ```java
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",
                    commandProperties = {@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "3000")})
    @Override
    public String paymentInfo_TimeOut(Integer id) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread() +
            " paymentInfo_OK，id：" + id +
            " 耗时: 5s";
    }
    ```

    - fallbackMethod表明该方法执行错误或超时，则调用目标方法进行兜底
    - `@HystrixProperty`表示该方法的自身调用超时峰值为3000ms，超时则调用服务降级fallback

- 在主启动类激活降级配置注解，`@EnableCircuitBreaker`

- 服务降级一般放在客户端，以上的服务端只是为了演示（而且客户端使用了OpenFeign，因此与服务端的注解不同）

    - 在yml中添加配置：feign:hystrix:enabled:true，开启Hystrix服务降级
    - 主启动类：添加注解`@EnableHystrix`

- 需要解决的问题

    - 每个方法都需要配置一个fallback方法，导致代码膨胀
        - 设置全局的fallback方法，在没有单独指定时，将该方法作为默认fallback方法
        - 类上添加注解`@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")`，表明该类中有默认的全局fallback方法payment_Global_FallbackMethod
        - 在方法上添加注解`@HystrixCommand`，表明该方法没有单独指定，将默认的方法作为fallback
    - fallback方法跟业务逻辑混在一起，造成代码混乱
        - 为Feign客户端定义的接口添加一个服务降级处理的实现类即可实现解耦
        - 异常类型：运行时异常、超时、服务器宕机
        - 新建一个类，并实现Feign接口，为接口里的每一个方法进行异常处理
        - 在Feign接口上更改注解：`@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT", fallback = PaymentFallbackService.class)`，表明fallback的位置

###### 5、服务熔断

- 应对雪崩效应的一种微服务链路保护机制，当链路的某个微服务出错不可用或者响应时间太长时，会进行服务降级，进而熔断该节点微服务的调用，快速返回错误的响应信息，**当检测到该节点微服务响应正常后，恢复调用链路**

- Hystrix会监控微服务间的调用状况，当失败的调用到一次阈值，缺省是5s内20次调用失败，就会启动熔断机制

- 服务熔断注解

    ```java
    //在60s内，至少有10次请求且失败率在60%时，就会触发断路器。就算有9次请求，且全部失败，也不会触发断路器
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
        //是否开启断路器
        @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
        //熔断判断逻辑中最小的请求数
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
        //时间窗口期（时间范围）
        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
        //失败率到达多少后跳闸
        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
    })public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        
    }
    ```

- 服务降级->进而熔断->恢复调用链路

    - 服务降级：当运行时异常、超时或服务器宕机时，会调用声明的fallback方法，即服务降级
    - 进而熔断：当请求的次数和失败率到达阈值时，断路器触发，此时就算没有运行异常、超时、服务器恢复，所有的请求也会处于服务降级状态
    - 恢复调用链路：在熔断情况下，如果有持续的请求进来，且成功率不断上升时，会自动关闭断路器，链路调用恢复

- 熔断类型

    - 熔断打开：请求不再进行调用当前服务，内部设置时钟一般为MTTR（平均故障处理时间），当打开时长达到所设时钟则进入半熔断状态
    - 熔断关闭：熔断关闭时不会对服务进行熔断
    - 熔断半开：部分请求根据规则调用当前服务，如果请求成功且符合规则就认为当前服务恢复正常，关闭熔断

- 在熔断后关闭的一段时间内（默认5s，可以自定义），这个时候断路器处于半开状态，会让其中一个请求进行转发，如果成功，断路器关闭，若失败，断路器继续开启，所有请求都不会进行转发（即不会调用主逻辑，而是直接调用降级fallback）

###### 6、服务限流（SpringCloud Alibab的Sentinel讲解）

###### 7、服务监控HystrixDashboard

。。。



##### 十、Gateway路由网关

###### 1、概述

- SpringCloud Gateway是基于WebFlux框架实现的，而WebFlux框架底层则使用了高性能的Reactor模式通信框架Netty（即基于非阻塞模型进行开发）
- 功能：反向代理、鉴权、流量控制、熔断、日志监控等
- 在微服务架构中，一般是：外部请求->负载均衡->网关->每个微服务
- 特性
    - 基于Spring5、Reactor和SpringBoot2构建
    - 动态路由，能够匹配任何请求属性
    - 可以对路由指定Predicate（断言）和Filter（过滤器）
    - 集成Hystrix的断路器功能
    - 集成SpringCloud服务发现功能
    - 请求限流功能
    - 支持路径重写
- 与Zuul的区别
    - Zuul使用的是阻塞架构，性能差
    - Gateway支持非阻塞API、长连接、websocket 

###### 2、三大核心概念

- 路由（route）：是构建网关的基本模块，由ID、目标URI、一系列的断言和过滤器组成，如果断言为true则匹配该路由
- 断言（predicate）：匹配HTTP请求中的所有内容（如请求头或请求参数），如果请求与断言相匹配则进行路由
- 过滤（filter）：指Spring框架中GatewayFilter的实例，使用过滤器，可以在请求被路由前或者之后对请求进行修改

###### 3、Gateway工作流程

- 客户端向SpringCloud Gateway发出请求，然后在Gateway Handler Mapping中找到与请求相匹配的路由，将其发送到Gateway Web Handler，Handler再通过指定的过滤器链来将请求发送到我们实际的服务执行业务逻辑，然后返回（过滤器可能会在发送代理请求之前pre或之后post执行业务逻辑）
- Filter在pre类型的过滤器可以做参数校验、权限校验、流量监控、日志输出、协议转换等等，在post类型的过滤器中可以做响应内容 、响应头的修改、日志的输出、流量监控等
- 核心逻辑：路由转发+执行过滤器链

###### 4、入门配置

- 新建Module：cloud-gateway-gateway9527

- 配置yml：配置端口、注册进Eureka

- 主启动类：标记`@EnableEurekaClient`注解

- 业务类：只是一个网关，因此不需要业务逻辑

- 9527网关如何做路由映射
    - 目前不想暴露9001端口，希望在9001外面多一层9527
    - yml新增网关配置

- GateWay网关路由的两种配置方式

    - yml文件中配置

    - 代码中注入RouteLocator的Bean

        ```java
        @Configuration
        public class GatewayConfig {
            /**
             * 配置了一个id为foo_id的路由规则
             * 当访问地址http://localhost:9527/872时，会自动转发到地址http://www.baidu.com
             * @param builder
             * @return
             */
            @SuppressWarnings("all")
            @Bean
            public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
                RouteLocatorBuilder.Builder routes = builder.routes();
                routes.route("foo_id", r ->
                    r.path("/872").uri("http://www.baidu.com")).build();
        
                return routes.build();
            }
        }
        ```

###### 5、通过微服务名实现动态路由

- 默认情况下，Gateway会根据注册中心注册的服务列表，以注册中心上微服务名为路径创建**动态路由进行转发，从而实现动态路由的功能**

- 修改yml配置

    不再uri将地址写死，而是通过微服务名去调用请求，且前缀`lb:`的意思的是要在CLOUD-PAYMENT-SERVICE找到的服务中进行负载

    ```yaml
    cloud:
        gateway:
          discovery:
            locator:
              enabled: true #开启从注册中心动态创建路由的功能，利用微服务名进行路由
          routes:
            - id: payment_routh #payment_route  #路由的id，没有固定规则但要求唯一，建议配合服务名
              #uri: http://localhost:9001  
              uri: lb://CLOUD-PAYMENT-SERVICE #匹配后提供服务的路由地址
              predicates:
                - Path=/payment/get/**  #断言，路径相匹配的进行路由
            - id: payment_routh2
              #uri: http://localhost:9001
              uri: lb://CLOUD-PAYMENT-SERVICE #匹配后提供服务的路由地址
              predicates:
                - Path=/payment/lb/**
    ```

###### 6、Predicate的使用

- 相当于路径匹配条件或限制条件，如路径名包含xx的、限定某个日期后才生效等
- RoutePredicateFactory举例
    - \- After=2019-09-15T20:58:18.786182+08:00[Asia/Shanghai]：在指定时间（默认时区时间）之后，路由匹配才会生效
    - \- Before、Between同理，限定路由的生效时间
    - \- Cookie：带Cookie能访问、不能Cookie能访问、带什么样的Cookie能访问
    - \- Header、Host、：与Cookie原理相同
    - \- Method：限定Get或Post等请求才能访问
    - \- Path：微服务名下，有匹配路径的才能访问
    - \- Query：要有参数名且值匹配自定义规则才能匹配
- 总结：Predicate就是为了实现一组匹配规则，让请求过来找到对应的Route进行处理

###### 7、Filter的使用

- 路由过滤器可用于修改进入的HTTP请求和返回的HTTP响应，路由过滤器只能指定路由进行使用

- SpringCloudGateway的Filter

    - 生命周期只有两个：pre、post
    - 种类只有两种：GatewayFilter（单一的）、GlobalFilter（全局的）

- 自定义全局GlobalFilter

    - 在yml文件中配置

        ```yaml
        filters:
        - AddRequestHeader=X-Request-Foo, Bar
        #filter为AddRequestHeaderGatewayFilterFactory(约定写成AddRequestHeader)，AddRequestHeader过滤器工厂会在请求头加上一对请求头，名称为X-Request-Foo，值为Bar
        ```

        

    - 需要实现两个接口GlobalFilter和Ordered

    ```java
    @Component
    @Slf4j
    public class MyLogGatewayFilter implements GlobalFilter, Ordered {
        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            String name = exchange.getRequest().getQueryParams().getFirst("userName");
            if (name == null) {
                log.info("用户名为空，非法用户");
                exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
                //请求不合法，过滤请求
                return exchange.getResponse().setComplete();
            }
            //请求合法，继续过滤器链
            return chain.filter(exchange);
        }
    
        //表示该过滤器的优先级，数字越低优先级越高，通常全局过滤器是最高优先级
        @Override
        public int getOrder() {
            return 0;
        }
    }
    ```

- 作用：全局日志记录、统一网关鉴权等
