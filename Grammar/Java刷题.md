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

6、在类加载的时候会赋予初值的是类变量（静态变量），而非对象成员（非静态变量）

7、%是取余，余数和被除数符号一致。mod是取模，和除数符号一致

8、整数类型  byte short int long

9、线程优先级高只能说明它获得cpu时间片的几率更大，而不是先于优先级低的线程先执行（所以并不会发生抢占的行为）

10、forward属于服务器内部的重定向，redirect输入外部的重定向，浏览器向服务器发送两次请求

11、static不能用来修饰类，除非类是内部类，此时该类作为外部类的成员变量，可以用static来修饰

12、Java语言中，中文字符所占的字节数取决于字符的编码方式

13、Java中，HashMap解决冲突用的是拉链法而非定址法

14、List和Set是collectin的子接口，Map是单独的一个

15、除了Thread，Runnable，还有Callable等等方式来实现线程

16、ThreadLocal重要作用在于多线程间的数据独立

17、垃圾回收器（GC）是将没有引用的对象进行垃圾回收，回收前会调用finalize()方法，下一个周期中进行回收

18、自动装箱不提供向上转型，如`Double foo=3;`是错误的

19、String s="welcome"+"to"+360;会经过编译器优化成了String s = "welcometo360";

20、在调用子类构造器之前，会调用父类的无参构造器，如果父类没有无参构造器，则需要用super显式的调用父类的有参构造器，否则会编译错误

19、静态属性既可以通过实例名访问，也可以直接用类名来访问

20、`&` `|`是按位运算符，如a|b、c&d，效果与逻辑运算符一样，区别在于前面无论true or false，后面的都会执行

21、HttpServletResponse方法调用，给客户端回应了一个定制的HTTP回应头，可以使用`response.setHeader();`如果同名header已存在，则覆盖一个同名header，或者使用`response.addHeader()`;如果同名header已存在，则追加至原同名header后面

22、数组默认值

- 基本类型数组: byte[],short[],int[] ,默认值为0；
- boolean[]默认值为false；
- float[],double[],默认值为0.0；
- 对象类型数组: 默认值为null

23、以下方法实现的单例是线程安全的：枚举；静态内部类；双检索模式；饿汉式。懒汉式如果加了synchronized也是线程安全的
24、new URL()时必须捕获检查异常，但这个异常是由于字符串格式和URL不符导致的，与网址是否存在无关。URL的toString方法返回字符串，无论网址是否存在，所以如果网址不存在，则返回toString后的网址

25、抽象类中不一定包含抽象方法，但是有抽象方法的类必定是抽象类

26、类不可以多继承，但是接口可以，如`Child extends Parent1，Parent2`

27、在getClass没有重写的情况下，会调用Object的getClass，并返回调用者的运行时的类，继续调用getName时，会返回包名.运行时类名。如果需要返回父类的运行时类名，需要写super.getClass().getSuperclass()

28、相同类型的变量、调用同一个方法时呈现出多种不同的行为特征，这就是多态（简单来说就是，**同一领域不同对象**调用相同的方法但表现不同）

29、Java内存区域包括：Java虚拟机栈、堆、方法区、程序计数器、本地方法栈

30、Vector、StringBuffer、Properties是线程安全的

31、对象相等则HashCode一定相等，但HashCode相等则对象不一定相等，需要进一步查询equals是否相等

32、Iterator  支持从源集合中安全地删除对象，只需在 Iterator 上调用 remove() 即可。有些集合不允许在迭代时删除或添加元素，但是调用 Iterator 的remove() 方法是个安全的做法。

33、int和Integer进行==比较时，会将integer进行自动拆箱进行比较，因此Integer a = new Integer(2020)比较int b = 2020;时a == b



###### String类、包装类、数据类型、运算

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

8、执行语句int a= '2'后，a的值是（ ）

答案：50

解析：

- 实质是char类型自动向上转型为int类型
- 字符'2'对应的ASCII码是整型的50

9、返回结果为

```java
Integer a = 1;
Integer b = 1;
Integer c = 500;
Integer d = 500;
System.out.print(a == b);
System.out.print(c == d);
```

答案：true、false

解析：

- -128~127存在常量池内,超过就要new新的

10、下面的程序 编译运行后，在屏幕上显示的结果是

```java
public class Test {

    public static void main(String args[]) {
        int x, y;
        x = 5 >> 2;
        y = x >>> 2;
        System.out.println(y);
    }
}
```

答案：0

解析：

- \>>表示带符号右移位运算符，5的二进制表示为：101，5\>>2表示右移两位，即001，即1
- \>>>表示不带符号右移位运算符，1\>>>2即001右移两位，即000

- <<表示带符号左移位运算符，5的二进制表示为：101，5<<2表示左移两位，10100，即64

11、输出结果为：

```java
String str = "";
System.out.print(str.split(",").length);
```

答案：1

解析：

- 当没有符合条件的字符串切割时，会返回长度为一的数组，且该数组中的元素为""，空串
- 可以这么理解，ABC去切割，结果没有符合条件的切割，而且split返回值是一个数据，那么这个数组返回长度为一，且数组[0]存放的就是ABC

12、以下代码执行后输出结果为（ ）

```java
public class ClassTest{
     String str = new String("hello");
     char[] ch = {'a','b','c'};
     public void fun(String str, char ch[]){
     str="world";
     ch[0]='d';
 }
 public static void main(String[] args) {
     ClassTest test1 = new ClassTest();
     test1.fun(test1.str,test1.ch);
     System.out.print(test1.str + " and ");
     System.out.print(test1.ch);
     }
 }
```

答案：hello and dbc

解析：

- 在函数调用中，char和String都是传递地址，而非传递值
- str="world"相当于 str = new String("world"); 即str从新指向了一个新开辟的堆空间，而放弃了原先形参的地址，因此不会影响到实参
- 而ch[0] = 'd'相当于操控原先的地址找到对象并修改，因此会影响到实参
- 总结：形参有没有影响实参首先看传递的是值还是地址，其次看形参在此期间有没有指向新的地址，如果有则不会影响实参，反之则影响实参

13、以下程序的执行结果是

```java
static boolean foo(char c){
    System.out.print(c);
    return true;
}
public static void main(String[] args) {
    int i =0;
    for(foo('A');foo('B')&&(i<2);foo('C')){
        i++;
        foo('D');
    }
}
```

答案：ABDCBDCB

解析：

- for循环的执行顺序是：最开始执行foo('A')，且是初始化条件，只执行一次，然后是foo('B')&&(i<2)，然后是循环体，最后是foo('C')
- 每次判断时都会执行foo('B')&&(i<2)，只有每次判断为true且循环体执行完毕时，才执行foo('C')

14、以下JAVA程序代码的输出是

```java
public static void main(String args[]) {
	System.out.println(14^3);
}
```

答案：13

解析：

- `^`是异或运算符，先转成二进制的数字，比较之后再转回二进制
- 14是1110，3是0011，相同是0 不同是1，所以14^3=1101，即13

15、以下程序段的输出结果为

```java
public class EqualsMethod
{
    public static void main(String[] args)
    {
        Integer n1 = new Integer(47);
        Integer n2 = new Integer(47);
        System.out.print(n1 == n2);
        System.out.print(",");
        System.out.println(n1 != n2);
    }
}
```

答案：true，false

解析：

- 只要是new的，地址就是不同的
- 如果是Integer n1 = 47；Integer n2 = 47；则二者是相同的，因为是在缓存中取的

16、Java表达式"13 & 17"的结果是什么?

答案：1

解析：

- `&`运算符：两个数据转换为二进制，然后从两个数的最高位进行位运算，两个都为真（1），结果才为真（1），否则为假（0）
- 13：01101
- 17：10001
- 结果：00001，即为1

17、下列代码片段中，存在编译错误的语句是

```java
byte b1=1,b2=2,b3,b6,b8;
final byte b4=4,b5=6,b7;
b3=(b1+b2);  /*语句1*/
b6=b4+b5;    /*语句2*/
b8=(b1+b4);  /*语句3*/
b7=(b2+b5);  /*语句4*/
System.out.println(b3+b6);
```

答案：语句1、3、4

解析：

- 所有的byte,short,char型的值将被提升为int型
- 被fianl修饰的变量不会自动改变类型，当2个final修饰相操作时，结果会根据左边变量的类型而转化。
- 语句1：因此b1+b2时的返回值为int型，但是接收类型是b3，即byte型
- 语句2：b4、b5为final型，因此不会发生自动转化，以等号左边类型为准转化
- 语句3：b4不会自动提升，但是b1会自动提升为int型，结果也为int型，但是接收类型是b7，即byte型
- 语句4：同上，同时b7也为final型，不可以再次赋值



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

3、下面的代码输出结果是：

```java
class Animal{
    public void move(){
        System.out.println("动物可以移动");
    }
}
class Dog extends Animal{
    public void move(){
        System.out.println("狗可以跑和走");
    }
    public void bark(){
        System.out.println("狗可以吠叫");
    }
}
public class TestDog{
    public static void main(String args[]){
        Animal a = new Animal();
        Animal b = new Dog(); 
        a.move();
        b.move();
        b.bark();
    }
}
```

答案：编译错误

解析：

- 第20行编译出错，因为父类中没有bark方法，多态只能调用子类重写父类的方法
- 可以使用向下转型((Dog)b).bark()使用该方法

4、下面字段声明中哪一个在interface主体内是合法的

```java
A.private final static int answer = 42;
B.public static int answer = 42;
C.final static answer = 42;
D.int answer;
```

答案：B

解析：

- 在接口中，属性都是默认public static final修饰的
- A，不能用private修饰，因为属性默认有public
- B，在接口中，属性默认public static final，这三个关键字可以省略
- C，没有声明属性的类型
- D，因为默认是final修饰，final修饰的属性必须赋值

5、对语句行 test.hello(). 描述正确的有

```
A.能编译通过，并正确运行
B.因为使用了未初始化的变量，所以不能编译通过
C.以错误的方式访问了静态方法
D.能编译通过，但因变量为null，不能正常运行
```

```java
package NowCoder;
class Test {
	public static void hello() {
	    System.out.println("hello");
	}
}
public class MyApplication {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test test=null;
		test.hello();
	}
}
```

答案：A

解析：

- 在其他类的静态方法中是可以调用公开的静态方法，此题hello方法是使用public修饰的所以在MyApplication类中的Main静态方法中调用hello是可以的
- 虽然test对象是null，但是这里调用类方法，实际上在用Test.hello，即使test为null也无关，因为不会去找到这个test判断它的值再去调用类方法

6、尝试编译以下程序会产生怎么样的结果？

```
A.编译错误将发生在（1），因为构造函数不能指定返回值
B.编译错误将发生在（2），因为该类没有默认构造函数
C.编译错误将在（3）处发生，因为该类没有构造函数，该构造函数接受一个int类型的参数
D.该程序将正确编译和执行
```

```java
public class MyClass {
    long var;
    public void MyClass(long param) { var = param; }//(1)
    public static void main(String[] args) {
        MyClass a, b;
        a =new MyClass();//(2)
        b =new MyClass(5);//(3)
    }
}
```

答案：C

解析：

- 有了void 就不是构造函数了，因此`public void MyClass(long param)`算普通的方法



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

2、AccessViolationException异常触发后，下列程序的输出结果为

```java
static void Main(string[] args)  
{  
    try  
    {  
        throw new AccessViolationException();  
        Console.WriteLine("error1");  
    }  
    catch (Exception e)  
    {  
        Console.WriteLine("error2");  
    }  
    Console.WriteLine("error3");  
}
```

答案：error2、error3

解析：

- try..catch，catch捕获到异常，如果没有抛出异常语句(throw)，不影响后续程序

3、以下关于JAVA语言异常处理描述正确的有

```
A.throw关键字可以在方法上声明该方法要抛出的异常。
B.throws用于抛出异常对象。
C.try是用于检测被包住的语句块是否出现异常，如果有异常，则捕获异常，并执行catch语句。
D.finally语句块是不管有没有出现异常都要执行的内容。
E.在try块中不可以抛出异常
```

答案：CD

解析：

- Java语言中的异常处理包括声明异常、抛出异常、捕获异常和处理异常四个环节。

- throw用于抛出异常。

- throws关键字可以在方法上声明该方法要抛出的异常，然后在方法内部通过throw抛出异常对象。

- try是用于检测被包住的语句块是否出现异常，如果有异常，则抛出异常，并执行catch语句。

- cacth用于捕获从try中抛出的异常并作出处理。

- finally语句块是不管有没有出现异常都要执行的内容。

- ```java
    try{
        throw new Exception();
    }
    ```

    因此E是错误的，可以在try块中抛出异常

4、有关finally语句块正确的是

```
A.不管catch是否捕获异常，finally语句块都是要被执行的
B.在try语句块或catch语句块中执行到System.exit(0)直接退出程序
C.finally块中的return语句会覆盖try块中的return返回
D.finally 语句块在 catch语句块中的return语句之前执行
```

答案：ABD

解析：

- 不管有木有出现异常，finally块中代码都会执行；
- 当try和catch中有return时，finally仍然会执行；
- finally是在return后面的表达式运算后执行的（此时并没有返回运算后的值，而是先把要返回的值保存起来，管finally中的代码怎么样，返回的值都不会改变，仍然是之前保存的值），所以函数返回值是在finally执行前确定的；
- finally中最好不要包含return，否则程序会提前退出，返回值不是try或catch中保存的返回值。



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

3、JDK提供的用于并发编程的同步器有哪些？

```
A.Semaphore
B.CyclicBarrier
C.CountDownLatch
D.Counter
```

答案：ABC

解析：

- Semaphore用来完成信号量控制，Semaphore可以控制某个资源可被同时访问的个数，通过acquire()获取一个许可，如果没有就等待，而release()释放一个许可
- CyclicBarrier的主要方法是await()，await()方法每被调用一次，计数便会减少1，并阻塞住当前线程。当计数减至0时，阻塞解除，所有在此CyclicBarrier上面阻塞的线程开始运行
- CountDownLatch.await()方法在倒计数为0之前会阻塞当前线程
- Counter不是并发编程的同步器



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



###### 集合

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



###### 语句格式、import、package

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

2、下列关于包（package）的描述，正确的是

```
A.包（package）是Java中描述操作系统对多个源代码文件组织的一种方式。
B.import语句将所对应的Java源文件拷贝到此处执行。
C.包（package）是Eclipse组织Java项目特有的一种方式。
D.定义在同一个包（package）内的类可以不经过import而直接相互使用。
```

答案：D

解析：

- 为了更好的组织类，Java提供了包机制。包是类的容器，用于分隔类名空间。如果没有指定包名，所有的示例都属于一个默认的无名包。Java中的包一般均包含相关的类，Java是跨平台的，因此Java中的包和操作系统没有任何关系，Java的包是用来组织文件的一种虚拟文件系统
- import语句并没有将对应的Java源文件拷贝到此处而仅仅是引入，告诉编译器有使用外部文件，编译的时候要去读取这个外部文件
- Java提供的包机制与IDE没有关系
- 定义在同一个包（package）内的类可以不经过import而直接相互使用



###### JVM、内存、GC

1、针对jdk1.7,以下哪个不属于JVM堆内存中的区域

答案：常量池

解析：

- JVM堆大小 = 新生代（old） + 老年代（young）
- 默认情况下，新生代占1/3堆空间大小，老年代占2/3堆空间大小
- 新生代分为一个Eden和两个Survivor区域，其中两个Survivor区域分别命名为from和to来区分
- 常量池属于 PermGen（方法区）

2、下面关于JAVA的垃圾回收机制，正确的是

```
A.当调用“System.gc()”来强制回收时，系统会立即回收垃圾
B.垃圾回收不能确定具体的回收时间
C.程序可明确地标识某个局部变量的引用不再被使用
D.程序可以显式地立即释放对象占有的内存
```

答案：B

解析：

- java的垃圾回收由垃圾回收器控制，显式调用回收方法（System.gc()）只是提醒GC可能需要执行一次垃圾回收，GC并不一定会立即执行垃圾回收
- 垃圾回收时间不是固定的，GC会定期监测满足回收条件才会回收

3、java程序内存泄露的最直接表现是

```
A.频繁FullGc
B.jvm崩溃
C.程序抛内存溢出的Exception
D.java进程异常消失
```

答案：C

解析：

- java是自动管理内存的，通常情况下程序运行到稳定状态，内存大小也达到一个基本稳定的值
- 但是内存泄露导致Gc不能回收泄露的垃圾，内存不断变大，最终超出内存界限，抛出OutOfMemoryExpection

4、下列Java代码中的变量a、b、c分别在内存的____存储区存放。

```java
class A {
    private String a = “aa”;
    public boolean methodB() {
        String b = “bb”;
        final String c = “cc”;
    }
}
```

答案：堆区、栈区、栈区

- 常量池属于堆中
- 方法内的变量，一般是临时放在栈中

5、关于Java语言的内存回收机制，下列选项中最正确的一项是

```
A.Java程序要求用户必须手工创建一个线程来释放内存
B.Java程序允许用户使用指针来释放内存
C.内存回收线程负责释放无用内存
D.内存回收线程不能释放内存对象
```

答案：C

解析：

- Java的内存回收是自动的，GC在后台运行，不需要用户手动操作
- Java中不允许使用指针
- 内存回收线程可以释放无用的对象内存

6、以下哪项陈述是正确的

```
A.垃圾回收线程的优先级很高，以保证不再使用的内存将被及时回收
B.垃圾收集允许程序开发者明确指定释放哪一个对象
C.垃圾回收机制保证了JAVA程序不会出现内存溢出
D.进入”Dead”状态的线程将被垃圾回收器回收
E.以上都不对
```

答案：E

解析：

- 垃圾回收在JVM中优先级相当相当低
- 垃圾收集器（GC）程序开发者只能推荐JVM进行回收，但何时回收、回收哪些，程序员不能控制
- 垃圾回收机制只是回收不再使用的JVM内存，如果程序有严重的BUG，内存会照样溢出
- 进入DEAD的线程还可以恢复，GC不会回收

7、下面有关java classloader说法正确的是

```
A.ClassLoader就是用来动态加载class文件到内存当中用的
B.JVM在判定两个class是否相同时，只用判断类名相同即可，和类加载器无关
C.ClassLoader使用的是双亲委托模型来搜索类的
D.Java默认提供的三个ClassLoader是Boostrap ClassLoader，Extension ClassLoader，App ClassLoader
E.以上都不正确
```

答案：ACD

解析：

- jdk提供了三个ClassLoader，根据层级从高到低为
    - 1、Bootstrap ClassLoader：主要加载JVM自身工作需要的类
    - 2、Extension ClassLoader：主要加载`%JAVA_HOME%\lib\ext`目录下的类库
    - 3、Application ClassLoader（也称为系统类加载器SystemClassLoader）：主要加载ClassPath指定的类库，一般情况下这是程序中的默认类加载器，也是`ClassLoader.getSystemClassLoader()` 的返回值（其中ClassPath默认指的是环境变量中配置的ClassPath，但是可以在执行Java命令的使用使用-cp参数来修改当前程序使用的ClassPath）
- JVM加载类的实现方式，称为双亲委托模型
    - 如果一个类加载器收到了类加载的请求，他首先不会自己去尝试加载这个类，而是把这个请求委托给自己的父加载器，每一层的类加载器都是如此。因此所有的类加载请求都应该传送到顶层的BootStrap ClassLoader中，只有当父加载器反馈自己无法完成加载请求时，子加载器才会尝试自己加载
    - 双亲委托模型的重要用途是为了解决类载入过程中的安全性问题（假设有一个开发者自己编写了一个名为Java.lang.Object的类，想借此欺骗JVM。现在他要使用自定义ClassLoader来加载自己编写的java.lang.Object类。然而幸运的是，双亲委托模型不会让他成功。因为JVM会优先在Bootstrap ClassLoader的路径下找到java.lang.Object类，并载入它）



###### IO流

1、对于文件的描述正确的是

```
文本文件是以“.txt”为后缀名的文件，其他后缀名的文件是二进制文件。
File类是Java中对文件进行读写操作的基本类。
无论文本文件还是二进制文件，读到文件末尾都会抛出EOFException异常。
Java中对于文本文件和二进制文件，都可以当作二进制文件进行操作。
```

答案：D

解析：

- 计算机文件基本分为两种：二进制文件和文本文件。文本文件是可以看到的字符，二进制文件是不可视字符
- Java中对文件进行读写操作的基本类是IO类；File类是对文件整体或文件属性操作的类
- 当输入过程中意外到达文件或流的末尾时，抛出EOFException异常，正常情况下读取到文件末尾时，不会抛异常
- Java中对文本文件和二进制文件都可以当做二进制文件进行操作

2、下列流当中，属于处理流的是

```
A.FilelnputStream
B.lnputStream
C.DatalnputStream
D.BufferedlnputStream
```

答案：CD

解析：

- 处理流就是包装流，需要包装基本的流
- 可以从/向一个特定的IO设备（如磁盘、网络）读/写数据的流，称为节点流，节点流也被成为低级流。
- 处理流是对一个已存在的流进行连接或封装，通过封装后的流来实现数据读/写功能，处理流也被称为高级流。
