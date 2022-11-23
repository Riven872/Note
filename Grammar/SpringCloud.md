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
    - 某时刻某一个微服务不可用了，Eureka不会立即清理，依旧会对该微服务的信息进行保存