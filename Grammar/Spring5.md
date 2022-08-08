##### 概述

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

##### IOC容器

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

###### IOC操作Bean管理基于XML方式

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

- DI：依赖注入，就是注入属性

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

    - p名称空间注入（了解有这么个注入方式就完了，几乎不用，底层用到set方法的注入）

    - XML注入其他类型的属性

        - Null值

            ```xml
            <!-- 给属性设置空值，因为用的是property标签，因此要求该字段有对应的set方法-->
            <property name="price">
                <null/>
            </property>
            ```

        - 特殊符号

            ```xml
            <!-- 给属性值设置特殊字符，将特殊字符写到CDATA中-->
            <property name="author">
                <value><![CDATA[<<教员>>]]></value>
            </property>
            ```

- 注入属性-外部bean（只是换了种写法，也可以用内部bean实现）

    - 1、例如在UserService类中，定义了UserDao类型的属性，需要持有UserDao对象

    - 2、在Spring配置文件中

        ```xml
        <!--创建userService对象-->
        <bean id="userService" class="com.edu.IOC02.service.UserService">
            <!--使用ref属性，创建userDao对象bean标签id值-->
            <property name="userDao" ref="userDaoImpl"></property>
        </bean>
        
        <!--创建userDaoImpl对象-->
        <!--接口不能实例化，因此class内写该接口的实现类-->
        <bean id="userDaoImpl" class="com.edu.IOC02.dao.UserDaoImpl"></bean>
        ```

- 注入属性-内部bean（只是换了种写法，也可以用外部bean实现）

    - 在实体类之间表示一对多关系，比如部门：员工 = 1：N

    - 部门类

        ```java
        public class Dept {
            private String name;
            //此处省略set方法
        }
        ```

    - 员工类

      ```java
      public class Emp {
          private String name;
          private String gender;
          private Dept dept;//表明员工属于哪个部门
          //此处省略set方法
      }
      ```

    - Spring配置文件

      ```xml
      <!--内部bean-->
      <bean id="emp" class="com.edu.bean.Emp">
          <!--设置两个普通属性-->
          <property name="name" value="tom"></property>
          <property name="gender" value="男"></property>
          <!--设置对象类型属性-->
          <property name="dept">
              <bean id="dept" class="com.edu.bean.Dept">
                  <property name="name" value="研发部"></property>
              </bean>
          </property>
      </bean>
      <!--也可以使用property标签的ref属性，在外部进行bean创建后，通过ref引入-->
      ```

- 注入属性-级联赋值（就是通过外部bean赋值，创建完bean之后多加了property标签）

    - 方法一：

        ```xml
        <!--级联bean-->
        <bean id="emp" class="com.edu.bean.Emp">
            <!--设置两个普通属性-->
            <property name="name" value="tom"></property>
            <property name="gender" value="男"></property>
            <!--设置对象类型属性-->
            <property name="dept" ref="dept"></property>
        </bean>
        <bean id="dept" class="com.edu.bean.Dept">
            <!--给级联bean内的属性赋值-->
            <property name="name" value="产品部"></property>
        </bean>
        ```

    - 方法二：

        ```xml
        <!--级联bean-->
        <bean id="emp" class="com.edu.bean.Emp">
            <!--设置两个普通属性-->
            <property name="name" value="tom"></property>
            <property name="gender" value="男"></property>
            <!--设置对象类型属性-->
            <property name="dept" ref="dept"></property>
            <!--要求emp类中的要有dept的get方法-->
            <property name="dept.name" value="产品部"></property>
        </bean>
        <bean id="dept" class="com.edu.bean.Dept"></bean>
        ```

- 注入属性-集合类型（数组、List、Set、Map）

    - 类中定义集合类型的属性

        ```java
        //省略了每个属性的set方法
        public class Stu {
            //数组类型属性
            private String[] array;
        
            //List集合类型属性
            private List<String> list;
        
            //Map集合类型属性
            private Map<String, String> map;
        
            //Set集合类型属性
            private Set<String> set;
        }
        ```

    - Spring配置文件

        ```xml
        <!--创建对象-->
        <bean id="stu" class="com.edu.IOC03.Stu">
            <!--数组类型属性注入-->
            <property name="array">
                <array>
                    <value>数据结构</value>
                    <value>操作系统</value>
                    <value>计算机网络</value>
                    <value>计算机组成原理</value>
                </array>
            </property>
        
            <!--List类型属性注入-->
            <property name="list">
                <list>
                    <value>链表</value>
                    <value>树</value>
                    <value>图</value>
                </list>
            </property>
        
            <!--Set类型属性注入-->
            <property name="set">
                <set>
                    <value>MySQL</value>
                    <value>Redis</value>
                </set>
            </property>
        
            <!--Map类型属性注入-->
            <property name="map">
                <map>
                    <entry key="顺序表" value="队列"></entry>
                    <entry key="树" value="二叉树"></entry>
                    <entry key="图" value="有向无环强连通图"></entry>
                </map>
            </property>
        </bean>
        ```

    - 在集合里面设置对象类型的值

        - 类中定义对象集合类型属性

            ```java
            //省略了该属性的set方法
            public class Stu {
                //对象集合类型属性
                private List<Course> courses;
            }
            ```

        - Spring配置文件

            ```xml
            <!--创建对象-->
            <bean id="stu" class="com.edu.IOC03.Stu">
                <!--List类型属性注入，值是对象类型-->
                <property name="courses">
                    <list>
                        <!--使用ref标签添加对象-->
                        <ref bean="course1"></ref>
                        <ref bean="course2"></ref>
                    </list>
                </property>
            </bean>
            
            <!--创建多个course对象-->
            <bean id="course1" class="com.edu.IOC03.Course">
                <property name="name" value="Spring5框架"></property>
            </bean>
            <bean id="course2" class="com.edu.IOC03.Course">
                <property name="name" value="SpringMVC"></property>
            </bean>
            ```

    - 把集合注入部分提取出来

        - 在Spring配置文件中引入名称空间Util

            ```xml
            <beans xmlns="http://www.springframework.org/schema/beans"
                   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xmlns:util="http://www.springframework.org/schema/util"
                   xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                                       http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">
            ```

        - 使用util标签完成List集合注入提取

            ```xml
            <?xml version="1.0" encoding="UTF-8"?>
            <beans xmlns="http://www.springframework.org/schema/beans"
                   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xmlns:util="http://www.springframework.org/schema/util"
                   xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                                       http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">
            
                <!--提取List集合类型属性-->
                <!--也有util:map等标签，此处以list为例-->
                <util:list id="bookList">
                    <!--现在演示的是提取字符串，以value为例，如果是对象，可以用ref，参考以前的写法-->
                    <value>测试1</value>
                    <value>测试2</value>
                    <value>测试3</value>
                    <value>测试4</value>
                </util:list>
            
                <!--将提取的部分进行注入到Stu类的List属性中-->
                <!--先创建stu类-->
                <bean id="stu" class="com.edu.IOC03.Stu">
                    <!--将提取出的部分（公共部分）用ref属性获取-->
                    <property name="list" ref="bookList"></property>
                </bean>
            </beans>
            ```

- IOC操作Bean管理中的Bean分类

    - 1、普通Bean，如下class中写的是Stu，则返回的实例也是Stu（配置文件中定义Bean类型就是返回类型）

        ```xml
        <bean id="stu" class="com.edu.IOC03.Stu">
        ```

    - 2、工厂Bean（在配置文件中定义Bean类型可以和返回类型不一样）

        - 1、创建类，让这个类作为工厂Bean，实现接口FactoryBean

        - 2、实现接口里的方法，在实现的方法中定义返回的Bean类型

            ```java
            //泛型中注明的是要返回的实例类型
            public class MyBean implements FactoryBean<Emp> {
                //该方法的返回值跟类声明的泛型一致，表明返回的类型
                @Override
                public Emp getObject() throws Exception {
                    //通过new的方式来返回实例类型
                    return new Emp();
                }
            
                @Override
                public Class<?> getObjectType() {
                    return null;
                }
            
                @Override
                public boolean isSingleton() {
                    return false;
                }
            }
            ```

            ```xml
            <?xml version="1.0" encoding="UTF-8"?>
            <beans xmlns="http://www.springframework.org/schema/beans"
                   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
            
            	<!--配置的是MyBean，但是返回的不是-->    
                <bean id="myBean" class="com.edu.IOC4.FactoryBean.MyBean"></bean>
            
            </beans>
            ```

            ```java
            public class test {
                @Test
                public void test1() {
                    //读取配置文件
                    ApplicationContext context = new ClassPathXmlApplicationContext("MyBean.xml");
                    //读取配置文件中设置的id的值，但返回的实例类型可以不是配置里的
                    Emp emp = context.getBean("myBean", Emp.class);
                    System.out.println(emp);
                }
            }
            ```

- IOC操作Bean管理中Bean的作用域

    - 1、在Spring中，可以设置创建Bean实例是单实例还是多实例

    - 2、在Spring中，默认情况下，Bean是单实例

    - 3、如何设置多实例还是单实例

        ```xml
        <!--使用bean标签中scope属性，如果不写的话默认是scope="singleton"，即单实例-->
        <bean id="cat" class="com.edu.IOC4.Cat" scope="prototype"></bean>
        ```

        ```java
        Cat cat1 = context.getBean("cat", Cat.class);
        Cat cat2 = context.getBean("cat", Cat.class);
        //对比二者的HashCode不同，即地址不同
        //728739494
        //2005733474
        ```

    - 4、`scope="singleton"`和`scope="prototype"`的区别：

        - 1、singleton：加载Spring配置文件时就会创建单实例对象
        - 2、prototype：不是在加载Spring配置文件时候创建对象，在调用getBean方法时创建多实例对象

- IOC操作Bean管理中Bean的生命周期

  - 1、通过构造器创建Bean实例（无参数构造）

  - 2、为Bean的属性设置值和对其他Bean引用（调用Set方法）

  - 3、把Bean实例传递Bean后置处理器

  - 4、调用Bean的初始化的方法（需要进行配置初始化的方法）

    ```xml
    <!--在类中定义一个方法，默认是不会执行，但用init-method属性指定后，在bean初始化时会自动进行调用-->
    <bean id="myBean" class="com.edu.IOC4.FactoryBean.MyBean" init-method="foo"></bean>
    ```

  - 5、把Bean实例传递Bean后置处理器

  - 6、Bean可以使用了（对象获取到了）

  - 7、当容器关闭的时候，调用Bean的销毁的方法（需要进行配置销毁的方法）

      ```xml
      <!--在类中定义一个方法，默认是不会执行，但用destroy-method属性指定后，在bean调用close方法销毁时会自动进行调用-->
      <bean id="myBean" class="com.edu.IOC4.FactoryBean.MyBean" init-method="foo" destroy-method="bar"></bean>
      ```

      ```java
      //ApplicationContext没有close的方法，因此使用ClassPathXmlApplicationContext来读取bean
      ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("MyBean.xml");
      Cat cat1 = context.getBean("cat", Cat.class);
      //当调用销毁方法时，
      context.close();
      ```

  - 注：配置后置处理器

      ```xml
      <?xml version="1.0" encoding="UTF-8"?>
      <beans xmlns="http://www.springframework.org/schema/beans"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
          
          <!--配置后置处理器，适用于所有的Bean-->
          <bean id="myBeanPost" class="com.edu.IOC04.MyBeanPost"></bean>
      </beans>
      ```

      ```java
      //实现BeanPostProcessor接口
      public class MyBeanPost implements BeanPostProcessor {
          //该方法表示初始化之前调用
          @Override
          public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
              return null;
          }
      
          //该方法表示初始化之后调用
          @Override
          public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
              return null;
          }
      }
      ```

- IOC操作Bean管理中的XML自动装配

    - 1、根据指定装配规则（属性名称或者属性类型），Spring自动将匹配的属性值进行注入

        ```xml
        <!--实现自动装配
            bean标签属性autowire，配置自动装配
            autowire属性常用的两个值：
                byName根据属性名称注入，注入值bean的id值和类属性名称一样
                byType根据属性类型注入
        -->
        ```

    - 2、根据名称自动装配

        ```xml
        <!--Emp类中，有 private Dept dept; 因此通过byName，会找到id值和类属性名称一样的bean，进行自动装配-->
        <bean id="emp" class="com.edu.bean.Emp" autowire="byName"></bean>
        <bean id="dept" class="com.edu.bean.Dept"></bean>
        ```

    - 3、根据类型自动装配

        ```xml
        <!--Emp类中，有 private Dept dept; 因此通过byType，会找到class类型和类属性定义类型一样的bean，进行自动装配-->
        <bean id="emp" class="com.edu.bean.Emp" autowire="byType"></bean>
        <bean id="dept1" class="com.edu.bean.Dept"></bean>
        <!--但是如果有两个同一类型的Bean，会报错，byType只能找一个类型相匹配的Bean-->
        <bean id="dept2" class="com.edu.bean.Dept"></bean>
        ```

- IOC操作Bean管理的引入外部属性文件

    - 1、场景举例：外部写一个数据库连接池的Properties的文件，然后在Spring配置中引入

    - 2、创建数据库连接池Properties文件

        ```properties
        prop.driverClass=com.mysql.jdbc.Driver
        prop.url=jdbc:mysql://localhost:3306/userDb
        prop.userName=root
        prop.passWord=123
        ```

    - 3、把外部的properties属性文件引入到Spring配置文件中（使用context命名空间）

        ```xml
        <!--引入外部文件-->
        <context:property-placeholder location="JDBC.properties"></context:property-placeholder>
        
        <!--配置连接池，value的值通过读取配置文件来获得，而不是写成固定的值，
          用${key}来获取配置文件中对应的value值-->
        <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
            <property name="driverClassName" value="${prop.driverClass}"></property>
            <property name="url" value="${prop.url}"></property>
            <property name="username" value="${prop.userName}"></property>
            <property name="password" value="${prop.passWord}"></property>
        </bean>
        ```


###### IOC操作Bean管理基于注解方式

- Spring针对Bean管理中创建对象提供的注解

  - 1、`@Component`，普通注解，都适用
  - 2、`@Service`，一般用在业务逻辑层或Service层中
  - 3、`@Controller`，一般用在web层中
  - 4、`@Repository`，一般用在持久层或DAO层中
  - 注：四个注解功能一直，都可以用来创建Bean实例，只是为了简化开发而分层使用

- 基于注解方式实现对象的创建

  - 1、引入`aop`依赖

  - 2、开启组件扫描，来通知Spring扫描到哪个包的类中，有创建对象的注解

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                               http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    
        <!--开启组件扫描，如果要扫描多个包，可以用逗号隔开或者扫描包的上级目录-->
        <context:component-scan base-package="com.edu.IOC05"></context:component-scan>
    
    </beans>
    ```

    ```java
    //其中value值可以不写，如果不写，默认值就是类名称且首字母小写
    @Component(value = "userService") //效果跟<bean id="userService" class="..."></bean>一样
    public class UserService {
        public void add(){
            System.out.println("我的UserService类的add方法");
        }
    }
    ```

  - 3、创建Bean对象

    ```java
    @Test
    public void test1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.add();
    }
    ```

  - 注：组件扫描的细节

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                               http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    
        <!--开启组件扫描，如果要扫描多个包，可以用逗号隔开或者扫描包的上级目录-->
        <context:component-scan base-package="com.edu.IOC05"></context:component-scan>
    
        <!--use-default-filters="false"表示现在不使用默认filter，自己配置filter-->
        <context:component-scan base-package="com.edu.IOC05" use-default-filters="false">
            <!--include-filter表示扫描哪些内容，annotation表示扫描注解，Controller表示只扫描Controller注解-->
            <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
        </context:component-scan>
    
        <context:component-scan base-package="com.edu.IOC05" use-default-filters="false">
            <!--exclude-filter相反，表示过滤哪些内容，表示Repository注解不进行扫描-->
            <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Repository"/>
        </context:component-scan>
    
    </beans>
    ```

- 基于注解方式实现属性的注入

  - 1、`@Autowired`，根据属性类型进行自动装配

    ```java
    @Service
    public class UserService {
        //不需要有Set方法，因为注解底层自带
        //根据类型装配，因此会去找UserDaoImpl对象注解
        @Autowired
        private UserDaoImpl userDao;
    
        public void add(){
            System.out.println("UserService类的add方法");
            userDao.update();
        }
    }
    ```

  - 2、`@Qualifier`，根据属性名称进行注入

    ```java
    //需要跟@Autowired一起来使用，当类型有多个时，Autowired就不清楚该用哪个注入，因此用Qualifier来根据名称确定要注入的对象的名称
    @Service
    public class UserService {
        @Autowired
        @Qualifier(value = "userServiceFoo")
        private UserDaoImpl userDao;
    
        public void add(){
            System.out.println("UserService类的add方法");
            userDao.update();
        }
    }
    
    @Repository(value = "userServiceFoo")
    public class UserDaoImpl implements IUserDao {
        @Override
        public void update() {
            System.out.println("调用UserDao类中的update方法");
        }
    }
    ```
  
  - 3、`@Resource`，可以根据类型注入，也可以根据名称注入
  
    注：`Resource`是`javax`包中的，Spring官方推荐用自己包里的`Autowired`或者是`Qualifier`
  
    ```java
    @Service
    public class UserService {
        //@Resource 根据类型进行注入
        //@Resource(name = "userServiceFoo") 根据名称进行注入
        private UserDaoImpl userDao;
    
        public void add(){
            System.out.println("UserService类的add方法");
            userDao.update();
        }
    }
    ```
  
  - 4、`@value`，注入普通类型属性
  
    ```java
    @Service
    public class UserService {
        //name的注入值就是foobar
        @Value(value = "foobar")
        private String name;
    
        public void add(){
            System.out.println(this.name);
        }
    }
    ```
  
  - 注：前3个注入的是对象类型，第4个针对的是普通类型的属性

- 完全注解开发

  - 1、创建配置类，替代XML配置文件

    ```java
    package com.config;
    
    import org.springframework.context.annotation.ComponentScan;
    import org.springframework.context.annotation.Configuration;
    
    @Configuration  //作为配置类，替代XML配置文件
    @ComponentScan(basePackages = {"com.edu.IOC05"})  //相当于配置文件中开启的组件扫描
    public class SpringConfig {
        
    }
    ```

  - 2、加载配置类并使用

    ```java
    public void test3() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);//加载配置类
        UserService userService = context.getBean("userService", UserService.class);
        userService.add();
    }
    ```


##### AOP

- 什么是AOP
    - 1、面向切面编程（方面），利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重要性，同时提高了开发的效率
    - 2、通俗描述：不通过修改源代码方式，在主干功能里面添加新功能

- AOP底层原理

    - AOP底层使用动态代理

        - 1、有接口的情况，使用JDK动态代理（创建接口实现类的代理对象，增强类的方法）

            ```java
            interface UserDao {
                public void login();
            }
            
            Class UserDaoImpl implements UserDao {
                public void login() {
                    //实现功能
                }
            }
            
            //使用JDK动态代理，创建UserDao接口实现类的代理对象
            //该代理对象有实现类的所有功能以及新增的功能
            实现过程...
            ```

        - 2、没有接口的情况，使用CGLIB动态代理（创建子类的代理对象，增强类的方法）

            ```java
            Class User {
                public void add(){
                    ...
                }
            }
            
            Class Person extends User {
                public void add() {
                    super.add();
                    //增强逻辑
                }
            }
            
            //使用CGLIB动态代理，创建当前子类的代理对象
            ```

- JDK动态代理

    - 1、使用`Proxy`类里面的方法创建代理对象

    - 2、调用`newProxyInstance`方法

        - 参数1：类加载器
        - 参数2：增强方法所在的类，这个类实现的接口，支持多个接口
        - 参数3：实现这个接口`InvocationHandler`，创建代理对象，写增强的部分

    - 3、JDK动态代理代码

        - 1、创建接口，定义方法

            ```java
            public interface IUserDao {
                int add(int a, int b);
            
                void update(String id);
            }
            ```

        - 2、创建接口实现类，实现方法

            ```java
            public class UserDaoImpl implements IUserDao{
                @Override
                public int add(int a, int b) {
                    return a + b;
                }
            
                @Override
                public String update(String id) {
                    return id;
                }
            }
            ```

        - 3、使用Proxy类创建接口代理对象实现增强的过程

            ```java
            //JDK动态代理
            public class JDKProxy {
                public static void main(String[] args) {
                    //接口的集合
                    Class[] interfaces = {IUserDao.class};
                    //UserDaoProxy默认是有参的构造函数，因此实例化一个实现类，传递动态代理的对象（代理谁就传谁）
                    UserDaoImpl userDao = new UserDaoImpl();
                    //返回代理对象
                    IUserDao dao = (IUserDao) Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new UserDaoProxy(userDao));
                    //调用方法
                    System.out.println(dao.add(1, 2));
                }
            }
            
            //参数3：实现这个接口InvocationHandler，创建代理对象，写增强的部分
            //创建代理对象代码
            public class UserDaoProxy implements InvocationHandler {
                //创建的是谁的代理对象，就把谁传过来
                //使用有参构造器
            
                private Object obj;
            
                //为了通用性，因此传入Object类型
                public UserDaoProxy(Object obj){
                    this.obj = obj;
                }
            
                /**
                 * 增强的逻辑
                 * @param proxy 代理对象
                 * @param method 被增强的方法，
                 *               可以根据method.getName()判断当前调用的是哪个方法，进而判断是否增强此方法
                 * @param args 参数
                 * @return
                 * @throws Throwable
                 */
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    //被增强的方法之前
                    System.out.println("被增强的方法之前执行" + method.getName() + " 传递的参数" + Arrays.toString(args));
                    //被增强的方法
                    Object res = method.invoke(obj, args);
                    //被增强的方法之后
                    System.out.println("被增强的方法之后执行" + obj);
            
                    return res;
                }
            }
            ```

- AOP术语

    - 1、连接点：类中的哪些方法可以被增强，那么这些方法就被称为连接点（理论上可以增强哪些）
    - 2、切入点：实际被增强的方法称为切入点（实际应用中增强了哪些）
    - 3、通知（增强）：实际增强的逻辑的部分称为通知（增强）
        - 通知的多种类型：前置通知、后置通知、环绕通知、异常通知、最终通知
    - 4、切面：把通知应用到切入点的过程（指的是一个动作）

- AOP操作（准备工作）

    - 1、Spring框架一般是基于AspectJ实现AOP操作
        - AspectJ不是Spring的组成部分，是一个独立的AOP框架，一般把AspectJ和Spring一起使用，进行AOP操作
    - 2、基于AspectJ实现AOP操作
        - 1、基于XML配置文件实现
        - 2、基于注解方式实现（使用）
    - 3、在项目工程中引入AOP相关的依赖
    - 4、切入点表达式
      
        - 1、作用：知道对哪个类里面的哪个方法进行增强
        
        - 2、语法结构：execution(\[权限修饰符]\[返回类型]\[类全路径]\[方法名称]\[参数列表])
        
            ```java
            //对com.edu.AOP.BookDao类里面的add方法进行增强，其中*表示可以是public或者是private，返回类型可以省略，两个..表示参数列表
            execution(* com.edu.AOP.BookDao.add(..))
            //对com.edu.AOP.BookDao类里面的所有方法进行增强
            execution(* com.edu.AOP.BookDao.*(..))
            //对com.edu.AOP包里的所有类，类里面的所有方法进行增强
            execution(* com.edu.AOP.*.*(..))    
            ```
        
    
- AOP操作（AspectJ注解）

    - 1、创建类，在类里面定义方法

        ```java
        //被增强的类
        public class User {
            public void add() {
                System.out.println("User类中的add方法");
            }
        }
        ```

    - 2、创建增强类（编写增强逻辑）

        ```java
        //增强的方法
        public class UserProxy {
            //前置通知
            public void before() {
                System.out.println("Before...");
            }
        }
        ```

    - 3、进行通知的配置

        - 1、在Spring配置文件中，开启注解扫描

            ```xml
            <?xml version="1.0" encoding="UTF-8"?>
            <beans xmlns="http://www.springframework.org/schema/beans"
                   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xmlns:context="http://www.springframework.org/schema/context"
                   xmlns:aop="http://www.springframework.org/schema/aop"
                   xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                                       http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                                       http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
            
                <!--开启注解扫描-->
                <context:component-scan base-package="com.edu.AOP2"></context:component-scan>
            
            </beans>
            ```

        - 2、使用注解创建User和UserProxy对象

            ```java
            @Component
            //被增强的类
            public class User{
                
            }
            
            @Component
            //增强的类
            public class UserProxy {
                
            }
            ```

        - 3、在增强类上面添加注解`@Aspect`

            ```java
            @Component
            @Aspect //生成代理对象
            //增强的类
            public class UserProxy {
            
            }
            ```

        - 4、在Spring配置文件中开启生成代理对象

            ```xml
            <?xml version="1.0" encoding="UTF-8"?>
            <beans xmlns="http://www.springframework.org/schema/beans"
                   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xmlns:context="http://www.springframework.org/schema/context"
                   xmlns:aop="http://www.springframework.org/schema/aop"
                   xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                                       http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                                       http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
            
                <!--开启注解扫描-->
                <context:component-scan base-package="com.edu.AOP2"></context:component-scan>
            
                <!--开始Aspect生成代理对象
            		作用是把有@AspectJ注解的类生成代理对象
              	-->
                <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
            
            </beans>
            ```

    - 4、配置不同类型的通知

        - 1、在增强类中，在作为通知方法上面添加通知类型注解，使用切入点表达式配置（以前置通知为例）

            ```java
            //增强的类
            @Component
            @Aspect //生成代理对象
            public class UserProxy {
                //前置通知
                //@Before表示前置通知，表示增强的是哪个包下的哪个类下的哪个方法
                @Before(value = "execution(* com.edu.AOP2.User.add(..))")
                public void before() {
                    System.out.println("Before...");
                }
            }
            ```

        - 2、前置通知（`@Before`）、后置通知（`@AfterReturning`）、最终通知（`@After`）、环绕通知（`@Around`）、异常通知（`@AfterThrowing`）

    - 5、细节问题

        - 1、提取相同的切入点

            ```java
            @Component
            @Aspect //生成代理对象
            //增强的类
            public class UserProxy {
                //抽取相同的切入点，并注解在一个方法上
                @Pointcut(value = "execution(* com.edu.AOP2.User.add(..))")
                public void pointDemo() {
            
                }
            
                //前置通知
                //@Before表示前置通知，表示增强的是哪个包下的哪个类下的哪个方法
                @Before(value = "pointDemo()")
                public void before() {
                    System.out.println("Before...");
                }
            }
            ```

            - 2、有多个增强类对同一个方法进行增强，设置增强类的优先级

            ```java
            //在增强类上加注解@order(整数)，参数越小，优先级越高
            @Component
            @Aspect
            @Order(3)
            public class PersonProxy {
                @Before(value = "execution(* com.edu.AOP2.User.add(..))")
                public void before() {
                    System.out.println("Person创建");
                }
            }
            ```

    - 6、完全注解开发
    
        ```java
        @Configuration //作为配置类，替代XML配置文件
        @ComponentScan(basePackages = {"com.edu.AOP2"}) //相当于配置文件中开启的组件扫描
        @EnableAspectJAutoProxy(proxyTargetClass = true) //开始Aspect生成代理对象
        public class config {
            
        }
        ```
    
- AOP操作（AspectJ配置文件）：不做说明，了解即可

##### JDBCTemplate

- 什么是JDBCTemplate

    - Spring框架对JDBC进行封装，使用JDBCTemplate方便实现对数据库的操作

- 准备工作

    - 1、引入依赖：Druid连接池、MysqlConnector、SpringJDBC、SpringTX、SpringORM

    - 2、在Spring配置文件中配置数据库连接池

        ```xml
        <?xml version="1.0" encoding="UTF-8"?>
        <beans xmlns="http://www.springframework.org/schema/beans"
               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
        
            <!--数据库连接池-->
            <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" destroy-method="close">
                <property name="url" value="jdbc:mysql:///edudb01"/>
                <property name="username" value="root"/>
                <property name="password" value="123"/>
                <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
            </bean>
        
        </beans>
        ```

    - 3、配置JDBCTemplate对象，注入DataSource

        ```xml
        <?xml version="1.0" encoding="UTF-8"?>
        <beans xmlns="http://www.springframework.org/schema/beans"
               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
        
            <!--数据库连接池-->
            <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" destroy-method="close">
                <property name="url" value="jdbc:mysql:///edudb01"/>
                <property name="username" value="root"/>
                <property name="password" value="123"/>
                <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
            </bean>
        
            <!--创建JdbcTemplate对象，注入DataSource-->
            <bean id="JdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
                <!--注入数据库连接池对象-->
                <property name="dataSource" ref="dataSource"></property>
            </bean>
        
        </beans>
        ```

    - 4、创建Service类和Dao类，在Dao中注入JdbcTemplate对象

- jdbctemplate操作数据库——添加

    - 1、对应数据库创建实体类

    - 2、编写Service和Dao

        - 1、在Dao进行数据库添加的操作

        - 2、调用JdbcTemplate对象里面的Update方法实现添加操作

            ```java
            @Repository
            public class BookDaoImp implements IBookDao {
            
                //注入JDBCTemplate
                @Autowired
                private JdbcTemplate jdbcTemplate;
            
                @Override
                public void add(Book book) {
                    //1、创建sql语句
                    String sql = "INSERT INTO book VALUES(?,?,?)";
                    //2、调用方法实现
                    Object[] args = {book.getBookId(), book.getBookName(), book.getBookStatus()};
                    int updateNum = jdbcTemplate.update(sql, args);
            
                    System.out.println(updateNum);
                }
            }
            ```

        - 3、测试类

            ```java
            public void test() {
                ApplicationContext context = new ClassPathXmlApplicationContext("MysqlConnector.xml");
                BookService service = context.getBean("bookService", BookService.class);
            
                //book对象在实际应用中，应该是页面上传过来的
                Book book = new Book();
                book.setBookId("001");
                book.setBookName("数据结构");
                book.setBookStatus("未借出");
            
                service.addBook(book);
            }
            ```

- jdbctemplate操作数据库——修改、删除与添加差不多

- jdbctemplate操作数据库——查询返回某个值

    ```java
    @Override
    public int selectCount() {
        String sql = "SELECT COUNT(1) FROM book";
        //第二个参数是返回类型的Class
        Integer count =  jdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }
    ```

- jdbctemplate操作数据库——查询返回对象

    ```java
    //示例：根据ID查找对应的Book内的数据
    @Override
    public Book findOne(String id) {
        String sql = "SELECT * FROM book WHERE bookid = ?";
        //第二个参数是RowMapper接口，针对返回不同类型数据，使用这个接口里面实现类完成数据封装
        Book book = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Book>(Book.class), id);
        return book;
    }
    ```

- jdbctemplate操作数据库——查询返回集合

    ```java
    @Override
    public Book findAllBooks() {
        String sql = "SELECT * FROM book";
        //第二个参数是RowMapper接口，针对返回不同类型数据，使用这个接口里面实现类完成数据封装
        List<Book> books = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Book>(Book.class), id);
        return book;
    }
    ```

- jdbctemplate操作数据库——批量添加

    ```java
    /**
    * 批量添加
    *
    * @param batchArgs 批量添加的值,Object数组：每个数组中放入填充占位符的值
    */
    @Override
    public void batchAddBook(List<Object[]> batchArgs) {
        String sql = "INSERT INTO book VALUES(?, ?, ?)";
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
    }
    ```

- jdbctemplate操作数据库——批量删除（传入参数只要id）、批量修改（传入参数需要填充占位符的值）与批量添加类似

##### 事务

- 事务添加到JavaEE三层结构中的Service层

- Spring进行事务操作

  - 编程式事务管理
  - 声明式事务管理（推荐）

- 声明式事务管理

  - 基于注解方式（推荐）
  - 基于XML配置文件方式

- 在Spring进行声明式事务管理时，底层使用的是AOP原理

- Spring事务管理提供一个接口，代表事务管理器，这个接口针对不同的ORM框架提供不同的实现类

- 注解声明式事务管理

  - 1、在Spring配置文件中创建事务管理器对象

  - 2、在Spring配置文件中，开启事务注解（引入tx名称空间）

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:context="http://www.springframework.org/schema/context"
           xmlns:tx="http://www.springframework.org/schema/tx"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                               http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                               http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">
    
        <!--组件扫描-->
        <context:component-scan base-package="com.edu"/>
    
        <!--数据库连接池-->
        <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" destroy-method="close">
            <property name="url" value="jdbc:mysql:///edudb01"/>
            <property name="username" value="root"/>
            <property name="password" value="123"/>
            <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        </bean>
    
        <!--创建JdbcTemplate对象，注入DataSource-->
        <bean id="JdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
            <!--注入数据库连接池对象-->
            <property name="dataSource" ref="dataSource"></property>
        </bean>
    
        <!--创建事务管理器-->
        <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
            <!--注入数据源，以表明是对哪个数据库进行事务操作-->
            <property name="dataSource" ref="dataSource"></property>
        </bean>
    
        <!--开启事务注解，并指定用的是哪个事务管理器-->
        <tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
    </beans>
    ```

- 在实现类上或方法上添加注解`@Transactional`

- 声明式事务管理参数配置

  - 1、Propagation：事务传播行为 `@Transactional(propagation = Propagation.REQUIRED)`
    - 多事务（并发事务）时，一个事务内的方法调用另一个事务内的方法，被调用的方法该如何进行
  - 2、isolation：事务隔离 `@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)`
    - 脏读：一个未提交的事务读取到另一个未提交事务的数据
    - 不可重复读：一个未提交的事务读取到另一个已提交事务修改的数据
    - 幻读：一个未提交的事务读取到另一个已提交事务添加的数据
    - 注：不可重复读可能发生在update,delete操作中，而幻读发生在insert操作中
  - 3、timeout：超时时间 `@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, timeout = -1)`
    - 事务在一定时间内如果没有提交，则回滚
    - 默认是-1，不超时，以秒为单位
  - 4、readOnly：是否只读
    - 读：查询操作 写：添加修改删除操作
    - 默认值为false，表示可以查询、添加、修改、删除操作
    - 置为true后，只能查询
  - rollbackFor：回滚
    - 设置出现哪些异常进行事务回滚
  - noRollbackFor：不回滚
    - 设置出现哪些异常不进行事务回滚
