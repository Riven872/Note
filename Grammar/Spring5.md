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

- IOC底层原理（使用了XML解析、工厂设计模式、反射技术）

- IOC过程

  - 第一步：XML配置文件，配置创建的对象

    ```xml
    <bean id="dao" class="com.edu.UserDao"></bean>
    ```

  - 第二步：有service类和DAO类，创建工厂类

    ```java
    public class UserFactory {
        public static UserDAO getDAO() {
    
        }
    }
    ```

    
