#### 一、SpringBoot2

##### 1、优点

- 创建独立Spring应用
- 内嵌web服务器
- 自动starter依赖，简化构建配置
- 自动配置Spring以及第三方功能
- 提供生产级别的监控、健康检查及外部化配置
- 无代码生成、无需编写XML
- SpringBoot是整合Spring技术栈的一站式框架
- SpringBoot是简化Spring技术栈的快速开发脚手架

#### 二、SpringBoot特点

##### 1、父项目做依赖管理

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

##### 2、自动配置

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

#### 三、容器功能

##### 1、组件添加

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

    - 应用场景

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

##### 2、原生配置文件引入

- @ImportResource注解——导入Spring配置文件

- 场景：公司使用bean.xml文件生成配置bean，然而你为了省事，想继续复用bean.xml，@ImportResource粉墨登场。

    ```java
    @Configuration
    @ImportResource("classpath:beans.xml") //复用原bean.xml文件，可以将以前bean.xml配置文件注册到IOC容器中
    public class MyConfig {
    	//...
    }
    ```

##### 3、配置绑定

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


##### 4、自动配置原理

- 引导加载自动配置类@SpringBootApplication

- @SpringBootApplication是一个合成注解，包括@SpringBootConfiguration、@EnableAutoConfiguration、@ComponentScan
    - @SpringBootConfiguration：实质还是@Configuration，代表当前是一个配置类
    - @ComponentScan：指定要扫描哪些包
    - @EnableAutoConfiguration：是一个合成注解，包括@AutoConfigurationPackage、@Import(AutoConfigurationImportSelector.class)
        - @AutoConfigurationPackage自动配置包（指定了默认的包规则）：
            - 本质是@Import(AutoConfigurationPackages.Registrar.class)
            - 给容器注册了一个AutoConfigurationPackages.Registrar的组件
            - 利用Registrar给容器中导入一系列组件
            - 会获取到注解所在的包名，即将指定的一个包下的所有组件导入进来，也因此是MainApplication所在的包下的所有组件注册到容器中（因为将@SpringBootApplication标注在了MainApplication类上）
        - @Import(AutoConfigurationImportSelector.class)（按需配置）：
            - 利用`getAutoConfigurationEntry(annotationMetadata);`给容器中批量导入一些组件
            - 调用`List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes)`获取到所有需要导入到容器中的配置类
            - 利用工厂加载 `Map<String, List<String>> loadSpringFactories(@Nullable ClassLoader classLoader);`得到所有的组件
            - 从`META-INF/spring.factories`位置来加载一个文件

- 按需开启自动配置项

    - 虽然各个场景的所有自动配置启动的时候默认全部加载，但是`xxxxAutoConfiguration`按照条件装配规则（`@Conditional`注解），最终会按需配置。

- SpringBoot默认会在底层配好所有的组件，但是如果用户自己配置了，那么以用户的优先，以编码过滤器为例：

    ```java
    @Bean
    @ConditionalOnMissingBean //当用户没有注册该组件时，那么执行该方法并注册组件，如果用户已经自己配置，那么将不会再次注册
    public CharacterEncodingFilter characterEncodingFilter() {
        //...
    }
    
    //注意：如果我认为默认的编码过滤器不好用，自己进行组件的注册，那么底层因为@ConditionalOnMissingBean注解的存在，则不会再次注册，并以用户注册的组件优先
    ```

- 总结

    - SpringBoot先加载所有的自动配置类（以AutoConfiguration为后缀）
    - 每个自动配置类按照条件进行生效（不是全部生效），默认都会绑定配置文件指定的值（以Properties为后缀的类），而Properties类又和配置文件进行了绑定（通过配置绑定的注解），因此方便定制化
    - 生效的配置类就会给容器中装配很多组件
    - 只要容器中有这些组件，相当于这些功能就有了
    - 定制化配置
        - 用户直接自己@Bean替换底层的组件
        - 用户去看这个组件是获取的配置文件的什么值就去修改

- 应用场景
    - 先引入相关的场景依赖（默认的或者是第三方的）
    - 查看自动配置了哪些（选做）
        - 自己进入自动配置类中分析，哪些生效了
        - 配置文件中debug=true开启自动配置报告会打印日志，其中Negative的是不生效的，Positive的是生效的
    - 是否需要定制化配置
        - 需要定制化，找到配置类对应的实体类，找到相应的前缀（prefix）和实体类中的属性，在配置文件中修改即可
        - 可以自定义加入或替换组件，通过@Bean、@Component等
        - 自定义器（以Customizer为后缀），以后讲

##### 5、开发技巧

- Lombok（简化Java Bean的开发）

    - 需要引入依赖，SpringBoot已经替我们管理了，不用写依赖的版本

    ```java
    @Data //在编译时，自动生成get、set方法
    @ToString //自动重写toString()
    @AllArgsConstructor //编译时自动生成全部参数的构造器
    @NoArgsConstructor //编译时自动生成无参构造器
    public class User {
        private String name;
    
        private int age;
    }
    ```

    - @Slf4j，可以直接用log.error("")进行日志的打印（或者log.info等等）
    - @EqualsAndHashCode，重写两个方法
    - 如果需要部分参数的构造器，需要自己手动去写

- dev-tools
    - 需要引入对应的依赖
    - 自动重启项目，不用手动重启，有丶小鸡肋，无所大谓

- Initailizr（项目初始化向导）
    - 创建SpringBoot工程向导，创建新项目的时候非常方便，会把目录、依赖、场景等等全部自动配置好

#### 四、配置文件

##### 1、Properties

- 语法同以前的Properties

##### 2、YAML

- 标记语言

- 适合用来做以数据为中心的配置文件

- 基本语法：

    - key冒号空格value（key: value）
    - 大小写敏感
    - 使用缩进表示层级关系
    - 缩进不允许使用tab，只允许空格
    - 缩进的空格数不重要，只要相同层级的元素左对齐即可
    - '#'表示注释
    - 字符串内容可以直接写，不需要引号
    - 双引号内的特殊字符不会转义，单引号内的特殊字符会转义

- 数据类型：

    - 字面量，如：date、boolean、string、number、null

        ```yaml
        k: v
        ```

    - 对象：键值对集合，如：map、hash、set、object

        ```yaml
        #行内写法
        k: {k1: v1,k2: v2,k3: v3}
        #或
        k: 
          k1: v1
          k2: v2
          k3: v3
        ```

    - 数组，如array、list、queue

        ```yaml
        #行内写法
        k: [v1,v2,v3]
        #或
        k:
          - v1
          - v2
          - v3
        ```

- 举个栗子

    ```java
    @ConfigurationProperties(prefix = "person")
    @Component
    @Data
    public class Person {
        private String userName;
        private Boolean boss;
        private Date birth;
        private Integer age;
        private Pet pet;
        private String[] interests;
        private List<String> animal;
        private Map<String, Object> score;
        private Set<Double> salarys;
        private Map<String, List<Pet>> allPets;
    }
    
    @Data
    public class Pet {
        private String name;
        private Double weight;
    }
    ```

    ```yaml
    person:
      userName: zhangsan
      boss: false
      birth: 2019/12/12 20:12:33
      age: 18
      pet: 
        name: tomcat
        weight: 23.4
      interests: [篮球,游泳]
      animal: 
        - jerry
        - mario
      score:
        english: 
          first: 30
          second: 40
          third: 50
        math: [131,140,148]
        chinese: {first: 128,second: 136}
      salarys: [3999,4999.98,5999.99]
      allPets:
        sick:
          - {name: tom}
          - {name: jerry,weight: 47}
        health: [{name: mario,weight: 47}]
    ```

- 自定义类绑定的配置提示

    - 这样在写的时候会有智能提示

    - 需要手动添加依赖

    - 因为该依赖只是在编码时使用，如果打包的话太冗余，可以在pom配置文件中，添加插件，在进行打包时不将配置处理器打包进去

        ```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
        
        <!-- 下面插件作用是工程打包时，不将spring-boot-configuration-processor打进包内，让其只在编码的时候有用 -->
        <build>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                    <configuration>
                        <excludes>
                            <exclude>
                                <groupId>org.springframework.boot</groupId>
                                <artifactId>spring-boot-configuration-processor</artifactId>
                            </exclude>
                        </excludes>
                    </configuration>
                </plugin>
            </plugins>
        </build>
        ```




#### 五、WEB开发

##### 1、SpringMVC自动配置

- 大多数场景不需要自定义配置

##### 2、简单功能分析

###### 2.1静态资源访问

- 放在`resources`目录下，新建`static`或`public`或`resources`或`META-INF/resources`

- 访问：当前项目根路径/ + 静态资源名，如`localhost:8080/foo.jpg`

- 原理：静态映射/**，请求进来之后，先去找controller能不能处理该请求映射，不能处理的所有请求又都交给静态资源处理器，静态资源则会去`resources`目录下的静态资源目录去找请求资源，如果资源也没有，则返回404

- 也可以改变默认的静态资源路径，`/static`，`/public`,`/resources`, `/META-INF/resources`失效，只有将静态资源放到`/foobar`目录下才会访问到

    ```yaml
    resources:
      static-locations: [classpath:/foobar/]
    ```

- 静态资源访问前缀

    - 默认无前缀，直接访问静态资源名即可

    - 当前项目名 + static-path-pattern + 静态资源名 = 静态资源文件夹下找，如`localhost:8080/bar/foo.jpg`

        ```yaml
        spring:
          mvc:
            static-path-pattern: /bar/**
        ```

###### 2.2静态资源配置原理

- SpringBoot启动默认加载xxxAutoConfiguration类（自动配置类）

- SpringMVC功能的自动配置类是WebMVCAutoConfiguration，根据条件装配注解查看是否生效

    ```java
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnWebApplication(type = Type.SERVLET)
    @ConditionalOnClass({ Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class })
    @ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
    @AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
    @AutoConfigureAfter({ DispatcherServletAutoConfiguration.class, TaskExecutionAutoConfiguration.class,
    		ValidationAutoConfiguration.class })
    public class WebMvcAutoConfiguration {
        ...
    }
    ```

- 给容器中配了什么

    ```java
    @Configuration(proxyBeanMethods = false)
    @Import(EnableWebMvcConfiguration.class)
    @EnableConfigurationProperties({ WebMvcProperties.class, ResourceProperties.class })
    @Order(0)
    public static class WebMvcAutoConfigurationAdapter implements WebMvcConfigurer {
        ...
    }
    ```

- 配置文件的相关属性的绑定：WebMvcProperties==spring.mvc实体类、ResourceProperties==spring.resources实体类

- 配置类中只有一个有参构造器时，有参构造的参数值都会从容器中确定

##### 3、请求参数处理

###### 3.1请求映射

- 即使用@xxxMapping注解

- RESTful风格的请求方式需要在SpringBoot中手动开启

    ```java
    @Bean
    @ConditionalOnMissingBean(HiddenHttpMethodFilter.class)
    @ConditionalOnProperty(prefix = "spring.mvc.hiddenmethod.filter", name = "enabled", matchIfMissing = false) //可以看出默认是不开启的，需要手动配置启动
    public OrderedHiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new OrderedHiddenHttpMethodFilter();
    }
    ```

    ```yaml
    #开启RESTful请求方式
    mvc:
    	hiddenmethod:
        	filter:
        		enable: true
    ```

- 因为只有表单发送请求时，才会经过包装为wrapper，被过滤器放行，转而进行PUT、DELETE等请求，但是其他方式（如通过客户端方式postman等），会直接发送RESTful的请求，因此SpringBoot中，默认不是开启的

- 请求映射原理

    - doGet->processRequest->doService->doService实现->doDispatch（每个请求都会调用）

    - 在`getHandler()`方法中，`this.handlerMappings`保存了所有的`@RequestMapping` 和`handler`的映射规则（即controller路径），请求进来时，将所有Controller的处理路径放入一个集合中，将请求进行对比，此时只会对比名称而不会匹配请求方式，然后将筛选出的同名的Controller请求进行对比，当哪个Controller能处理这个请求时，那么这个Controller就是请求所映射的Controller

        ```java
        @Nullable
        protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
            if (this.handlerMappings != null) {
                for (HandlerMapping mapping : this.handlerMappings) {
                    HandlerExecutionChain handler = mapping.getHandler(request);
                    if (handler != null) {
                        return handler;
                    }
                }
            }
            return null;
        }
        ```

    - SpringBoot自动配置欢迎页的 WelcomePageHandlerMapping 。因此访问 /能访问到index.html；

    - 我们需要一些自定义的映射处理，我们也可以自己给容器中放HandlerMapping，自定义HandlerMapping（后面讲）

###### 3.2常用参数注解使用

- @PathVariable 路径变量
- @RequestHeader 获取请求头
- @RequestParam 获取请求参数（指问号后的参数，url?a=1&b=2）
- @CookieValue 获取Cookie值
- @RequestAttribute 获取request域属性
- @RequestBody 获取请求体[POST]
- @MatrixVariable 矩阵变量
- @ModelAttribute

###### 3.3Entity（Bean、POJO）封装过程

- 数据绑定：页面提交的请求数据（GET、POST）都可以和对象属性（实体对象中的属性）进行绑定

- 传递过来的参数使用ServletModelAttributeMethodProcessor参数处理器处理
- WebDataBinder：web数据绑定器，将请求参数的值绑定到指定的JavaBean里面
- WebDataBinder利用它里面的Converters将请求数据转成指定的数据类型，再次封装到JavaBean中
- GenericConversionService：在设置每一个值的时候，找它里面的所有converter那个可以将这个数据类型（request带来参数的字符串）转换到指定的类型（如自定义User类中的Integer类型）

###### 3.4参数处理原理

- HandlerMapping中找到能处理请求的Handler（即Controller.method()）

- 为当前handler找一个适配器HandlerAdapter，默认RequestMappingHandlerAdapter

- HandlerAdapter（共四种）
    - RequestMappingHandlerAdapter - 支持方法上标注@RequestMapping
    - HandlerFunctionAdapter - 支持函数式编程
    - HttpRequestHandlerAdapter
    - SimpleControllerHandlerAdapter

- 执行目标方法
    - 会找到目标方法的全类名和方法签名
    - 使用`invokeHandlerMethod(request, response, handlerMethod);` 执行目标方法

- 参数解析器
    - 确定将要执行的目标方法的每一个参数的值是什么
    - SpringMVC目标方法能写多少种参数类型，取决于参数解析器
        - 首先当前解析器会判断是否支持解析传进来的参数
        - 如果可以，返回true，调用该解析器
        - 如果不行，则返回false，下一个解析器进行判断
        - 如RequestHeaderMethodArgumentResolver解析器用来解析注解为@RequestHeader 的参数，即通过`parameter.hasParameterAnnotation(RequestHeader.class)`，判断当前传进来的参数`parameter`是否有RequestHeader这个注解

- 返回值处理器
    - 包括ModelAndViewMethodReturnValueHandler、ModelMethodProcessor、ViewMethodeReturnValueHandler等进行返回值的处理

- 目标方法执行完成

    - 将所有的数据都放在ModelAndViewContainer中，包含要去的页面地址View，还包含Model数据

- 处理派发结果

    - `processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);`

    - 暴露模型作为请求域属性

        ```java
        暴露模型作为请求域属性
        // Expose the model object as request attributes.
        exposeModelAsRequestAttributes(model, request);
        ```

    - 然后model中的所有数据遍历挨个放在请求域中

        ```java
        model.forEach((name, value) -> {
            if (value != null) {
                request.setAttribute(name, value);
            }
            else {
                request.removeAttribute(name);
            }
        });
        ```

##### 4、数据响应与内容协商

###### 4.1响应JSON

- 使用Jackson.jar，并在方法上标注@RequestBody，就可以给前端自动返回Json数据
- 返回值解析器
    - 挨个遍历返回值处理器，返回值处理器会判断是否支持这种类型的返回值（supportsReturnType）
    - 返回值处理器调用handleReturnValue进行处理

###### 4.2内容协商

- 根据客户端接收能力不同，返回不同媒体类型的数据

- 内容协商原理（默认基于请求头）
    - 判断当前响应头中是否已经有确定的媒体类型（即MediaType）
    
    - 获取客户端（浏览器、Postman等）支持接收的内容类型（即获取客户端请求头的Accept数据，如`accept: application/json`，只接收Json类型的数据）
    
    - 遍历循环所有当前系统的MessageConverter，看谁支持操作这个对象（如自定义对象User）
    
    - 找到支持操作User的Converter，把Converter支持的媒体类型统计出来
    
    - 客户端需要`accept: application/json`，而服务端能力是10种+Json+xml
    
    - 找到内容协商的最佳匹配媒体类型 
    
    - 注：如果通过浏览器请求，请求头无法设置对应的值，浏览器有默认的请求头的值，可以在配置中开启基于请求参数的内容协商功能
    
        ```yaml
        spring:
        	contentnegotiation:
        		favor-parameter: true
        #在发送请求时，添加format字段并说明返回类型即可，如localhost:8080/test/getPerson?format=json，指明返回json类型
        ```

##### 5、视图解析与模板引擎

###### 5.1视图解析

- SpringBoot工程的打包结果是一个jar包，是压缩包，JSP不支持在压缩包中被编译运行，所以SpringBoot默认不支持JSP，需要引入第三方模板引擎技术实现页面渲染

- 视图处理方式：转发、重定向、自定义视图

###### 5.2模板引擎-Thymeleaf

- 现代化、服务端Java模板引擎

- 引入Starter

- 自动配置好了thymeleaf

    ```java
    @Configuration(proxyBeanMethods = false)
    @EnableConfigurationProperties(ThymeleafProperties.class)
    @ConditionalOnClass({ TemplateMode.class, SpringTemplateEngine.class })
    @AutoConfigureAfter({ WebMvcAutoConfiguration.class, WebFluxAutoConfiguration.class })
    public class ThymeleafAutoConfiguration { }
    ```

- 自动配好的策略

    - 1、所有thymeleaf的配置值都在 ThymeleafProperties
    - 2、配置好了 SpringTemplateEngine
    - 3、配好了ThymeleafViewResolver
    - 4、我们只需要直接开发页面

##### 6、拦截器

- 配置好拦截器要拦截哪些请求
- 把这些配置放在容器中

```java
@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    HttpSession session = request.getSession();
    Object loginUser = session.getAttribute("loginUser");
    if (loginUser != null) {
        //放行
        return true;
    }
    //拦截，未登录，跳转到登录页
    //往请求域中添加错误提示信息
    request.setAttribute("msg", "请先登录");
    //重定向至登录页
    //response.sendRedirect("/");
    request.getRequestDispatcher("/").forward(request, response);
    return false;
}
```

- 编写一个拦截器实现HandlerInterceptor接口
- 拦截器注册到容器中（实现WebMvcConfiguration的addInterceptor）
- 指定拦截规则（如果是拦截所有，静态资源也会被拦截）

```java
@Configuration
public class AdminWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")//所有的请求都被拦截，包括静态资源
                .excludePathPatterns("/", "/login", "/css/**", "/js/**", "/fonts/**", "images/**");//放行的资源，不可以直接/static/**，因为在地址栏访问资源时，是localhost:8080/css/xxx等，没有static的前缀
    }
}
```


