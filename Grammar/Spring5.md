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

  - 3、调用Bean的初始化的方法（需要进行配置初始化的方法）

    ```xml
    <!--在类中定义一个方法，默认是不会执行，但用init-method属性指定后，会自动进行调用-->
    <bean id="myBean" class="com.edu.IOC4.FactoryBean.MyBean" init-method="foo"></bean>
    ```

  - 4、Bean可以使用了（对象获取到了）

  - 5、当容器关闭的时候，调用Bean的销毁的方法（需要进行配置销毁的方法）
