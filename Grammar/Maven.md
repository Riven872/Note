##### 1、Maven概述

###### Maven作为依赖管理工具

- jar包的规模
- jar包的来源
- jar包之间的依赖关系

###### Maven作为构建管理工具

- 脱离IDE环境进行构建操作



##### 2、什么是Maven

- 为Java项目提供构建和依赖管理支持的工具

###### 构建

- 构建是使用【原材料生产出直接可用的产品】的过程（以JavaWeb为例，成品就是一个可以在服务器上运行的项目）
- 构建过程中包含的主要的环节（主要，非所有）
    - 清理：删除上一次构建的结果，为下一次构建做好准备
    - 编译：Java源代码程序编译为\*.class字节码文件
    - 测试：运行提前准备好的测试程序（整合的JUnit工具）
    - 报告：针对刚才测试的结果生成一个全面的信息
    - 打包：
        - Java工程：Jar包
        - Web工程：War包
    - 安装：把一个Maven工程经过打包操作生成的Jar包或者War包存入到Maven仓库
    - 部署：
        - 部署Jar包：把一个Jar包部署到Nexus私服服务器上
        - 部署War包：借助相关Maven插件（如Cargo），将War包部署到Tomcat服务器上

###### 依赖

- 如果A工程里面用到了B工程的类、接口、配置文件等这样的资源，那么可以称为A依赖B
- 依赖管理中要解决的具体问题：
    - Jar包的下载：使用Maven之后，Jar包会从规范的远程仓库下载到本地
    - Jar包之间的依赖：通过依赖的传递性自动完成
    - Jar包之间的冲突：通过对依赖的配置进行调整，让某些Jar包不会被导入



##### 3、Maven核心程序

###### 解压Maven核心程序

- Maven的核心配置文件：`conf/settings.xml`

###### 配置本地仓库

- ```xml
    <!--配置Maven本地方库，这个目录可以等执行构建命令时由Maven创建-->
    <localRepository>d:\maven-repo</localRepository>
    ```

###### 配置阿里云提供的镜像仓库

- 中央仓库在境外，下载速度慢

- 将原有的配置例子注释掉

    ```xml
    <!--<mirror>
          <id>maven-default-http-blocker</id>
          <mirrorOf>external:http:*</mirrorOf>
          <name>Pseudo repository to mirror external repositories initially using HTTP.</name>
          <url>http://0.0.0.0/</url>
          <blocked>true</blocked>
    </mirror>-->
    ```

- 在配置文件中进行配置，将mirro标签整体放入mirros标签内

    ```xml
    <mirror>
        <id>nexus-aliyun</id>
        <mirrorOf>central</mirrorOf>
        <name>Nexus aliyun</name>
        <url>http://maven.aliyun.com/nexus/content/groups/public</url>
    </mirror>
    ```

配置Maven工程的基础JDK版本

- 默认是JDK5，现要在配置文件中配置成JDK8

    ```xml
    <profile>
        <id>jdk-1.8</id>
        <activation>
            <activeByDefault>true</activeByDefault>
            <jdk>1.8</jdk>
        </activation>
        <properties>
            <maven.compiler.source>1.8</maven.compiler.source>
            <maven.compiler.target>1.8</maven.compiler.target>
            <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
        </properties>
    </profile>
    ```

###### 配置环境变量（略...）



##### 4、根据“坐标”创建Maven工程

###### 坐标

- 向量：使用三个“向量”在Maven仓库中唯一的定位到一个Jar包（由大到小的范围）

    - groupId：公司或组织的id
    - artifactId：一个项目或者是项目中一个模块的id
    - version：版本号

- 三个向量的取值方式

    - groupId：公司或组织域名的倒序，通常也会加上项目名称

        - 如：com.edu.maven（即maven.edu.com）

    - artifactId：模块的名称，将来作为Maven工程的工程名

    - version：模块的版本号

        - 如：SNAPSHOT表示快照版本，正在迭代过程中，不稳定的版本
        - 如：RELEASE表示正式版本

    - 例如

        - groupId：com.edu.maven
        - artifactId:pro01-edu-maven
        - version:1.0-SNAPSHOT

    - 坐标：

        ```xml
        <groupId>javax.servlet</groupId>
        <artifactId>servlet-api</artifactId>
        <version>2.5</version>
        ```

    - 坐标对应的Jar包在Maven本地仓库中的位置：

        ```xml
        Maven本地仓库根目录\javax\servlet\servlet-api\2.5\servlet-api-2.5.jar
        ```

###### 实验操作

- 创建目录作为后面操作的工作空间

- 此时有三个目录：

    - Maven核心程序
    - Maven本地仓库
    - 本地工作空间

- 使用命令创建Maven工程（了解即可）

- 自动生成的pom.xml

    ```xml
    <!--根标签是project，表示对当前工程进行配置、管理-->
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    
        <!--从Maven2开始就固定4.0.0-->
        <!--代表当前pom.xml所采用的标签结构-->
        <modelVersion>4.0.0</modelVersion>
    
        <!--gav 坐标信息-->
        <groupId>com.edu.maven</groupId>
        <artifactId>pro01-maven-java</artifactId>
        <version>1.0-SNAPSHOT</version>
        <!--packaging 表示当前Maven工程打包的方式-->
        <!--取值是jar，则说明这是一个Java工程-->
        <!--取值是war，则说明这是一个Javaweb工程-->
        <!--取值是pom，则说明这是一个管理其他工程的工程-->
        <packaging>jar</packaging>
    
        <name>pro01-maven-java</name>
        <url>http://maven.apache.org</url>
    
        <!--在Maven中定义属性值-->
        <properties>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        </properties>
    
        <!--dependencies配置具体依赖信息，包含多个dependency-->
        <dependencies>
            <!--dependency配置一个具体的依赖信息-->
            <dependency>
                <!--导入哪个Jar包，就配置它的坐标信息-->
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>4.12</version>
    
                <!--test标签：配置当前依赖的范围-->
                <scope>test</scope>
            </dependency>
        </dependencies>
    </project>
    ```

###### POM

- 含义：Project Object Model，项目对象模型，是模型化思想的具体体现
- POM表示将工程抽象为一个模型，再用程序中的对象来描述这个模型（如：要研究地球，则模块化出地球仪）
- 对应的配置文件：pom.xml配置文件就是Maven工程的核心配置文件

###### 约定的目录结构

- Maven本地工作空间：
    - src：源码目录
        - main：主题程序目录
            - java：Java源代码
                - com：package目录
            - resources：配置文件
        - test：测试程序目录
            - java：Java源代码
                - com：package目录

##### 5、在Maven工程中编写代码

- 主体程序放在main目录下com.foo.bar.maven中，也就是包的最下一层
- 测试程序放在test目录下com.foo.bar.maven中，也就是包的最下一层



##### 6、执行Maven的构建命令——命令行操作

###### 基本要求

- 要求：在运行Maven中和构建操作相关的命令时，必须进入到pom.xml所在的目录。否则会报错
- 除了`mvn -v`以外，其他的基本都是构建命令
- 构建相关的命令要在pom.xml所在目录下运行，且操作哪个工程，就进入哪个工程的pom.xml目录

###### 清理操作

- 删除target目录

###### 编译操作

- 主体程序编译结果存放的目录：target/classes
- 测试程序编译结果存放的目录：target/test-classes

###### 测试操作

- 测试的报告存放的目录：target/surefire-reports

###### 打包操作

- 打包的结果：jar
- 存放的目录：target

###### 安装操作

- 就是将本地构建过程中生成的jar包存入到Maven本地仓库
- 还会生成对应的pom.xml文件一起存入本地仓库

###### 依赖的范围

- 标签的位置：dependencies/dependency/scope
- 标签的可选值：compile/test/provided/system/runtime/import
- compile：主体业务功能所需要的依赖
- test：测试过程中使用的jar包，只对test目录有效，且不会部署到服务器（即打war包时不会在里面）
- provided：用于main目录和test目录，但不会部署到服务器上，原因是服务器中已经有这个依赖了，如果加入进来会产生冲突

###### 依赖的传递性

- 在A依赖B，B依赖C的前提下，C是否能传递到A（即在A没有配置对C的依赖的情况下，A里面能不能直接使用C），取决于B依赖C时使用的依赖范围
- B依赖C使用的是compile，则可以传递
- B依赖C使用的是test或provided，则不可以传递，因此在A需要C的jar包时，需要明确的配置依赖

###### 依赖的排除

- 阻断依赖的传递，防止导入多个相同的jar包以造成的冲突

- 配置依赖的排除

    ```xml
    
    <exclusions>
        <exclusion>
            <!-- 配置具体的排除信息，让选定的依赖不传递到当前工程，且不需要指定version -->
            <groupId>commons-logging</groupId>
            <artifactId>commons-logging</artifactId>
        </exclusion>
    </exclusions>
    ```

###### 继承

- Maven工程之间，A工程继承B工程，则A工程是子工程，B工程是父工程

- 本质是子工程继承父工程的pom.xml中的配置

- 作用是让父工程统一管理项目中的依赖信息，即统一管理版本

- 只有打包方式是pom的Maven工程能够管理其他的Maven工程

- 打包方式为pom的Maven工程中不写业务代码，是用来专门管理其他的Maven工程

    ```xml
    <groupId>com.edu.maven</groupId>
    <artifactId>pro03-maven-parent</artifactId>
    <version>1.0-SNAPSHOT</version>
    <!-- 当前工程作为父工程，去管理其它工程-->
    <packaging>pom</packaging>
    ```

- 即使在父工程配置了对依赖的管理，子工程需要使用具体哪一个依赖还是要明确配置

    - 对于已经在父工程进行了管理的依赖，子工程中引用时可以不写version
    - 如果明确写了version且与父工程的version不同，则会采纳子工程的version
    - 绝大多数情况下，子工程遵从父工程统一管理的依赖

- 子工程并不一定要把父工程配置的所有的依赖都拿过来，需要哪个依赖就配置哪个依赖

###### 聚合

- 使用一个“总工程”将各个“模块工程”汇集起来，作为一个整体对应完整的项目

- 通过聚合，很多构建命令可以在“总工程”中执行，影响到“模块工程”，且会自动按照正确的顺序执行



##### 7、使用Maven——IDEA环境

- 实际操作...



##### 8、生命周期

- 为了让构建自动化完成，Maven设定了三个生命周期，生命周期中的每一个环节对应构建过程中的一个操作
- Clean：清理操作相关
- Site：生成站点相关
- Default：主要构建过程



