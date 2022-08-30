##### 一、SpringBoot2

###### 1、优点

- 创建独立Spring应用
- 内嵌web服务器
- 自动starter依赖，简化构建配置
- 自动配置Spring以及第三方功能
- 提供生产级别的监控、健康检查及外部化配置
- 无代码生成、无需编写XML
- SpringBoot是整合Spring技术栈的一站式框架
- SpringBoot是简化Spring技术栈的快速开发脚手架

##### 二、SpringBoot特点

###### 1、父项目做依赖管理

```xml
依赖管理
<parent>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-parent</artifactId>
	<version>2.3.4.RELEASE</version>
</parent>

上面项目的父项目如下：
<parent>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-dependencies</artifactId>
	<version>2.3.4.RELEASE</version>
</parent>

它几乎声明了所有开发中常用的依赖的版本号，自动版本仲裁机制
```

- 开发导入starter场景启动器

    - 见到很多 spring-boot-starter-* ： *就某种场景

    - 只要引入starter，这个场景的所有常规需要的依赖我们都自动引入


    ```xml
    所有场景启动器最底层的依赖
    <dependency>
    	<groupId>org.springframework.boot</groupId>
    	<artifactId>spring-boot-starter</artifactId>
    	<version>2.3.4.RELEASE</version>
    	<scope>compile</scope>
    </dependency>
    ```

- 无需关注版本号，自动版本仲裁

    - 引入依赖默认都可以不写版本

    - 引入非版本仲裁的jar，要写版本号。

    - 可以修改版本号，Maven采用就近原则，会将自己设置的代替父项目的依赖

        ```xml
        查看spring-boot-dependencies里面规定当前依赖的版本用的key
        在当前项目里重写配置
        <properties>
            <mysql.version>自己要想要的版本号</mysql.version>
        </properties>
        ```

###### 2、自动配置

- 自动配好Tomcat

    - 引入Tomcat依赖
    - 配置Tomcat

- 自动配好SpringMVC

    - 引入SpringMVC全套组件
    - 自动配好SpringMVC常用组件（功能）

- 自动配好Web常见功能，如：字符编码问题

    - SpringBoot帮我们配置好了所有web开发的常见场景

    ```java
    public static void main(String[] args) {
        //1、返回我们IOC容器
        ConfigurableApplicationContext run = SpringApplication.run(MainApplication.class, args);
    
        //2、查看容器里面的组件
        String[] names = run.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
    ```

- 默认的包结构

    - 主程序所在包及其下面的所有子包里面的组件都会被默认扫描进来

    - 无需以前的包扫描配置

    - 想要改变扫描路径

        - @SpringBootApplication(scanBasePackages=“com.edu.boot”)
        - @ComponentScan 指定扫描路径

        ```java
        @SpringBootApplication
        等同于
        @SpringBootConfiguration
        @EnableAutoConfiguration
        //因为@ComponentScan中会默认写主程序所在的包，因此只写@SpringBootApplication注解时，会自动扫描主程序所在的包或其子包
        //当然也可以自定义扫描包的范围
        @ComponentScan("com.edu.boot")
        ```

- 各种配置拥有默认值
    - 默认配置最终都是映射到某个类上，如：`MultipartProperties`
    - 配置文件的值最终会绑定每个类上，这个类会在容器中创建对象

- 按需加载所有自动配置项
    - 非常多的starter
    - 引入了哪些场景这个场景的自动配置才会开启
    - SpringBoot所有的自动配置功能都在 spring-boot-autoconfigure 包里面

##### 三、容器功能

###### 1、组件添加

- @Bean、@Component、@Controller、@Service、@Repository，它们是Spring的基本标签，在Spring Boot中并未改变它们原来的功能

- @Configuration注解——标记配置类

    - 基本使用

    - Full模式与Lite模式

    - 示例

        ```java
        /**
         * 1、配置类里面使用@Bean标注在方法上给容器注册组件，默认也是单实例的
         * 2、配置类本身也是组件
         * 3、proxyBeanMethods：代理bean的方法
         *      Full(proxyBeanMethods = true)（保证每个@Bean方法被调用多少次返回的组件都是单实例的）（默认）
         *      Lite(proxyBeanMethods = false)（每个@Bean方法被调用多少次返回的组件都是新创建的）
         */
        @Configuration(proxyBeanMethods = false)//@Configuration注解表示告诉SpringBoot这是一个配置类（作用等于配置文件）
        public class MyConfig {
        
            /**
             * Full:外部无论对配置类中的这个组件注册方法调用多少次获取的都是之前注册容器中的单实例对象
             * @return
             */
            @Bean //给容器中添加组件。以方法名作为组件的id。返回类型就是组件类型。返回的值，就是组件在容器中的实例
            public User user01(){
                User zhangsan = new User("zhangsan", 18);
                //user组件依赖了Pet组件
                zhangsan.setPet(tomcatPet());
                return zhangsan;
            }
        
            @Bean("tom") //组件名变为自定义的“tom”，而非方法名
            public Pet tomcatPet(){
                return new Pet("tomcat");
            }
        }
        ```

    - 最佳实战

    - 配置 类组件之间无依赖关系用Lite模式加速容器启动过程，减少判断

    - 配置 类组件之间有依赖关系，方法会被调用得到之前单实例组件，用Full模式（默认）

    - 总结：

        - true时：相当于单例模式，会将组件添加到容器中，每次用到该组件时，会检查容器中是否有该组件（因此启动时会慢一点），如果有则直接从容器中拿而非再进行实例化一个，当组件之间有依赖时，需要用此，否则会出现依赖的组件不是原来我的组件，而且新实例化了一个
        - false时：跳过检查组件的过程，直接进行一个新的实例化，当没有组件依赖时，用此，跳过判断检查速度快的一麻批

- @Import注解——导入组件

    - @Import({User.class, DBHelper.class})给容器中自动创建出这两个类型的组件、默认组件的名字就是全类名（会默认调用无参的构造器进行创建）

        ```java
        @Import({User.class, DBHelper.class})
        @Configuration //告诉SpringBoot这是一个配置类 == 配置文件
        public class MyConfig {
        }
        ```

- @Conditional注解——条件装配

    - 满足Conditional指定的条件，则进行组件注入

    - 其下有很多派生注解，以@ConditionalOnBean注解为例

        ```java
        @Configuration
        @ConditionalOnBean(name = "jack") //当容器中注册了jack组件时，该类中的所有组件才进行注册
        public class MyConfig {
        
            @ConditionalOnBean(name = "jack") //也可以放在组件方法上，表示当容器中注册了jack组件时，该组件才进行注册
            @Bean
            public User user01(){
                User zhangsan = new User("zhangsan", 18);
                zhangsan.setPet(tomcatPet());
                return zhangsan;
            }
        
            @Bean("tom")
            public Pet tomcatPet(){
                return new Pet("tomcat");
            }
        }
        ```

###### 2、原生配置文件引入

- @ImportResource注解——导入Spring配置文件

- 场景：公司使用bean.xml文件生成配置bean，然而你为了省事，想继续复用bean.xml，@ImportResource粉墨登场。

    ```java
    @Configuration
    @ImportResource("classpath:beans.xml") //复用原bean.xml文件，可以将以前bean.xml配置文件注册到IOC容器中
    public class MyConfig {
    	//...
    }
    ```

###### 3、配置绑定

- 传统方法读取properties文件中的内容，并且把它封装到JavaBean中，以供随时使用非常繁琐

- 例子（@ConfigurationProperties + @Component）

    - 有实体类

        ```java
        @Component //只有在容器中的组件，才会拥有SpringBoot提供的强大功能，因此需要该注解注册到容器中
        @ConfigurationProperties(prefix = "mycar") //自定义该实体类的配置文件中的前缀是"mycar"
        public class Car {
            private String barnd;
            private int price;
            //...对应的get set方法
        }
        ```

    - 有配置文件

        ```properties
        #各种其他的配置项，巨多，找起来很繁杂
        mycar.brand=BYD
        mycar.price=100000
        #使用配置绑定注解，将该配置与实体类绑定，字段对应，且前缀为自定义值
        #当配置好后，实体类对应的属性会有对应的默认值（使用@AutoWired时，会将默认值自动装配进去）
        ```

- 例子（@ConfigurationProperties + @EnableConfigurationProperties）

    - 有实体类

        ```java
        //@Component //可能会用到第三方的jar包，而又不能去别人的jar包中写注解的情况
        @ConfigurationProperties(prefix = "mycar") //自定义该实体类的配置文件中的前缀是"mycar"
        public class Car {
            private String barnd;
            private int price;
            //...对应的get set方法
        }
        ```

    - 有配置类

        ```java
        @EnableConfigurationProperties(Car.class) //开启Car实体类的配置绑定功能且将Car这个组件自动注册到容器中
        @Configuration //表明这是一个配置类
        public class MyConfig {
        	//...
        }
        ```

    - 配置文件同上

