###### 常识

1、包装类是针对基础类型而言的，因此String不是包装类

2、实例方法是指普通的方法，类方法是指static修饰的方法

3、System.out.println()中

- System是java.lang中的类
- out为System中的一个静态成员
- out是java.io.PrintStream类的对象
- println()是java.io.PrintStream类的方法
- 即：类.静态方法.println()方法

4、不是关键字的有：true、false、null、sizeof、friendly

5、局部变量没有默认初始化值，必须先赋值后使用，否则通不过编译，成员变量（类的属性）有默认初始化值，在创建对象时被初始化，static修饰属性就成为了类变量，在类加载时被创建，并进行初始化



###### String类、包装类、数据类型

1、输出的内容为：

```java
public class SystemUtil{
    public static boolean isAdmin(String userId){
        return userId.toLowerCase()=="admin";
    }
    public static void main(String[] args){
        System.out.println(isAdmin("Admin"));
    }
}
```

答案：false

解析：

- `toLowerCase()`的源码是`new String`，因此==比较的是堆中对象的地址，而不是常量池中的String

2、在Java中，以下数据类型中,需要内存最多的是：

```
byte	8位	1字节
long	64位 8字节
Object	引用类型，只申明而不创建实例，只会在内存中开辟空间，默认为空，空占1bit（1位 1/8字节）
int 	32位 4字节
```

答案：long

3、在java7中，下列不能做switch()的参数类型是：

答案：浮点型

解析：

- switch语句后的控制表达式只能是short、char、int、long整数类型和枚举类型，不能是float，double和boolean类型。String类型是java7开始支持。

4、以下b的值是：byte b = (byte)129：

答案：-127

解析：

- 因为byte是有符号单字节整形，所以存储数字范围是[-128·127]，而且定义数字的时候是一个环，最大数字后面就是最小，因此(byte)128则是127的“后面一位”，也就是-128，同理(byte)129就是-127

5、有以下两个赋值语句，正确的是：

```java
a = Integer.parseInt("1024");
b = Integer.valueOf("1024").intValue();
```

答案：a和b都是整数类型变量并且它们的值相等。

解析：

- Integer.parseInt("");是将字符串类型转换为int的基础数据类型
- Integer.valueOf("")是将字符串类型数据转换为Integer对象
- Integer.intValue();是将Integer对象中的数据取出，返回一个基础数据类型int

6、o1的结果是：

```java
Object o1 = true ? new Integer(1) : new Double(2.0);
```

答案：1.0

解析

- 三元操作符如果遇到可以转换为数字的类型，会做自动类型提升，因此会将Integer变成Double类型
- 若两个操作数不可转换，则不做转换，返回值为Object类型

7、表达式(short)10/10.2*2运算后结果类型是

答案：double

解析：

- (short)10/10.2*2，而不是(short) (10/10.2*2)，前者只是把10强转为short，又由于运算式中存在浮点数，所以会对结果值进行一个自动类型的提升，浮点数默认为double，所以答案是double；后者是把计算完之后值强转short



###### 封装、继承、多态

1、下列选项中是正确的方法声明的是：

```java
protected abstract void f1();
public final void f1() {}
static final void fq(){}
private void f1() {}
```

答案：ABCD

解析：

- A：抽象方法只可以被public 和 protected修饰；
- B：final可以修饰类、方法、变量，分别表示：该类不可继承、该方法不能重写、该变量是常量
- C：static final 可以表达在一起来修饰方法，表示是该方法是静态的不可重写的方法
- D：private 修饰方法（这太常见的）表示私有方法，本类可以访问，外界不能访问

2、下面代码输出是？

```java
enum AccountType
{
    SAVING, FIXED, CURRENT;
    private AccountType()
    {
        System.out.println("It is a account type");
    }
}
class EnumOne
{
    public static void main(String[]args)
    {
        System.out.println(AccountType.FIXED);
    }
}
```

答案：编译正确，输出”It is a account type”thrice followed by”FIXED”

解析：

- 枚举类有三个实例，故调用三次构造方法，打印三次It is a account type



###### 异常处理

1、以下代码执行后输出结果为：

```java
public class Test {
    public static void main(String[] args) {
        System.out.println("return value of getValue(): " +
        getValue());
    }
     public static int getValue() {
         try {
             return 0;
         } finally {
             return 1;
         }
     }
 }
```

答案：return value of getValue(): 1

解析：

- 当Java程序执行try块、catch块时遇到了return或throw语句，这两个语句都会导致该方法立即结束，但是系统执行这两个语句并不会结束该方法，而是去寻找该异常处理流程中是否包含finally块，如果没有finally块，程序立即执行return或throw语句，方法终止；如果有finally块，系统立即开始执行finally块。只有当finally块执行完成后，系统才会再次跳回来执行try块、catch块里的return或throw语句；如果finally块里也使用了return或throw等语句，finally块会终止方法，系统将不会跳回去执行try块、catch块里的任何代码



###### 多线程

1、java用（）机制实现了线程之间的同步执行：

答案：监视器

解析：

- 首先jvm中没有进程的概念 ，但是jvm中的线程映射为操作系统中的进程，对应关系为1：1。那这道题的问的就是jvm中线程如何异步执行 。  在jvm中 是使用监视器锁来实现不同线程的异步执行，  在语法的表现就是synchronized  。

2、下面有关 Java ThreadLocal 说法正确的有

答案：ABCD

解析：

- ThreadLocal存放的值是线程封闭，线程间互斥的，主要用于线程内共享一些数据，避免通过参数来传递
- 线程的角度看，每个线程都保持一个对其线程局部变量副本的隐式引用，只要线程是活动的并且 ThreadLocal 实例是可访问的；在线程消失之后，其线程局部实例的所有副本都会被垃圾回收
- 在Thread类中有一个Map，用于存储每一个线程的变量的副本。
- 对于多线程资源共享的问题，同步机制采用了“以时间换空间”的方式，而ThreadLocal采用了“以空间换时间”的方式
- 在Thread中有一个成员变量ThreadLocals，该变量的类型是ThreadLocalMap,也就是一个Map，它的键是threadLocal，值就是变量的副本



###### 序列化

1、将此对象序列化为文件，并在另外一个JVM中读取文件，进行反序列化，请问此时读出的Data0bject对象中的word和i的值分别为：

```java
//有以下一个对象：
public class DataObject implements Serializable{
    private static int i=0;
    private String word=" ";
    public void setWord(String word){
        this.word=word;
    }
    public void setI(int i){
        Data0bject.i=i;
     }
}
//创建一个如下方式的DataObject:
DataObject object=new Data0bject ( );
object.setWord("123");
object.setI(2);
```

答案："123", 0

解析：

- 序列化保存的是对象的状态，静态变量属于类的状态，因此，序列化并不保存静态变量。所以i是没有改变的
- 序列化的是对象，不是类，类变量不会被序列化

2、下列关于序列化和反序列化描述正确的是

```
序列化是将数据转为n个 byte序列的过程
反序列化是将n个 byte转换为数据的过程
```



###### Collection

1、下面哪个Set类是按元素排好序的：

```
TreeSet自然排序，调用有参的构造器插入元素时，会根据自定的排序规则自动排序
LinkedHashSet按添加顺序排序，元素的顺序为添加的顺序
```

答案：TreeSet



###### 编译

1、在 JAVA 编程中， Java 编译器会将 Java 程序转换为：

```
编译器将java源代码编译成字节码class文件
类加载到JVM里面后，执行引擎把字节码转为可执行码
执行的过程，再把可执行码转为机器码，由底层的操作系统完成执行
```

答案：字节码



###### 网络通信、Servlet

1、默认RMI采用的是什么通信协议

答案：TCP/IP

解析：

- 死记硬背

2、下面哪个不属于HttpServletResponse接口完成的功能

答案：读取路径信息

解析：

- HttpServletResponse完成：设置http头标，设置cookie，设置返回数据类型，输出返回数据
- 读取路径信息是HttpServletRequest做的

3、在Web应用程序中，(  )负责将HTTP请求转换为HttpServletRequest对象

答案：Web容器

解析：

- web容器是一种服务程序，在服务器一个端口就有一个提供相应服务的程序
- 这个程序就是处理从客户端发出的请求，如Java的Tomcat，ASP的IIS或PWS都是这样的容器
- 一个服务器可以有多个容器



###### 语句格式

1、有一个源代码，只包含import java.util.* ; 这一个import语句，下面叙述正确的是

```
A:只能写在源代码的第一句
B:可以访问java/util目录下及其子目录下的所有类
C:能访问java/util目录下的所有类，不能访问java/util子目录下的所有类
D:编译错误
```

答案：C

解析：

- java.util.*，只能读取其目录下的类，不能读取其子目录下的类
- 因为其根目录和子目录下可能有同名类，若都能读取，则会混淆



###### JVM、内存

1、针对jdk1.7,以下哪个不属于JVM堆内存中的区域

答案：常量池

解析：

- jvm堆分为：新生代（一般是一个Eden区，两个Survivor区），老年代（old区）
- 常量池属于 PermGen（方法区）
