## 字节码

### 1、字节码文件概述

- 只要能将源文件编译为正确的 Class 文件，那么这种语言就可以在 Java 虚拟机上执行。
- 字节码文件（.class）内部就是二进制数据。
- 基本所有的类型有对应有 Class 对象，如接口、数组、枚举、基本数据类型（String.class）

### 2、Class 文件细节

1. Class 文件总体结构包括：魔数、Class 文件版本、常量池、访问标识、类索引、字段表集合、方法表集合、属性表集合。
2. Class 文件的魔数指的是：
    - 每个 Class 文件开头的 4 个字节的无符号整数就是魔数。
    - 唯一作用是确定该文件是否能被虚拟机接受。
    - 因此使用魔数而不是扩展名来识别的目的是出于安全考量。



## 类的加载



## 运行时内存



## 对象内存布局



## 执行引擎



## 垃圾回收



## JVM 性能监控



## JVM 调优案例



