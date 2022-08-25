##### 一、MyBatis

###### 1、MyBatis特性

- MyBatis 是支持定制化 SQL、存储过程以及高级映射的优秀的持久层框架
- MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集
- MyBatis可以使用简单的XML或注解用于配置和原始映射，将接口和Java的POJO（Plain Old Java Objects，普通的Java对象）映射成数据库中的记录
- MyBatis 是一个 半自动的ORM（Object Relation Mapping）框架

###### 2、和其它持久化层技术对比

- JDBC
    - SQL 夹杂在Java代码中耦合度高，导致硬编码内伤
    - 维护不易且实际开发需求中 SQL 有变化，频繁修改的情况多见
    - 代码冗长，开发效率低

- Hibernate 和 JPA
    - 操作简便，开发效率高
    - 程序中的长难复杂 SQL 需要绕过框架
    - 内部自动生产的 SQL，不容易做特殊优化
    - 基于全映射的全自动框架，大量字段的 POJO 进行部分映射时比较困难。
    - 反射操作太多，导致数据库性能下降

- MyBatis
    - 轻量级，性能出色
    - SQL 和 Java 编码分开，功能边界清晰。Java代码专注业务、SQL语句专注数据
    - 开发效率稍逊于HIbernate，但是完全能够接受

###### 3、创建MyBatis的核心配置文件

- 习惯上命名为mybatis-config.xml，这个文件名仅仅只是建议，并非强制要求。将来整合Spring之后，这个配置文件可以省略，所以大家操作时可以直接复制、粘贴。核心配置文件主要用于配置连接数据库的环境以及MyBatis的全局配置信息核心配置文件存放的位置是src/main/resources目录下

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--设置连接数据库的环境-->
    <environments default="development">
        <environment id="development">
            <!--事务管理还是通过最初始的JDBC进行管理-->
            <transactionManager type="JDBC"/>
            <!--数据源，POOLED表示使用的是数据库连接池-->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/MyBatis"/>
                <property name="username" value="root"/>
                <property name="password" value="123456"/>
            </dataSource>
        </environment>
    </environments>
    <!--引入映射文件-->
    <mappers>
        <mapper resource="mappers/UserMapper.xml"/>
    </mappers>
</configuration>
```

###### 4、创建mapper接口

- MyBatis中的mapper接口相当于以前的dao。但是区别在于，mapper仅仅是接口，我们不需要提供实现类

```java
package com.atguigu.mybatis.mapper;  
  
public interface UserMapper {  
	/**  
	* 添加用户信息  
	*/  
	int insertUser();  
}
```

###### 5、创建MyBatis的映射文件

- 相关概念：ORM（Object Relationship Mapping）对象关系映射。

    - 对象：Java的实体类对象
    - 关系：关系型数据库
    - 映射：二者之间的对应关系

    > | Java概念 | 数据库概念 |
    > | -------- | ---------- |
    > | 类       | 表         |
    > | 属性     | 字段/列    |
    > | 对象     | 记录/行    |

- 映射文件的命名规则
    - 表所对应的实体类的类名+Mapper.xml（与Mapper接口名字保持一致）
    - 例如：表t_user，映射的实体类为User，所对应的映射文件为UserMapper.xml
    - 因此一个映射文件对应一个实体类，对应一张表的操作
    - MyBatis映射文件用于编写SQL，访问以及操作表中的数据
    - MyBatis映射文件存放的位置是src/main/resources/mappers目录下
- MyBatis中可以面向接口操作数据，要保证两个一致
    - mapper接口的**全类名**和映射文件的**命名空间（namespace）**保持一致
    - mapper接口中**方法名**和映射文件中编写SQL的**标签的id属性**保持一致
- 最后需要在核心配置文件中，引入映射文件

- 表--实体类--mapper接口--映射文件  相对应