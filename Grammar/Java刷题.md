1、输出的内容为：false

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

- `toLowerCase()`的源码是`new String`，因此==比较的是堆中对象的地址，而不是常量池中的String

2、下列选项中是正确的方法声明的是：ABCD

```java
protected abstract void f1();
public final void f1() {}
static final void fq(){}
private void f1() {}
```

- A：抽象方法只可以被public 和 protected修饰；
- B：final可以修饰类、方法、变量，分别表示：该类不可继承、该方法不能重写、该变量是常量
- C：static final 可以表达在一起来修饰方法，表示是该方法是静态的不可重写的方法
- D：private 修饰方法（这太常见的）表示私有方法，本类可以访问，外界不能访问

3、以下代码执行后输出结果为：return value of getValue(): 1

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

- 当Java程序执行try块、catch块时遇到了return或throw语句，这两个语句都会导致该方法立即结束，但是系统执行这两个语句并不会结束该方法，而是去寻找该异常处理流程中是否包含finally块，如果没有finally块，程序立即执行return或throw语句，方法终止；如果有finally块，系统立即开始执行finally块。只有当finally块执行完成后，系统才会再次跳回来执行try块、catch块里的return或throw语句；如果finally块里也使用了return或throw等语句，finally块会终止方法，系统将不会跳回去执行try块、catch块里的任何代码

4、java用（）机制实现了线程之间的同步执行：监视器

- 首先jvm中没有进程的概念 ，但是jvm中的线程映射为操作系统中的进程，对应关系为1：1。那这道题的问的就是jvm中线程如何异步执行 。  在jvm中 是使用监视器锁来实现不同线程的异步执行，  在语法的表现就是synchronized  。

5、将此对象序列化为文件，并在另外一个JVM中读取文件，进行反序列化，请问此时读出的Data0bject对象中的word和i的值分别为："123", 0

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

- 序列化保存的是对象的状态，静态变量属于类的状态，因此，序列化并不保存静态变量。所以i是没有改变的
- 序列化的是对象，不是类，类变量不会被序列化

6、下列关于序列化和反序列化描述正确的是

```
序列化是将数据转为n个 byte序列的过程
反序列化是将n个 byte转换为数据的过程
```

7、在Java中，以下数据类型中,需要内存最多的是long

```
byte	8位	1字节
long	64位 8字节
Object	引用类型，只申明而不创建实例，只会在内存中开辟空间，默认为空，空占1bit（1位 1/8字节）
int 	32位 4字节
```

8、下面哪个Set类是按元素排好序的：TreeSet

```
TreeSet自然排序，调用有参的构造器插入元素时，会根据自定的排序规则自动排序
LinkedHashSet按添加顺序排序，元素的顺序为添加的顺序
```

9、在 JAVA 编程中， Java 编译器会将 Java 程序转换为：字节码

```
编译器将java源代码编译成字节码class文件
类加载到JVM里面后，执行引擎把字节码转为可执行码
执行的过程，再把可执行码转为机器码，由底层的操作系统完成执行
```

10、在java7中，下列不能做switch()的参数类型是：浮点型

```
switch语句后的控制表达式只能是short、char、int、long整数类型和枚举类型，不能是float，double和boolean类型。String类型是java7开始支持。
```

11、以下b的值是：byte b = (byte)129：-127

```
因为byte是有符号单字节整形，所以存储数字范围是[-128·127]，而且定义数字的时候是一个环，最大数字后面就是最小，因此(byte)128则是127的“后面一位”，也就是-128，同理(byte)129就是-127
```

