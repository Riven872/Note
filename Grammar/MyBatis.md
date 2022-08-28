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

###### 6、测试第一个Mybatis小案例

```java
public class MyBatisTest {
    @Test
    public void testMyBatis() throws IOException {
        //加载核心配置文件
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        //获取SqlSessionFactoryBuilder
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        //获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        //获取SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取mapper对象（底层是代理模式，返回接口对应实现类的对象）
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //测试功能
        int res = mapper.insertUser();
        //提交事务（因为事务管理器用默认的JDBC，因此需要自己手动提交事务）
        sqlSession.commit();
        System.out.println("影响行数：" + res);
    }
}
```

- 此时需要手动提交事务，如果要自动提交事务，则在获取sqlSession对象时，使用`SqlSession sqlSession = sqlSessionFactory.openSession(true);`，传入一个Boolean类型的参数，值为true，这样就可以自动提交

###### 7、加入log4j日志功能

- log4j的配置文件名为log4j.xml，存放的位置是src/main/resources目录下
- 日志的级别：FATAL(致命)>ERROR(错误)>WARN(警告)>INFO(信息)>DEBUG(调试) 从左到右打印的内容越来越详细

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
<log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/">
    <appender name="STDOUT" class="org.apache.log4j.ConsoleAppender">
        <param name="Encoding" value="UTF-8" />
        <layout class="org.apache.log4j.PatternLayout">
			<param name="ConversionPattern" value="%-5p %d{MM-dd HH:mm:ss,SSS} %m (%F:%L) \n" />
        </layout>
    </appender>
    <logger name="java.sql">
        <level value="debug" />
    </logger>
    <logger name="org.apache.ibatis">
        <level value="info" />
    </logger>
    <root>
        <level value="debug" />
        <appender-ref ref="STDOUT" />
    </root>
</log4j:configuration>
```

###### 8、查询功能注意事项

```xml
<!--
	查询语句有返回值，因此要设置返回的类型
  	resultType:设置默认的映射关系（最常使用的，表字段和实体属性一一对应）
 	resultMap:设置自定义的映射关系（表字段和实体属性不对应或一对多关系时使用）
-->
<select id="getUserById" resultType="com.edu.mybatis.User">
    select * from t_user where id = 3
<select>
```



##### 二、核心配置文件详解

- 核心配置文件中的标签必须按照固定的顺序(有的标签可以不写，但顺序一定不能乱)：
    properties、settings、typeAliases、typeHandlers、objectFactory、objectWrapperFactory、reflectorFactory、plugins、environments、databaseIdProvider、mappers

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//MyBatis.org//DTD Config 3.0//EN"
        "http://MyBatis.org/dtd/MyBatis-3-config.dtd">
<configuration>
    <!--引入properties文件，此时就可以${属性名}的方式访问属性值-->
    <properties resource="jdbc.properties"></properties>
    <settings>
        <!--将表中字段的下划线自动转换为驼峰-->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
        <!--开启延迟加载-->
        <setting name="lazyLoadingEnabled" value="true"/>
    </settings>
    <typeAliases>
        <!--
        typeAlias：设置某个具体的类型的别名
        属性：
        type：需要设置别名的类型的全类名
        alias：设置此类型的别名，且别名不区分大小写。若不设置此属性，该类型拥有默认的别名，即类名
        -->
        <!--<typeAlias type="com.atguigu.mybatis.bean.User"></typeAlias>-->
        <!--<typeAlias type="com.atguigu.mybatis.bean.User" alias="user"></typeAlias>-->
        <!--以包为单位，设置改包下所有的类型都拥有默认的别名，即类名且不区分大小写-->
        <package name="com.atguigu.mybatis.bean"/>
    </typeAliases>
    <!--
    environments：设置多个连接数据库的环境
    属性：
	    default：设置默认使用的环境的id
    -->
    <environments default="mysql_test">
        <!--
        environment：设置具体的连接数据库的环境信息
        属性：
	        id：设置环境的唯一标识，可通过environments标签中的default设置某一个环境的id，表示默认使用的环境
        -->
        <environment id="mysql_test">
            <!--
            transactionManager：设置事务管理方式
            属性：
	            type：设置事务管理方式，type="JDBC|MANAGED"
	            type="JDBC"：表示当前环境中，执行SQL时，使用的是JDBC中原生的事务管理，事务的提交或回滚需要手动处理
	            type="MANAGED"：设置事务被管理，例如spring中的AOP
            -->
            <transactionManager type="JDBC"/>
            <!--
            dataSource：设置数据源
            属性：
	            type：设置数据源的类型，type="POOLED|UNPOOLED|JNDI"（以后Spring整合时，Spring会自带数据源不用Mybatis设置）
	            type="POOLED"：使用数据库连接池，即会将创建的连接进行缓存，下次使用可以从缓存中直接获取，不需要重新创建
	            type="UNPOOLED"：不使用数据库连接池，即每次使用连接都需要重新创建
	            type="JNDI"：调用上下文中的数据源
            -->
            <dataSource type="POOLED">
                <!--设置驱动类的全类名-->
                <property name="driver" value="${jdbc.driver}"/>
                <!--设置连接数据库的连接地址-->
                <property name="url" value="${jdbc.url}"/>
                <!--设置连接数据库的用户名-->
                <property name="username" value="${jdbc.username}"/>
                <!--设置连接数据库的密码-->
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
    <!--引入映射文件-->
    <mappers>
        <!-- <mapper resource="UserMapper.xml"/> -->
        <!--
        以包为单位，将包下所有的映射文件引入核心配置文件
        注意：
			1. mapper接口和mapper映射文件必须在相同的包下
			2. mapper接口要和mapper映射文件的名字一致
        -->
        <!--
			此时，接口全路径为src下的java下的main下的com.edu.mybatis.mapper，且接口名为Usermapper.java
			那么要求在resource下建同样的包，即com.edu.mybatis.mapper，且映射文件名为Usermapper.xml
		-->
        <package name="com.edu.mybatis.mapper"/>
    </mappers>
</configuration>
```



##### 三、MyBatis获取参数值的两种方式

- MyBatis获取参数值的两种方式：${}和#{}
- ${}的本质就是字符串拼接
- #{}的本质就是占位符赋值
- ${}使用字符串拼接的方式拼接sql，若为字符串类型或日期类型的字段进行赋值时，需要手动加单引号
- #{}使用占位符赋值的方式拼接sql，此时为字符串类型或日期类型的字段进行赋值时，可以自动添加单引号

###### 1、单个字面量类型的参数

- 若mapper接口中的方法参数为单个的字面量类型，此时可以使用${}和#{}以任意的名称（最好见名识意）获取参数的值
- ${}需要手动加单引号

```xml
<!--User getUserByUsername(String username);-->
<select id="getUserByUsername" resultType="User">
	select * from t_user where username = #{username}
</select>

<!--User getUserByUsername(String username);-->
<select id="getUserByUsername" resultType="User">  
	select * from t_user where username = '${username}'  
</select>
```

###### 2、多个字面量类型的参数

- 若mapper接口中的方法参数为多个时，此时MyBatis会自动将这些参数放在一个map集合中
    - 以arg0,arg1…为键，以参数为值
    - 以param1,param2…为键，以参数为值
- 因此只需要通过${}和#{}访问map集合的键就可以获取相对应的值
- ${}需要手动加单引号
- 使用arg或者param都行，要注意的是，arg是从arg0开始的，param是从param1开始的

```xml
<!--User checkLogin(String username,String password);-->
<select id="checkLogin" resultType="User">  
	select * from t_user where username = #{arg0} and password = #{arg1}  
</select>

<!--User checkLogin(String username,String password);-->
<select id="checkLogin" resultType="User">
	select * from t_user where username = '${param1}' and password = '${param2}'
</select>
```

###### 3、map集合类型的参数

- 若mapper接口中的方法需要的参数为多个时，此时可以手动创建map集合，将这些数据放在map中只需要通过${}和#{}访问map集合的键就可以获取相对应的值
- ${}需要手动加单引号

```xml
<!--User checkLoginByMap(Map<String,Object> map);-->
<select id="checkLoginByMap" resultType="User">
	select * from t_user where username = #{username} and password = #{password}
</select>
```

```java
@Test
public void checkLoginByMap() {
	SqlSession sqlSession = SqlSessionUtils.getSqlSession();
	ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
    //手动创建Map集合，自定义键值对，在用Mybatis取的时候，可以用自定义的键去取
	Map<String,Object> map = new HashMap<>();
	map.put("usermane","admin");
	map.put("password","123456");
	User user = mapper.checkLoginByMap(map);
	System.out.println(user);
}
```

###### 4、实体类类型的参数

- 若mapper接口中的方法参数为实体类对象时此时可以使用${}和#{}，通过访问实体类对象中的属性名获取属性值
- ${}需要手动加单引号

```xml
<!--int insertUser(User user);-->
<insert id="insertUser">
	insert into t_user values(null,#{username},#{password},#{age},#{sex},#{email})
</insert>
```

```java
@Test
public void insertUser() {
	SqlSession sqlSession = SqlSessionUtils.getSqlSession();
	ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
	User user = new User(null,"Tom","123456",12,"男","123@321.com");
	mapper.insertUser(user);
}
```

###### 5、使用@Param标识参数

- 可以通过@Param注解标识mapper接口中的方法参数，此时，会将这些参数放在map集合中
    - 以@Param注解的value属性值为键，以参数为值
    - 以param1,param2…为键，以参数为值
- 只需要通过${}和#{}访问map集合的键就可以获取相对应的值
- ${}需要手动加单引号

```xml
<!--User CheckLoginByParam(@Param("username") String username, @Param("password") String password);-->
<select id="CheckLoginByParam" resultType="User">
    select * from t_user where username = #{username} and password = #{password}
</select>
```

```java
User checkLoginByParam(@Param("username") String username, @Param("password") String password);
```

```java
@Test
public void testCheckLoginByParam(){
    SqlSession sqlSession = sqlSessionUtils.getSqlSession();
    ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
    mapper.checkLoginByParam("admin", "123456");
}
```

###### 6、总结

- 分成两种情况进行处理
    - 实体类类型的参数
    - 使用@Param标识参数



##### 四、Mybatis的各种查询功能（各种返回值怎么处理）

- 如果查询出的数据只有一条，可以通过
    - 实体类对象接收
    - List集合接收
    - Map集合接收，结果{password=123456, sex=男, id=1, age=23, username=admin}
- 如果查询出的数据有多条，一定不能用实体类对象接收，会抛异常TooManyResultsException，可以通过
    - 实体类类型的LIst集合接收
    - Map类型的LIst集合接收
    - 在mapper接口的方法上添加@MapKey注解

###### 1、查询一个实体类对象

```java
/**
 * 根据用户id查询用户信息
 * @param id
 * @return
 */
User getUserById(@Param("id") int id);
```

```xml
<!--User getUserById(@Param("id") int id);-->
<select id="getUserById" resultType="User">
	select * from t_user where id = #{id}
</select>
```

###### 2、查询一个List集合

```java
/**
 * 查询所有用户信息
 * @return
 */
List<User> getUserList();
```

```xml
<!--List<User> getUserList();-->
<select id="getUserList" resultType="User">
	select * from t_user
</select>

```

###### 3、查询单个数据

```java
/**  
 * 查询用户的总记录数  
 * @return  
 * 在MyBatis中，对于Java中常用的类型都设置了类型别名  
 * 例如：java.lang.Integer-->int|integer  
 * 例如：int-->_int|_integer  
 * 例如：Map-->map,List-->list  
 */  
int getCount();
```

```xml
<!--int getCount();-->
<select id="getCount" resultType="_integer">
	select count(id) from t_user
</select>
```

###### 4、查询一条数据为map集合

```java
/**  
 * 根据用户id查询用户信息为map集合  
 * @param id  
 * @return  
 */  
Map<String, Object> getUserToMap(@Param("id") int id);
```

```xml
<!--Map<String, Object> getUserToMap(@Param("id") int id);-->
<select id="getUserToMap" resultType="map">
	select * from t_user where id = #{id}
</select>
<!--结果：{password=123456, sex=男, id=1, age=23, username=admin}-->
```

###### 5、查询多条数据为map集合

- 法一：

    ```java
    /**  
     * 查询所有用户信息为map集合  
     * @return  
     * 将表中的数据以map集合的方式查询，一条数据对应一个map；若有多条数据，就会产生多个map集合，此时可以将这些map放在一个list集合中获取  
     */  
    List<Map<String, Object>> getAllUserToMap();
    ```

    ```xml
    <!--Map<String, Object> getAllUserToMap();-->  
    <select id="getAllUserToMap" resultType="map">  
    	select * from t_user  
    </select>
    <!--
    	结果：
    	[{password=123456, sex=男, id=1, age=23, username=admin},
    	{password=123456, sex=男, id=2, age=23, username=张三},
    	{password=123456, sex=男, id=3, age=23, username=张三}]
    -->
    
    ```

- 法二：

    ```java
    /**
     * 查询所有用户信息为map集合
     * @return
     * 将表中的数据以map集合的方式查询，一条数据对应一个map；若有多条数据，就会产生多个map集合，并且最终要以一个map的方式返回数据，此时需要通过@MapKey注解设置map集合的键，值是每条数据所对应的map集合
     */
    //该注解的值为id，表明以id为唯一值，当做该Map的键
    @MapKey("id")
    Map<String, Object> getAllUserToMap();
    ```

    ```xml
    <!--Map<String, Object> getAllUserToMap();-->
    <select id="getAllUserToMap" resultType="map">
    	select * from t_user
    </select>
    <!--
    	结果：
    	{
    	1={password=123456, sex=男, id=1, age=23, username=admin},
    	2={password=123456, sex=男, id=2, age=23, username=张三},
    	3={password=123456, sex=男, id=3, age=23, username=张三}
    	}
    -->
    ```



##### 五、特殊SQL的执行

###### 1、模糊查询（like语句中的%拼接问题）

```java
/**
 * 根据用户名进行模糊查询
 * @param username 
 */
List<User> getUserByLike(@Param("username") String username);
```

```xml
<!--List<User> getUserByLike(@Param("username") String username);-->
<select id="getUserByLike" resultType="User">
	<!--select * from t_user where username like '%${mohu}%'-->  
	<!--select * from t_user where username like concat('%',#{mohu},'%')-->  
	select * from t_user where username like "%"#{mohu}"%"
</select>
```

###### 2、批量删除

- 只能使用${}，如果使用#{}，则解析后的sql语句为delete from t_user where id in ('1,2,3')，这样是将1,2,3看做是一个整体，只有id为1,2,3的数据会被删除。
- 正确的语句应该是delete from t_user where id in (1,2,3)，或者delete from t_user where id in ('1','2','3')

```java
/**
 * 根据id批量删除
 * @param ids 
 * @return int
 * @date 2022/2/26 22:06
 */
int deleteMore(@Param("ids") String ids);
```

```xml
<delete id="deleteMore">
	delete from t_user where id in (${ids})
</delete>
```

```java
//测试类
@Test
public void deleteMore() {
	SqlSession sqlSession = SqlSessionUtils.getSqlSession();
	SQLMapper mapper = sqlSession.getMapper(SQLMapper.class);
	int result = mapper.deleteMore("1,2,3,8");
	System.out.println(result);
}
```

###### 3、动态设置表名

```java
/**
 * 查询指定表中的数据
 * @param tableName 
 * @return java.util.List<com.atguigu.mybatis.pojo.User>
 * @date 2022/2/27 14:41
 */
List<User> getUserByTable(@Param("tableName") String tableName);
```

```xml
<!--List<User> getUserByTable(@Param("tableName") String tableName);-->
<select id="getUserByTable" resultType="User">
	select * from ${tableName}
</select>
```

###### 4、添加功能获取自增的主键

- 在mapper.xml中设置两个属性

    - useGeneratedKeys：设置使用自增的主键

    - keyProperty：因为增删改有统一的返回值是受影响的行数，因此只能将获取的自增的主键放在传输的参数user对象的某个属性中

```java
/**
 * 添加用户信息
 * @param user 
 * @date 2022/2/27 15:04
 */
void insertUser(User user);
```

```xml
<!--void insertUser(User user);-->
<insert id="insertUser" useGeneratedKeys="true" keyProperty="id">
	insert into t_user values (null,#{username},#{password},#{age},#{sex},#{email})
</insert>
```

```java
//测试类
@Test
public void insertUser() {
	SqlSession sqlSession = SqlSessionUtils.getSqlSession();
	SQLMapper mapper = sqlSession.getMapper(SQLMapper.class);
	User user = new User(null, "ton", "123", 23, "男", "123@321.com");
	mapper.insertUser(user);
	System.out.println(user);
	//输出：user{id=10, username='ton', password='123', age=23, sex='男', email='123@321.com'}，自增主键存放到了user的id属性中
}
```

##### 六、自定义映射resultMap

###### 1、resultMap处理字段和属性的映射关系

- resultMap：设置自定义映射
    - 属性：
        - id：表示自定义映射的唯一标识，不能重复
        - type：查询的数据要映射的实体类的类型
    - 子标签：
        - id：设置主键的映射关系
        - result：设置普通字段的映射关系
        - 子标签属性：
            - property：设置映射关系中实体类中的属性名
            - column：设置映射关系中表中的字段名

- 若字段名和实体类中的属性名不一致，则可以通过resultMap设置自定义映射，即使字段名和属性名一致的属性也要映射，也就是全部属性都要列出来

```xml
<resultMap id="empResultMap" type="Emp">
	<id property="eid" column="eid"></id>
	<result property="empName" column="emp_name"></result>
	<result property="age" column="age"></result>
	<result property="sex" column="sex"></result>
	<result property="email" column="email"></result>
</resultMap>
<!--List<Emp> getAllEmp();-->
<select id="getAllEmp" resultMap="empResultMap">
	select * from t_emp
</select>
```

- 若字段名和实体类中的属性名不一致，但是字段名符合数据库的规则（使用_），实体类中的属性名符合Java的规则（使用驼峰）。此时也可通过以下两种方式处理字段名和实体类中的属性的映射关系

    - 1、可以通过为字段起别名的方式，保证和实体类中的属性名保持一致

        ```xml
        <!--List<Emp> getAllEmp();-->
        <select id="getAllEmp" resultType="Emp">
        	select eid,emp_name empName,age,sex,email from t_emp
        </select>
        ```

    - 2、可以在MyBatis的核心配置文件中的`setting`标签中，设置一个全局配置信息mapUnderscoreToCamelCase

        ```xml
        <!--设置Mybatis的全局配置-->
        <settings>
            <!--将下划线自动映射为驼峰，如：emp_name -> empName-->
            <setting name="mapUnderscoreToCamelCase" value="true"/>
        </settings>
        ```

###### 2、多对一映射处理

- 实体类

    ```java
    public class Emp {  
    	private Integer eid;  
    	private String empName;  
    	private Integer age;  
    	private String sex;  
    	private String email;  
    	private Dept dept;
    	//...构造器、get、set方法等
    }
    ```

- 级联方式处理映射关系

    ```xml
    <resultMap id="empAndDeptResultMapOne" type="Emp">
    	<id property="eid" column="eid"></id>
    	<result property="empName" column="emp_name"></result>
    	<result property="age" column="age"></result>
    	<result property="sex" column="sex"></result>
    	<result property="email" column="email"></result>
    	<result property="dept.did" column="did"></result>
    	<result property="dept.deptName" column="dept_name"></result>
    </resultMap>
    <!--Emp getEmpAndDept(@Param("eid")Integer eid);-->
    <select id="getEmpAndDept" resultMap="empAndDeptResultMapOne">
    	select * from t_emp left join t_dept on t_emp.did = t_dept.did where t_emp.eid = #{eid}
    </select>
    ```

- 使用association处理映射关系

    - association：处理多对一的映射关系
    - property：需要处理多对一的映射关系的属性名
    - javaType：该属性的类型

    ```xml
    <resultMap id="empAndDeptResultMapTwo" type="Emp">
    	<id property="eid" column="eid"></id>
    	<result property="empName" column="emp_name"></result>
    	<result property="age" column="age"></result>
    	<result property="sex" column="sex"></result>
    	<result property="email" column="email"></result>
    	<association property="dept" javaType="Dept">
    		<id property="did" column="did"></id>
    		<result property="deptName" column="dept_name"></result>
    	</association>
    </resultMap>
    <!--Emp getEmpAndDept(@Param("eid")Integer eid);-->
    <select id="getEmpAndDept" resultMap="empAndDeptResultMapTwo">
    	select * from t_emp left join t_dept on t_emp.did = t_dept.did where t_emp.eid = #{eid}
    </select>
    ```

- 分步查询

    - select：设置分布查询的sql的唯一标识（namespace.SQLId或mapper接口的全类名.方法名）

    - column：设置分布查询的条件

        ```java
        //EmpMapper里的方法
        /**
         * 通过分步查询，员工及所对应的部门信息
         * 分步查询第一步：查询员工信息
         * @param  
         * @return com.atguigu.mybatis.pojo.Emp
         * @date 2022/2/27 20:17
         */
        Emp getEmpAndDeptByStepOne(@Param("eid") Integer eid);
        ```

        ```xml
        <resultMap id="empAndDeptByStepResultMap" type="Emp">
        	<id property="eid" column="eid"></id>
        	<result property="empName" column="emp_name"></result>
        	<result property="age" column="age"></result>
        	<result property="sex" column="sex"></result>
        	<result property="email" column="email"></result>
        	<association property="dept"
        				 select="com.atguigu.mybatis.mapper.DeptMapper.getEmpAndDeptByStepTwo"
        				 column="did"></association>
        </resultMap>
        <!--Emp getEmpAndDeptByStepOne(@Param("eid") Integer eid);-->
        <select id="getEmpAndDeptByStepOne" resultMap="empAndDeptByStepResultMap">
        	select * from t_emp where eid = #{eid}
        </select>
        ```

        ```java
        //DeptMapper里的方法
        /**
         * 通过分步查询，员工及所对应的部门信息
         * 分步查询第二步：通过did查询员工对应的部门信息
         * @param
         * @return com.atguigu.mybatis.pojo.Emp
         * @date 2022/2/27 20:23
         */
        Dept getEmpAndDeptByStepTwo(@Param("did") Integer did);
        ```

        ```xml
        <!--此处的resultMap仅是处理字段和属性的映射关系-->
        <resultMap id="EmpAndDeptByStepTwoResultMap" type="Dept">
        	<id property="did" column="did"></id>
        	<result property="deptName" column="dept_name"></result>
        </resultMap>
        <!--Dept getEmpAndDeptByStepTwo(@Param("did") Integer did);-->
        <select id="getEmpAndDeptByStepTwo" resultMap="EmpAndDeptByStepTwoResultMap">
        	select * from t_dept where did = #{did}
        </select>
        ```

- 延迟加载

    - 分布查询的优点：可以实现延迟加载，但是必须在核心配置文件中设置全局配置信息：
        - lazyLoadingEnabled：延迟加载的全局开关，当开启时，所有关联对象都会延迟加载
        - aggressiveLazyLoading：当开启时，任何方法的调用都会加载该对象的所有属性，否则，每个属性会按需加载
    - 获取的数据是什么，就只会执行相应的SQL，就是按需加载，此时可用通过association和collection中的fetchType属性设置当前的分布查询是否使用延迟加载：fetchType=“lazy(延迟加载)|eager(立即加载)”

    ```xml
    <settings>
    	<!--开启延迟加载-->
    	<setting name="lazyLoadingEnabled" value="true"/>
    </settings>
    ```

    ```java
    <resultMap id="empAndDeptByStepResultMap" type="Emp">
    	<id property="eid" column="eid"></id>
    	<result property="empName" column="emp_name"></result>
    	<result property="age" column="age"></result>
    	<result property="sex" column="sex"></result>
    	<result property="email" column="email"></result>
    	<association property="dept"
    				 select="com.atguigu.mybatis.mapper.DeptMapper.getEmpAndDeptByStepTwo"
    				 column="did"
    				 fetchType="lazy"></association>
    </resultMap>
    ```

###### 3、一对多映射处理

- 实体类

    ```java
    public class Dept {
        private Integer did;
        private String deptName;
        private List<Emp> emps;
    	//...构造器、get、set方法等
    }
    ```

- 使用collection处理一对多的映射关系

    - collection：用来处理一对多的映射关系
    - ofType：表示该属性对饮的集合中存储的数据的类型

    ```xml
    <resultMap id="DeptAndEmpResultMap" type="Dept">
    	<id property="did" column="did"></id>
    	<result property="deptName" column="dept_name"></result>
    	<collection property="emps" ofType="Emp">
    		<id property="eid" column="eid"></id>
    		<result property="empName" column="emp_name"></result>
    		<result property="age" column="age"></result>
    		<result property="sex" column="sex"></result>
    		<result property="email" column="email"></result>
    	</collection>
    </resultMap>
    <!--Dept getDeptAndEmp(@Param("did") Integer did);-->
    <select id="getDeptAndEmp" resultMap="DeptAndEmpResultMap">
    	select * from t_dept left join t_emp on t_dept.did = t_emp.did where t_dept.did = #{did}
    </select>
    ```

- 分步查询

    - select：设置分布查询的sql的唯一标识（namespace.SQLId或mapper接口的全类名.方法名）
    - column：设置分步查询的条件



##### 七、动态SQL

###### 1、if

- if标签可通过test属性（即传递过来的数据）的表达式进行判断，若表达式的结果为true，则标签中的内容会执行；反之标签中的内容不会执行

    ```xml
    <!--List<Emp> getEmpByCondition(Emp emp);-->
    <select id="getEmpByCondition" resultType="Emp">
    	select * from t_emp where 1=1
    	<if test="empName != null and empName !=''">
    		and emp_name = #{empName}
    	</if>
    	<if test="age != null and age !=''">
    		and age = #{age}
    	</if>
    	<if test="sex != null and sex !=''">
    		and sex = #{sex}
    	</if>
    	<if test="email != null and email !=''">
    		and email = #{email}
    	</if>
    </select>
    ```

###### 2、where

- where和if一般结合使用：

    - 若where标签中的if条件都不满足，则where标签没有任何功能，即不会添加where关键字
    - 若where标签中的if条件满足，则where标签会自动添加where关键字，并将条件最前方多余的and/or去掉

    ```xml
    <!--List<Emp> getEmpByCondition(Emp emp);-->
    <select id="getEmpByCondition" resultType="Emp">
    	select * from t_emp
    	<where>
    		<if test="empName != null and empName !=''">
    			emp_name = #{empName}
    		</if>
    		<if test="age != null and age !=''">
    			and age = #{age}
    		</if>
    		<if test="sex != null and sex !=''">
    			and sex = #{sex}
    		</if>
    		<if test="email != null and email !=''">
    			and email = #{email}
    		</if>
    	</where>
    </select>
    ```

- 注意：where标签不能去掉条件后多余的and/or

    ```xml
    <!--这种用法是错误的，只能去掉条件前面的and/or，条件后面的不行-->
    <if test="empName != null and empName !=''">
    emp_name = #{empName} and
    </if>
    <if test="age != null and age !=''">
    	age = #{age}
    </if>
    ```

###### 3、trim

- trim用于去掉或添加标签中的内容

    - prefix：在trim标签中的内容的前面添加某些内容
    - suffix：在trim标签中的内容的后面添加某些内容
    - prefixOverrides：在trim标签中的内容的前面去掉某些内容
    - suffixOverrides：在trim标签中的内容的后面去掉某些内容

- 若trim中的标签都不满足条件，则trim标签没有任何效果

    ```xml
    <!--List<Emp> getEmpByCondition(Emp emp);-->
    <select id="getEmpByCondition" resultType="Emp">
    	select * from t_emp
    	<trim prefix="where" suffixOverrides="and|or">
    		<if test="empName != null and empName !=''">
    			emp_name = #{empName} and
    		</if>
    		<if test="age != null and age !=''">
    			age = #{age} and
    		</if>
    		<if test="sex != null and sex !=''">
    			sex = #{sex} or
    		</if>
    		<if test="email != null and email !=''">
    			email = #{email}
    		</if>
    	</trim>
    </select>
    ```

###### 4、choose、when、otherwise

- `choose、when、otherwise`相当于`if...else if..else`

- 当一个when成立时，下面的when将不会执行，当所有的when都不成立时，则执行otherwise（相当于最后的else）

- when至少要有一个，otherwise至多只有一个

    ```xml
    <select id="getEmpByChoose" resultType="Emp">
    	select * from t_emp
    	<where>
    		<choose>
    			<when test="empName != null and empName != ''">
    				emp_name = #{empName}
    			</when>
    			<when test="age != null and age != ''">
    				age = #{age}
    			</when>
    			<when test="sex != null and sex != ''">
    				sex = #{sex}
    			</when>
    			<when test="email != null and email != ''">
    				email = #{email}
    			</when>
    			<otherwise>
    				did = 1
    			</otherwise>
    		</choose>
    	</where>
    </select>
    ```

###### 5、foreach

- 属性：

    - collection：设置要循环的数组或集合
    - item：表示集合或数组中的每一个数据
    - separator：设置循环体之间的分隔符，分隔符前后默认有一个空格，如`,`
    - open：设置foreach标签中的内容的开始符
    - close：设置foreach标签中的内容的结束符

- 批量删除

    ```xml
    <!--int deleteMoreByArray(Integer[] eids);-->
    <delete id="deleteMoreByArray">
    	delete from t_emp where eid in
    	<foreach collection="eids" item="eid" separator="," open="(" close=")">
    		#{eid}
    	</foreach>
    </delete>
    ```

- 批量添加

    ```xml
    <!--int insertMoreByList(@Param("emps") List<Emp> emps);-->
    <insert id="insertMoreByList">
    	insert into t_emp values
    	<foreach collection="emps" item="emp" separator=",">
    		(null,#{emp.empName},#{emp.age},#{emp.sex},#{emp.email},null)
    	</foreach>
    </insert>
    ```

###### 6、SQL片段

- sql片段，可以记录一段公共sql片段，在使用的地方通过include标签进行引入

- 声明sql片段：`<sql>`标签

    ```xml
    <sql id="empColumns">eid,emp_name,age,sex,email</sql>
    ```

- 引用sql片段：`<include>`标签

    ```xml
    <!--List<Emp> getEmpByCondition(Emp emp);-->
    <select id="getEmpByCondition" resultType="Emp">
    	select <include refid="empColumns"></include> from t_emp
    </select>
    ```





##### 八、MyBatis的缓存

###### 1、MyBatis的一级缓存

- 一级缓存是SqlSession级别的，通过同一个SqlSession查询的数据会被缓存，下次查询相同的数据，就会从缓存中直接获取，不会从数据库重新访问
- 使一级缓存失效的四种情况：
    - 不同的SqlSession对应不同的一级缓存
    - 同一个SqlSession但是查询条件不同
    - 同一个SqlSession两次查询期间执行了任何一次增删改操作
    - 同一个SqlSession两次查询期间手动清空了缓存

###### 2、MyBatis的二级缓存

- 二级缓存是SqlSessionFactory级别，通过同一个SqlSessionFactory创建的SqlSession查询的结果会被缓存；此后若再次执行相同的查询语句，结果就会从缓存中获取
- 二级缓存开启的条件
    - 在核心配置文件中，设置全局配置属性cacheEnabled=“true”，默认为true，不需要设置
    - 在映射文件中设置标签（只需一个`<cache />`即可）
    - 二级缓存必须在SqlSession关闭或提交之后有效
    - 查询的数据所转换的实体类类型必须实现序列化的接口

- 使二级缓存失效的情况：两次查询之间执行了任意的增删改，会使一级和二级缓存同时失效

###### 3、二级缓存的相关配置
