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
    <mirrors>
        <mirror>
            <id>nexus-aliyun</id>
            <mirrorOf>central</mirrorOf>
            <name>Nexus aliyun</name>
            <url>http://maven.aliyun.com/nexus/content/groups/public</url>
        </mirror>
    </mirrors>
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



##### 根据“坐标”创建Maven工程

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

