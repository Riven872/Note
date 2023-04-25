## 一、Java 基础

### 1、hashCode() 方法

- `hashCode()` 与 `equals()` 作用都是用来比较两个对象是否相等。
- 但两个对象的 `hashCode()` 相同时，两个对象不一定相等，需要进一步的用 `equals()` 方法去判定。因为 `hashCode()` 使用的哈希算法存在让多个对象回传相同的哈希值，产生碰撞。因此 `hashCode()` 不同时，对象不相等，但是 `equals()` 相同，则两个对象相等。
- 可以直接用 `equals()` 来判定对象是否相同，为什么还要用不一定准确的 `hashCode()` 方法呢？是因为 `HashMap` 和 `HashSet` 这两个容器，有了 `hashCode()`  方法之后，可以大大减少 `equals()` 的次数。例如：把对象加入 `HashSet` 时，先会计算该对象的哈希值和已经加入的对象的哈希值比较，如果不同，直接加入即可，如果相同，可以再调用 `equals()` 判定是否真的相等，如果真的相等，则判定为重复元素，加入失败。
- 因此重写 `equals()` 时，也需要重写 `hashCode()`，防止 `equals()` 判定相同了，`hashCode()` 没有相同



### 2、接口和抽象类

- 接口本身可以继承多个接口，而实现类可以实现多个接口。
- 接口是为了提供一种规范，实现了该接口后需要实现接口内定义的方法。
- 非抽象类继承了抽象类之后，需要实现所有的抽象方法。
- 抽象类提供一个基础的实现类和可扩展的抽象类，子类继承之后，可以直接用父类的基础实现类，然后自行重写父类的抽象方法。
- 如 `AbstractApplicationContext`：`Spring` 应用程序上下文的抽象类，定义了应用程序上下文的通用行为和生命周期方法，如初始化、刷新和关闭等。
- `AbstractBeanFactory`：`Spring Bean`工厂的抽象类，提供了 `BeanFactory` 的基础实现，以便子类可以更方便地实现自己的 `Bean` 工厂。



### 3、String  类

- `String` 不可变的原因：

    - ```java
        public final class String implements java.io.Serializable, Comparable<String>, CharSequence {
            private final char value[];
        	//...
        }
        ```

    - `String` 使用 `char` 数组来存储，而保存字符串的数组被修饰为 `final` 且为私有，而且 `String` 类没有提供 / 暴露 修改字符串数组的方法

    - `String` 类被 `final` 修饰导致不能继承，也就避免子类去破坏 `String` 原来的值

- `String` 不可变的案例

    - ```java
        String str1 = "hello";
        String str2 = str1.toUpperCase();
        System.out.println(str1); // 输出 "hello"
        System.out.println(str2); // 输出 "HELLO"
        ```

    - 虽然使用了 `toUpperCase()` 方法，但是 `str1` 的值并没有改变，而是返回了一个新的字符串，不是修改原有的字符串

- `String` 不可变的应用

    - 不可变可以使字符串更加安全、可靠、高效，因此在 `Map` 结构中用于储存 `key` 值

- 字符串用 `+` 拼接时，通过字节码文件可知实质还是使用了 `StringBuilder` 调用 `append()` 方法拼接，完成之后再 `toString()`，得到一个 `String` 对象。但在循环中拼接时，底层会创建多个 `StringBuilder` 对象，此时就需要手动去拼接。

- 字符串常量是指直接用双引号括起来的字符串字面值，如 "hello"，保存在方法区的常量池中。而字符串对象是使用 `String` 或 `StringBuilder` 的构造函数创建的对象，保存在堆中。

- **字符串对象在堆中，保存的不是字符串值而是字符串常量在方法区的地址**

- ```java
    String a = new String("a");
    String b = new String("a");
    System.out.println(a == b);// false，因为相当于创建了两个不同的字符串对象，在堆中地址不同，但指向的常量池中的字符串常量是同一个
    
    String a = "a";
    String b = "a";
    System.out.println(a == b);// true，直接比较常量池中的字符串常量，比较是同一个。相当于直接返回字符串常量池中字符串对象 "a" 的引用，不会创建同样的字符串常量
    
    System.out.println(a.hashCode());// 97
    System.out.println(b.hashCode());// 97
    // 证明了二者保存的不是字符串值而是字符串常量在方法区的地址
    ```

- `String s1 = new String("abc");`这句话创建了几个字符串对象？一个或两个

    - 1、首先在堆中创建一个 `String` 对象，此时只开辟了空间但没有初始化值。
    - 2、在常量池中创建字符串常量 "abc"。（如果常量池中已经有了 "abc"，则会直接指向，而非创建新的）
    - 3、使用 `String` 的构造方法，初始化 `String` 指向常量池。

- `String s1 = "abc";`这句话创建了几个字符串对象？零个或一个

    - 在常量池中创建字符串常量 "abc"。（如果常量池中已经有了 "abc"，则会直接指向，而非创建新的）

- 其中 `intern` 方法用来直接返回指向的常量池中的字符串，而非指向堆中的地址

- ```java
    // 在堆中创建字符串对象”Java“
    // 将字符串对象”Java“的引用保存在字符串常量池中
    String s1 = "Java";
    // 直接返回字符串常量池中字符串对象”Java“对应的引用
    String s2 = s1.intern();
    // 会在堆中在单独创建一个字符串对象
    String s3 = new String("Java");
    // 直接返回字符串常量池中字符串对象”Java“对应的引用
    String s4 = s3.intern();
    // s1 和 s2 指向的是堆中的同一个对象
    System.out.println(s1 == s2); // true
    // s3 和 s4 指向的是堆中不同的对象
    System.out.println(s3 == s4); // false
    // s1 和 s4 指向的是堆中的同一个对象
    System.out.println(s1 == s4); //true
    ```



### 4、Java 中只有值传递

- 值传递 ：方法接收的是实参值的拷贝，会创建副本。

- 引用传递 ：方法接收的直接是实参所引用的对象在堆中的地址，不会创建副本，对形参的修改将影响到实参。

- 传递基本数据类型时，a、b 只是 num1、num2 的副本，因此副本修改不会影响到本体

    ```java
    public static void main(String[] args) {
        int num1 = 10;
        int num2 = 20;
        swap(num1, num2);
        System.out.println("num1 = " + num1);
        System.out.println("num2 = " + num2);
    }
    
    public static void swap(int a, int b) {
        int temp = a;
        a = b;
        b = temp;
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }
    
    // 输出
    a = 20
    b = 10
    num1 = 10
    num2 = 20
    ```

- 传递引用类型参数案例1，此时副本内存放的是该对象的地址，直接指向该对象，因此修改时会影响到本体

    ```java
    public static void main(String[] args) {
        int[] arr = { 1, 2, 3, 4, 5 };
        System.out.println(arr[0]);
        change(arr);
        System.out.println(arr[0]);
    }
    
    public static void change(int[] array) {
        // 将数组的第一个元素变为0
        array[0] = 0;
    }
    
    // 输出
    1
    0
    ```

- **传递引用类型参数案例2**，副本内存放的仍然是对象的地址，发生交换时，只是交换了副本内存放的地址，而没有影响本体

    ```java
    public class Person {
        private String name;
       // 省略构造函数、Getter&Setter方法
    }
    
    public static void main(String[] args) {
        Person xiaoZhang = new Person("小张");
        Person xiaoLi = new Person("小李");
        swap(xiaoZhang, xiaoLi);
        System.out.println("xiaoZhang:" + xiaoZhang.getName());
        System.out.println("xiaoLi:" + xiaoLi.getName());
    }
    
    public static void swap(Person person1, Person person2) {
        Person temp = person1;
        person1 = person2;
        person2 = temp;
        System.out.println("person1:" + person1.getName());
        System.out.println("person2:" + person2.getName());
    }
    
    // 输出
    person1:小李
    person2:小张
    xiaoZhang:小张
    xiaoLi:小李
    ```

- 总结

    - 参数是基本类型，拷贝的副本中存放的是基本类型的字面值
    - 参数是引用类型，拷贝的副本中存放的是指向该对象在堆中的地址



### 5、序列化号 serialVersionUID 的作用

- 序列化号 `serialVersionUID` 属于版本控制的作用。反序列化时，会检查 `serialVersionUID` 是否和当前类的 `serialVersionUID` 一致。如果 `serialVersionUID` 不一致则会抛出 `InvalidClassException` 异常。强烈推荐每个序列化类都手动指定其 `serialVersionUID`，如果不手动指定，那么编译器会动态生成默认的 `serialVersionUID`。



### 6、代理模式

- 静态代理，手动写死，在编译层面就已经实现了。实现时需要将被代理的实现类注入到代理类中（通过构造方法），然后调用增强的方法。

    - 说白了，就是把被代理类当做是代理类的一个属性，然后在被代理类执行方法的前后进行扩展处理。
    - 被代理类和代理类需要实现同一个接口，代理类在重写方法时，内部需要调用被代理类基本的方法。
    - **因为静态代理需要实现同一个接口，因此多一个被代理类，就要多加一个对应的代理类。而且接口新增了方法时，代理类也需要进行重写新加的方法并再写一遍新加的方法的代理逻辑**

- **JDK 动态代理**

    - 因为是反射机制调用方法，因此被代理类的所有方法都会被增强。

    - 获取代理类时，传入的被代理类和动态代理器是解耦无关的（是单独的两个参数），因此不同的被代理类可以用相同的动态代理器，而每个动态代理器可以定制本代理器的功能，而不是面向要代理的类定制。

    - 通过 `newProxyInstance()` 静态方法获取代理类

        ```java
        /**
        *	ClassLoader loader，被代理的类
        *	Class<?>[] interfaces，代理指定类需要实现的接口，可以指定多个，是因为要通过多态（接口）去调用方法
        *	InvocationHandler target，指定实现了 InvocationHandler 的实现类，该类用来调用被代理类的方法并自定义处理逻辑
        */
        public static Object newProxyInstance(ClassLoader loader,
                                              Class<?>[] interfaces,
                                              InvocationHandler target)
            throws IllegalArgumentException
        {
            ......
        }
        ```

    - 通过 InvocationHandler 接口的实现类来实现自定义逻辑

        ```java
        public class DebugInvocationHandler implements InvocationHandler {
            /**
             * 被代理类，跟静态代理一样，需要注入被代理类，但是直接通过反射增强类，而非类的某一个方法
             */
            private final Object target;
        
            public DebugInvocationHandler(Object target) {
                this.target = target;
            }
        
        	/**
        	*	重写 invoke 方法，添加自定义的逻辑
        	*/
            @override
            public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
                // 调用方法之前，我们可以添加自己的操作
                System.out.println("before method " + method.getName());
                // 调用原生的方法
                Object result = method.invoke(target, args);
                // 调用方法之后，我们同样可以添加自己的操作
                System.out.println("after method " + method.getName());
                return result;
            }
        }
        ```

    - 实际使用

        ```java
        public static void main(String[] args) {
            SmsServiceImpl smsService = new SmsServiceImpl();// 实例化被代理类
            
            SmsService proxyInstance = (SmsService) Proxy.newProxyInstance(
                smsService.getClass().getClassLoader(),
                smsService.getClass().getInterfaces(),
                new CustomInvocationHandler(smsService));// 获取代理类
            
            proxyInstance.sendMsg("123");// 调用的是原生的方法，实际会去执行 invoke() 方法
        }
        ```

    - **CGLIB 动态代理**

        - JDK 的动态代理需要被代理类实现了接口，可以用 CGLIB 机制来避免这个问题。

        - CGLIB 需要引入相应的 jar 包，是第三方提供的。

        - 自定义方法拦截器，重写其中的 `intercept()` 方法，用于拦截增强被代理类。

            ```java
            /**
             * 自定义方法拦截器
             */
            public class CustomMethodInterceptor implements MethodInterceptor {
                /**
                 * @param o           被代理的对象（需要增强的对象）
                 * @param method      被拦截的方法（需要增强的方法）
                 * @param args        方法入参
                 * @param methodProxy 用于调用原始方法
                 */
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    System.out.println("before" + method.getName());//method参数 可以获取方法名、参数等，详情见反射章节
                    Object invoke = methodProxy.invokeSuper(o, objects);//调用原始方法
                    System.out.println("after" + method.getName());//调用原始方法之后增强
                    return invoke;
                }
            }
            
            // ！！！！！！！！注意：
            // invoke 调用的是增强之后的方法，无论是通过 method 参数还是 methodProxy 调用，都会报错
            // 而 methodProxy 通过 invokeSuper 调用的是原始的没有被增强的方法，可以多次调用，因此 methodProxy 参数的目的在于调用原始方法
            // methodProxy 是生成的一个代理子类，因此需要 invokeSuper 来调用父类的方法，实现拦截方法然后增强
            ```

        - 通过 `Enhancer` 类的 `create()`创建代理类

            ```java
            // 创建动态代理增强类
            Enhancer enhancer = new Enhancer();
            // 设置被代理类加载器（clazz为被代理类）
            enhancer.setClassLoader(clazz.getClassLoader());
            // 设置被代理类
            enhancer.setSuperclass(clazz);
            // 设置方法拦截器
            enhancer.setCallback(new CustomMethodInterceptor());
            // 创建代理类
            return enhancer.create();
            ```

        - 实际使用

            ```java
            // 通过上面的步骤获取到代理类
            SmsService smsService = (SmsService) enhancer.create();
            // 调用方法
            smsService.send("123");
            ```

    - JDK 动态代理和 CGLIB 动态代理对比

        - JDK 动态代理只能代理实现了接口的类或者直接代理接口，而 CGLIB 可以代理未实现任何接口的类。
        - **CGLIB 动态代理是通过生成一个被代理类的子类来拦截被代理类的方法调用，因此不能代理声明为 final 类型的类和方法。**
        - 就二者的效率来说，大部分情况都是 JDK 动态代理更优秀，随着 JDK 版本的升级，这个优势更加明显。



### 7、集合

- **ArrayList**
    - ArrayList 的底层是 Object 类型的数组 `transient Object[] elementData`
    - 当创建 ArrayList 对象时，如果使用的是无参构造器，则初始 elementData 容量为 0，第一次添加时，则扩容 elementData 为 10，如需再次扩容，则扩容 elementData 为原来的 1.5 倍
    - 如果使用的是指定大小的构造器，则初始 elementData 容量为指定大小，如果需要扩容，则直接扩容 elementData 为原来的 1.5 倍
- **HashSet**
    - HashSet 的底层是 HashMap，而 HashMap 的底层是数组+链表+红黑树，HashMap 的数据结构是哈希链表（数组+链表）
    - 数组用来存放第一次散列时的数据，如果出现哈希碰撞，则使用拉链法，将冲突的值加到链表中
    - 第一次添加时，table 数组扩容到 16，临界值(threshold) = 当前容量(capacity) \* 加载因子(loadFactor)，即 12 = 16 * 0.75。如果table 数组使用到了临界值 12，就会扩容到 32 =16 \* 2，新的临界值就是 24 = 32 * 0.75，以此类推(加载因子不变，一直是 0.75)
    - 如果一条链表的元素个数到达 **TREEIFY_THRESHOLD**（默认8），并且 table 的大小 >= **MIN_TREEIFY_CAPACITY**（默认64），就会进行树化（红黑树），如果数组 table 大小不满足条件，则新结点继续 hash 并加到链表后，数组进行一次扩容，直至数组扩容到 64 时，进行树化
    - 每加一个结点，table 的数组内元素总量就加1（不管是直接加到 table 表，还是延伸到链表），所以并不是只添加在 table 时就 +1。
    - 红黑树是一种平衡二叉树（AVL 树），该树实现了自平衡，即左右子树高度差不超过 1，而且有左小右大的特点，查询效率较高



### 8、适配器模式

- 举例理解：电源输出 220V，但是手机要求输入 5V，因此手机无法直接使用电源的功能，此时可以手机使用电源适配器的功能，电源适配器使用电源的功能，即可间接完成手机使用电源。电源适配器用来完成 220V 到 5V 的转换

- 该模式主要用于接口互不兼容的类的协调工作，其中被适配的对象称为适配者（220V 电源），作用于适配者的称为适配器

- **类适配器模式**

    - 适配器继承适配者，用来转换适配者的功能
    - 适配器实现功能接口，表明该适配器提供什么样的功能
    - 使用者（手机）若想使用适配者的某个功能，传入适配器即可

- **对象适配器模式**

    - 适配器中内聚适配者（通过构造方法注入），而非继承，实现高内聚低耦合

    - 适配器实现功能接口，表明该适配器提供什么样的功能

    - 使用者（手机）若想使用适配者的某个功能，传入适配器，并在适配器的有参构造函数中，实例化一个适配者实现注入

    - ```java
        /**
         * src source 资源提供类 被适配者
         */
        public class Voltage220V {
            public int outPut220() {
                int src = 220;
                System.out.println("电源输出电压：" + src);
                return src;
            }
        }
        
        /**
         * 该接口用来定义适配器能提供哪些功能
         */
        public interface IVoltage5V {
            /**
             * 提供输出 5V 电压的功能
             * @return
             */
            int outPut5V();
        }
        
        /**
         * 适配器类，用来实现适配器接口规定的功能
         */
        public class Adapter implements IVoltage5V {
            /**
             * 适配者
             */
            private Voltage220V voltage220V;
        
            /**
             * 通过有参构造注入适配者
             * @param voltage220V
             */
            public Adapter(Voltage220V voltage220V) {
                this.voltage220V = voltage220V;
            }
        
            /**
             * 完成功能的转换适配
             * @return
             */
            @Override
            public int outPut5V() {
                if (voltage220V == null) {
                    return 0;
                }
                int src = voltage220V.outPut220();
                src = src / 44;
                System.out.println("经过适配器后输出电压：" + src);
                return src;
            }
        }
        
        /**
         * 用户实现类
         */
        public class Phone {
            /**
             * 用户的充电需求，需要 5V 电压，因此使用适配器提供 5V
             * @param iVoltage5V 总的适配器接口
             */
            public void charging(IVoltage5V iVoltage5V) {
                int i = iVoltage5V.outPut5V();
                System.out.println("手机使用适配器充电，电压为：" + i);
            }
        }
        
        /**
         * 主类
         */
        public class Demo {
            public static void main(String[] args) {
                Phone phone = new Phone();
                phone.charging(new Adapter(new Voltage220V())); //通过适配器提供 5V 电压，适配器又通过注入将适配者传入
            }
        }
        ```

        

### 9、装饰者模式

- 在不改变原有对象的情况下拓展功能

- 装饰器类需要跟原始类继承相同的抽象类或者实现相同的接口

- 以 IO 流的输入流源代码为例

    ```java
    /**
    * 输入流的抽象类，用来规范子类实现的方法，修饰者类和被修饰者都会直接或间接的继承该类
    */
    public abstract class InputStream{}
    
    /**
    * 是一个抽象的装饰者类，用来将继承了 InputStream 的子类注入进来，不是具体的装饰者
    */
    public class FilterInputStream extends InputStream{
        /**
        * 把被装饰者注入进来（也是通过有参构造）
        */
        protected volatile InputSteam in;
    }
    
    /**
    * 继承了装饰者类，是一个具体的装饰者，用来真正去装饰被装饰者
    */
    class DataInputStream extends FilterInputStream{}
    
    /**
    * 被装饰者类
    */
    public class FileInputStream extends InputStream{}
    
    /**
    * 实际使用
    */
    public class mainDemo {
        public static void main(String[] args) {
            FileInputStream fis = new FileInputStream();// 实例化被装饰者类
            DataInputStream dataInputStream1 = new DataInputStream(fis);// 实例化装饰者类，并包装被装饰者，以此实现对被		装饰者功能的增强与扩展
        }
    }
    ```

    


### 10、Volatile

1. 重排序
    1. 指令重排序主要是为了优化单线程程序的执行速度，但是由于多线程场景下线程执行顺序的乱序执行可能会导致各种问题。
    2. 如在双端检索单例模式中，如果不用 Volatile 修饰单例，那么可能会因为重排序导致已经给单例分配了空间，但是没来得及初始化，就让其他线程返回了未初始化的单例，从而造成异常。
2. 可见性
    1. 同样的在双端检索单例模式中，如果禁止了重排序，可以顺利的让单例初始化完成，但是如果没有刷新回主内存，其他线程拿到的只能是未初始化的副本，因此 Volatile 修饰单例也可以让其发生变化时及时刷新到主内存让所有线程知晓。
    2. 注：可以借助`synchronized` 、`volatile` 以及各种 `Lock` 实现可见性，其他情况下不会刷新局部变量到主内存
3. 非原子性
    1. 假如 i 初始值为 0 且有 Volatile 修饰，`i ++`  执行时，线程 A 从主内存中读取到 i 的值 0 到本线程，此时还没有进行自加时，线程 B 也从主内存中读取到 i 的值 0 到本线程，然后对 i 进行自加，将结果 1 刷新到主内存中，此时线程 A 对 i 进行修改，因为 i 已经拿到了，不需要从主内存中重新拿，因此会对 0 进行自加，然后将结果 1 刷新到主内存中，从而因为非原子性的问题造成数据不一致。
    2. 因此只有线程读取 Volatile 变量时，才会强制读取主内存的值，如果已经读取过了，Volatile 变量再发生修改则不会回过头去重写读取，即“过期不候”。



### 11、原子类

1. 底层用到了 CAS 修改，利用了 CPU 的原子操作指令，比如 x86 架构的 CMPXCHG 指令，因此操作是原子性的
2. CAS 算法涉及三个操作数：内存位置（V）、预期原值（A）和新值（B）。当需要更新这个内存位置的值时，CAS 首先读取当前的值，然后比较内存位置的值和预期原值，如果它们相等，就使用新值更新内存位置的值。如果不相等，则说明这个内存位置的值已经被其他线程更新，此时 CAS 操作将失败，需要重试。
3. 以 `AtomicInteger` 为例，`incrementAndGet()` 方法使用了一个无限循环，每次循环时首先通过 `get()` 方法获取当前的值，然后计算下一个值。如果当前值和预期值相等，就调用 `compareAndSet()` 方法来尝试更新 value 属性的值，如果更新成功，就返回下一个值，否则就继续循环尝试更新。
4. 引用类型原子类
    1. 通过引用类的构造方法，原子更新某一整个类（不是更新类里的某一个字段）。
    2. `AtomicStampedReference`：原子更新带有版本号的引用类型。该类将整数值与引用关联起来，可用于解决原子的更新数据和数据的版本号，可以解决使用 CAS 进行原子更新时可能出现的 ABA 问题。
    3. `AtomicMarkableReference`：原子更新带有标记的引用类型。用来解决是否修改过，将状态戳简化为 T / F



### 12、ThreadLocal

1. 本地线程的变量实际上是存放在 ThreadLocalMap 中，并不是在 ThreadLocal 上，ThreadLocal 只是 ThreadLocalMap 的封装，用来传递变量。ThreadLocal 类中可以通过 `Thread.currentThread()` 获取到当前线程的对象，并通过 `getMap(Thread t)` 访问到该线程的 ThreadLocalMap 对象

2. **`ThreadLocal` 实例通过 `ThreadLocalMap` 对象与当前线程建立关联的底层结构**：`<Thread, Entry<ThreadLocal, Object>>`。即每一个线程有独立的 Entry，这个 Entry 中以 ThreadLocal 实例对象为 key，该 ThreadLocal 实例对象中存放的值为 value。（其中 `Entry<ThreadLocal, Object>` 称为 `ThreadLocalMap`，因此实际上数据存放在 ThreadLocalMap 中）

3. 每个 `ThreadLocal` 实例只能存放一个值，如果需要存放多个值，可以创建多个 `ThreadLocal` 实例。每个 `ThreadLocal` 实例对应一个变量，所以多个 `ThreadLocal` 实例就可以对应多个变量。

4. 因此要取值时，除了指定哪个 Thread 的同时，还要指定是哪个 ThreadLocal 实例

    ```java
    ThreadLocal<UserDTO> localA = new ThreadLocal<>();
    ThreadLocal<String> localB = new ThreadLocal<>();
    
    // 设置值
    localA.set(new UserDTO("name", 12));
    localB.set("value");
    
    // 获取值
    Thread currentThread = Thread.currentThread();// 获取当前线程
    ThreadLocalMap threadLocalMap = ThreadLocal.get(currentThread);// 获取当前线程中所有的 threadLocalMap
    if (threadLocalMap != null) {
        String value = (String) threadLocalMap.get(localB);// 通过指定的 ThreadLocal 得到其中的值
        // localB.get() 也可以直接通过指定的 ThreadLocal 取出其中存放的值
        System.out.println(value); // 输出 "value"
    }
    ```

5. **内存泄露**

    1. **ThreadLocalMap（`Entry<ThreadLocal, Object>`）**中使用的 key 为 ThreadLocal 是弱引用，而 value 为 Object 是强引用。因此，如果 ThreadLocal 没有被外部强引用的情况下，GC 时 key 会被清理掉，但是 value 不会被清理，这样 ThreadLocalMap 中就会出现 key 为 null 的 Entry。假如我们不做任何措施的话，value 永远无法被 GC 回收，这个时候就可能会产生内存泄露。
    2. 实际在使用时，调用 `set()`、`get()`、`remove()` 方法的时候，会清理掉 key 为 null 的记录。使用完 `ThreadLocal`方法后，最好手动调用`remove()`方法，清空该 `ThreadLocal` 实例中的值
    3. 系统设计 key 为弱引用是防止 ThreadLocalMap 中的 **key** 造成内存泄露（**ThreadLocal  使用频率低，会自动 GC 防止内存泄露**）
    4. 系统设计 value 为强引用是防止 ThreadLocalMap 中的 **value** 造成内存泄露（**object 使用频率高，只能手动清理防止内存泄露**）

6. **为什么 ThreadLocalMap 中的 key 设计为弱引用**

    1. ThreadLocal 的使用周期可能很短，如果不使用弱引用，那么即使 ThreadLocal 不再使用，其对应的 ThreadLocalMap 中的 Entry 仍然会存在，并且会一直占用内存，导致内存泄漏。
    2. ThreadLocalMap 使用弱引用作为 key，当 ThreadLocal 对象没有被其他强引用引用时，ThreadLocal 对象就会被垃圾回收掉，对应的 entry 也会被清除掉，从而避免内存泄漏问题。

7. **为什么 ThreadLocalMap 中的 value 设计为强引用**

    1. 因为 ThreadLocal 中存储的值可能会被频繁地读写，如果将 value 设计成弱引用，那么在读取或写入值时，需要每次都创建新的弱引用对象，这样会导致频繁的对象创建和回收，进而导致性能下降。
    2. 因此 ThreadLocalMap 中的 value 通常是设计成强引用的，由程序员自己负责清理对应的值。

8. **实际应用-链路追踪**

    1. 当请求发送到服务 A 时，服务端生成一个类似`UUID`的`traceId`字符串，将此字符串放入当前线程的`ThreadLocal`中，在调用服务 B 时，将`traceId`写入到请求的`Header`中，服务 B 在接收请求时会先判断请求的`Header`中是否有`traceId`，如果存在则写入自己线程的`ThreadLocal`中。
    2. 这样生成的 `traceId`字符串就会变成一条链路，追踪请求从开始到结束的链路
    3. 同样的也可以用在 RPC 或者 OpenFeign 远程调用的跟踪





### 13、AQS

1. 能简单且高效地构造出应用广泛的大量的锁和同步器，如 `ReentrantLock`，`Semaphore`，`ReentrantReadWriteLock`，`SynchronousQueue`等
2. **原理**
    1. 如果请求的临界资源是空闲的，则将该临界资源的线程设置为当前有效的工作线程，并将该临界资源设置为锁定状态
    2. 如果请求的临界资源被占用，则由 AQS 利用 CLH 队列锁机制将暂时获取不到锁的线程加入到队列中
    3. CLH 队列是逻辑上的双向队列链表，AQS 将每个请求临界资源的线程封装成该队列的一个结点（Node）来实现锁的分配。一个结点表示一个线程，保存着线程的引用、当前结点在队列中的状态及其前驱后继



### 14、线程安全的并发容器

1. HashMap：ConcurrentHashMap
    1. 在进行读操作时(几乎)不需要加锁，而在写操作时通过锁分段技术只对所操作的段加锁而不影响客户端对其它段的访问。
2. List：CopyOnWriteArrayList
    1. 读取时完全不用加锁，写入时也不会阻塞读操作，是因为当 List 需要被修改的时候，并不修改原有内容，**而是对原有数据进行一次复制**，将修改的内容写入副本。写完之后，再将修改完的副本替换原来的数据。
    2. 写入时加了锁，保证了同步，避免了多线程写的时候会 copy 出多个副本出来。
3. LinkedList：ConcurrentLinkedQueue
    1. 阻塞队列：加锁实现
    2. 非阻塞队列：CAS 实现



### 15、IO 模型

1. **BIO (Blocking I/O)**

    1. 属于同步阻塞 IO 模型
    2. 应用程序发起 read 系统调用后，会一直阻塞，直到内核把数据拷贝到用户空间
    3. 无法应对高并发的场景

2. ### NIO (Non-blocking I/O)

    1. 属于同步非阻塞 IO 模型。

    2. 应用程序会一直发起 read 系统调用，在内核没有准备好数据时，期间会通过轮询操作查询数据是否准备完成而避免一直阻塞

    3. 等待数据从内核空间拷贝到用户空间的这段时间内，线程才会阻塞，等待内核把数据拷贝到用户空间，

    4. 不断轮询也会消耗 CPU 资源

    5. **I / O 多路复用**

        1. 属于 NIO 非阻塞，用来改善不断轮询的情况，通过减少无效的系统调用，减少了对 CPU 的消耗

        2. 线程首先发起 select / poll / epoll 系统调用，询问内核数据是否已经就绪

        3. 内核数据准备完成后，用户线程再发起 read 调用，调用过程中才会阻塞（即数据从内核 -> 用户）

            > - select 调用：内核提供的系统调用，它支持一次查询多个系统调用的可用状态。几乎所有的操作系统都支持。
            > - epoll 调用 ：linux 2.6 内核，属于 select 调用的增强版本，优化了 IO 的执行效率。

        4. Java NIO 中，使用 selector 作为多路复用器。selector 可以注册多个通道，并通过轮询的方式检查这些通道上是否有事件发生。当有事件发生时，**先经过 selector 收集**，然后 selector 返回对应的 selectionKey，应用程序根据事件类型进行相应的处理

        5. selector 通过一个线程管理同时管理多个通道，而不需要为每个通道创建一个独立的线程，从而减少了线程的创建和上下文切换开销，提高了系统的性能和资源利用率。

3. **AIO (Asynchronous I/O)**

    1. 属于异步非阻塞 IO 模型
    2. 基于事件和**回调**机制实现的，也就是应用操作之后会直接返回，不会堵塞在那里，当后台处理完成，操作系统会通知相应的线程进行后续的操作。