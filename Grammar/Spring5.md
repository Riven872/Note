###### 概述

- Spring是轻量级的开源的JavaEE框架
- Spring可以解决企业应用开发的复杂性
- Spring的两个核心：IOC和Aop
  - IOC：控制反转，把创建对象过程交给Spring进行管理
  - Aop：面向切面，不修改源代码进行功能增强
- Spring特点
  - 方便解耦，简化开发
  - Aop编程支持
  - 方便程序测试
  - 方便和其他框架进行整个
  - 方便进行事务操作
  - 降低API开发难度

###### IOC容器

- 什么是IOC

  - 控制反转，把对象创建和对象创建之间的调用过程，交给Spring进行管理
  - 使用IOC的目的是为了降低耦合度

- IOC容器：

    - IOC底层原理
    - IOC接口（BeanFactory）
    - IOC操作Bean管理（基于XML）
    - IOC操作Bean管理（基于注解）

- IOC底层原理（使用了XML解析、工厂设计模式、反射技术）

    - 第一步：XML配置文件，配置创建的对象

      ```xml
      <bean id="DAO" class="com.edu.UserDAO"></bean>
      ```

    - 第二步：有service类和DAO类，创建工厂类（service类需要DAO类中的方法时，可以通过工厂得到DAO类而非直接new，降低了service和DAO的耦合性）

      ```java
      public class UserFactory {
          public static UserDAO getDAO() {
      		String classValue = class属性值;//XML解析
              Class clazz = Class.forName(classValue);//通过反射创建对象
              return (UserDAO) clazz.newInstance();//实例化
          }
      }
      ```
      

- IOC接口

    - IOC思想基于IOC容器完成，IOC容器底层就是对象工厂
    - Spring提供IOC容器实现的两种方式：（两个接口）
        - BeanFactory：IOC容器基本实现，是Spring内部的使用接口，不提供给开发人员进行使用
            - 加载配置文件时候不会创建对象，在获取对象（要使用对象时）的时候才会去创建
        - ApplicationContext：BeanFactory接口的子接口，提供更多更强大的功能，一般由开发人员进行使用
            - 加载配置文件时就会把在配置文件里的对象进行创建
    - ApplicationContext接口有实现类
        - FileSystemXmlApplicationContext()，接收配置文件的绝对路径
        - ClassPathXmlApplicationContext()，接收配置文件的相对路径（src目录下）

- IOC操作Bean管理

    - Bean管理指的是两个操作
        - 1、Spring创建对象
        - 2、Spring注入属性
    - Bean管理操作的两种方式
        - 1、基于XML配置文件方式实现
        - 2、基于注解方式实现

- IOC操作Bean管理之创建对象（基于XML配置文件方式实现，即基于XML方式创建对象）

    - 1、在Spring配置文件中，使用Bean标签，标签里面添加对应的属性，就可以实现对象的创建

        ```xml
        <bean id="DAO" class="com.edu.UserDAO"></bean>
        ```

    - 2、Bean标签中的属性说明

        - id属性：唯一标识
        - class属性：类的全路径
        - name属性：类似于id属性，但现在几乎不用

    - 3、创建对象时，默认执行该类的无参构造方法完成对象的创建（如果该类中没有无参的构造方法，会报错）

        ```java
        //加载Spring配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        //获取配置并创建对象
        User user = context.getBean("user", User.class);
        ```

- IOC操作Bean管理之注入属性（基于注解方式实现，即基于XML方式注入属性）

    - 1、DI：依赖注入，就是注入属性

        - 调用set方法注入

            - 1、创建类，定义属性和对应的set方法
            - 2、在配置文件配置对象创建，配置属性注入

                ```xml
                <!-- 配置User对象的创建-->
                <bean id="user" class="com.edu.IoC01.User">
                    <!-- 使用property标签完成属性的注入
                             name:类里面属性名称
                             value:向属性注入的值
                        -->
                    <property name="age" value="25"></property>
                    <property name="name" value="龙城"></property>
                </bean>
                <!-- 要求必须有对应的set方法，否则会报错 -->
                ```

        - 用有参的构造方法注入
        
            - 1、创建类，定义属性，创建属性对应有参的构造函数
        
            - 2、在 Spring配置文件中进行配置
        
                ```xml
                <!-- 有参构造函数注入属性：配置Order对象的创建-->
                <bean id="order" class="com.edu.IoC01.Order">
                    <!-- 如果不写此标签会报错，因为类中没有无参的构造器，用此标签后，会通过有参的构造器创建类
                             name:类里面属性名称
                             value:向属性注入的值
                        -->
                    <constructor-arg name="id" value="001"></constructor-arg>
                    <constructor-arg name="name" value="数据结构与算法"></constructor-arg>
                    <!-- 也可以通过索引值去定位要注入的属性-->
                    <constructor-arg index="1" value="第1个属性"></constructor-arg>
                </bean>
                ```
        
                

