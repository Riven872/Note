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



## 二、Mysql

### 1、索引

#### B 树和 B+ 树的区别

1. 二者都类似于二叉查找树（BST），与 二叉平衡树（AVL）的区别只是 AVL 需要平衡，而 BST 不需要
2. B 树的所有节点既存放键值 key，也存放数据 Data；而 B+ 树只有叶子节点存放 key 和 Data，非叶子节点只存放 Key
3. B 树的所有节点是独立的；而 B+ 树的叶子节点之间有双向链表连接
4. B 树的检索的过程相当于对范围内的每个节点的关键字做二分查找，可能还没到叶子节点就已经检索结束了；而 B+ 树的检索效率很稳定，**任何查找都是从根结点到叶子节点，检索顺序稳定**
5. B+ 树效率更高
    1. 因为 B+ 树的所有非叶子节点只保存 key，因此大小同样的磁盘可以保存更多的节点元素，层数更少，IO 操作也更少
    2. B+ 树检索顺序稳定，性能也稳定
    3. B 树的叶子节点是独立的，因此需要先二分查找找到范围的下限，然后不断的中序遍历找到范围的上限，费时费力。B+ 树因为有联表，通过二分查找之后找到范围下限，再遍历链表的顺序找到上限即可，效率较高

#### 使用联合索引时的最左前缀匹配原则

1. Mysql 根据联合索引中的字段顺序，从左到右依次到查询条件中去匹配，如果查询条件中存在与联合索引中最左侧字段相匹配的字段，则就**会使用该字段过滤一批数据**
2. 直至联合索引中全部字段匹配完成，或者在执行过程中遇到范围查询（如 **`>`**、**`<`**）才会停止匹配。对于 **`>=`**、**`<=`**、**`BETWEEN`**、**`like`** 前缀匹配的范围查询，并不会停止匹配
3. 在使用联合索引时，可以将区分度高的字段放在最左边，这也可以过滤更多数据
4. 举例理解（从扫描区间方向考虑）：有联合索引（a, b, c）
    1. 条件：`where a > 1 and b = 2`，扫描区间依然是 `(1, ∞)`，与 b 没有关系，因此 b 字段没有使用到该联合索引，即停止匹配
    2. 条件：`where a >= 1 and b = 2`，扫描区间是 `[1,2,∞)`，当 a = 1 时，b 字段是有序的，因此可以通过 b = 2 条件减少需要扫描的二级索引记录范围，因此 b 字段匹配到了联合索引
    3. 条件：`WHERE a BETWEEN 2 AND 8 AND b = 2`，BETWEEN 包含了 value1 和 value2 边界值，类似于 >= and =<，因此用到了联合索引
    4. 条件：`WHERE name like 'j%' and age = 22`，同条件二，**可以从符合 name = 'j' and age = 22 条件的第一条记录时开始扫描，而不需要从第一个 name 为 j 的记录开始扫描**

#### 索引下推

1. 在非聚簇索引遍历过程中，对**索引**中包含的字段先做判断，过滤掉不符合条件的记录，减少回表次数。

2. 索引下推是指在查询条件中如果某些条件列已经被索引覆盖，则可以**在索引上完成查询**，而不必回到表上去检索数据。这个过程是在查询执行计划中完成的，而不是在查询的字段中触发的。具体来说，如果查询条件中使用到了索引列，并且所有需要查询的列都被索引覆盖，则可以使用索引下推来加速查询。

3. 举例子：有字段 `a, b, c`，其中  `a, b, c` 都有单独的索引或者是联合索引，那么在 `select a,b,c` 时，每查出一条数据，直接通过对应的索引判断该数据是否符合 `where` 中的条件，不符合直接筛选掉，从而过滤掉不需要的数据，提高性能。如果没有索引下推，则会根据条件之一查询出数据，然后再根据条件之二筛选其中数据，性能较低。

4. 因此如果触发了索引下推，那么在索引扫描的过程中就直接过滤掉不符合条件的行，只将符合条件的行返回给查询结果，而不是重复筛选查询结果。这样做可以显著提高查询性能，尤其是当数据量较大时。

5. **更详细的例子：**

    > 假设有一个表，有字段A、B、C、D，并且 A、B 两个字段分别建立了单独的索引，执行查询语句 `SELECT * FROM table WHERE A=1 AND B=2 AND C=3 AND D=4` 时，发生索引下推
    >
    > MySQL 会先尝试使用 A 和 B 的单独索引进行查找。因为 A 和 B 建立了单独的索引，所以可以使用索引进行查找。
    >
    > 首先，MySQL 会从 A 的单独索引中查找值为 1 的行。找到符合条件的行后，将其主键值保存下来。接着，MySQL 会从 B 的单独索引中查找值为 2 的行，找到符合条件的行后，同样将其主键值保存下来。
    >
    > 接下来，**MySQL 将两个保存的主键值进行比较，只有当两个主键值相同的时候，才继续向下查询**。MySQL 会使用这个主键值去从原表中查询对应的行，然后再判断 C 和 D 两个条件是否符合。
    >
    > 如果 C 和 D 建立了单独索引，MySQL 也会尝试使用这两个索引进行查找。如果没有单独的索引，则需要扫描整张表。

#### 前缀索引

1. 仅限于字符串类型的字段
2. 如果不限制长度，则会将整个字符串建立索引树。如果使用前缀索引，则会根据限定的字符串个数建立前缀索引，是一种时间换空间的做法，只需要字符串前几位，就可以达到很高的区分度，而不用整个字段，降低了索引树的大小
3. 如果是以 `like "%foo"` 这种的 % 开头，则不会触发前缀索引，反之 `like foo%` 则会触发

#### 索引失效的常见情况

1. 模糊查询
    1. 当使用 LIKE、%、_ 等模糊查询时，MySQL 无法利用索引的 B+ 树结构进行匹配，因此会导致索引失效。例如，查询语句为：`SELECT * FROM table WHERE name LIKE '%abc%'`，这种情况下无法使用索引。
    2. 是因为无法命中前缀索引
2. 对索引列进行函数运算
    1. 当对索引列进行函数操作时，MySQL 无法使用索引，而需要进行全表扫描。例如，查询语句为：`SELECT * FROM table WHERE YEAR(create_time) = 2021`，这种情况下无法使用索引。
3. 对索引列进行运算
    1. 当对索引列进行运算时，MySQL 无法使用索引，而需要进行全表扫描。例如，查询语句为：`SELECT * FROM table WHERE id / 10 = 100`，这种情况下无法使用索引。
4. 在索引列上使用 NOT、<、> 等操作符
    1. 当在索引列上使用 NOT、<、>、<> 等操作符时，MySQL 无法使用索引，而需要进行全表扫描。例如，查询语句为：`SELECT * FROM table WHERE id <> 100`，这种情况下无法使用索引。
5. 对于联合索引，不符合最左前缀匹配原则
    1. 如果一个表有多列组成的索引，那么查询语句中必须使用到索引的第一列，否则索引会失效。例如，索引为（a,b,c），查询语句为：`SELECT * FROM table WHERE b = 2`，这种情况下无法使用索引。
6. 数据过滤的数据太多
    1. 如果数据过滤的数据太多，那么MySQL就会认为全表扫描比使用索引更快。例如，查询语句为：`SELECT * FROM table WHERE id > 1000000`，如果表中 id 的总数很少，那么 MySQL 就会认为使用全表扫描更快。
7. 隐式类型转换
    1. 当在查询中使用的值类型和索引列类型不一致时，MySQL 会进行隐式类型转换。这种情况下索引也会失效。例如，查询语句为：`SELECT * FROM table WHERE age = '18'`，如果 age 列的类型是整型，那么 MySQL 就会进行隐式类型转换，导致索引失效。
    2. 即查询时使用了字符串，但实质该字段是 int，那么会发生类型转换，进而无法命中索引

### 2、日志

#### 慢查询日志（slow query log）

1. 记录了执行时间超过 `long_query_time` 的所有查询语句，该参数默认为 10s，通常设置为 1s

#### 二进制日志（bin log）

1. ##### binlog 日志概述

    1. 主要记录了对 MySQL 数据库执行了更改的所有操作
    2. 数据库执行的所有 DDL 和 DML 语句
    3. 表结构变更：CREATE、ALTER、DROP TABLE 等
    4. 表数据修改：INSERT、UPDATE、DELETE 等
    5. 不包括 SELECT、SHOW 这种不会对数据库结构造成更改的操作
    6. 并不是只对数据库产生了修改时才会记录进 binlog，即使表结构和表数据修改操作没有对数据库造成更改，也依然会被记录进 binlog
    7. 使用 `show binary logs;` 查看所有二进制日志的列表
    8. 使用 `show binlog events in 'binlog.000001' limit 10;` 查看指定日志的具体内容，这里需要指定 limit，否则内容会太多。或者使用内置的 binlog 查看工具 mysqlbinlog，解析二进制文件
    9. binlog 是通过追加的方式进行写入，大小没有限制。如果设置了最大容量，那么文件大小到达阈值时，会自动生成新的 binlog 文件，不会出现被覆盖的情况

2. ##### binlog 的格式

    1. Statement 模式：每一条会修改数据的 sql 都会被记录在 binlog 中，如 INSERTS、UPDATES、DELETES

    2. Row 模式（推荐）：每一行的具体变更事件都会被记录在 binlog 中

    3. Mixed 模式：Statement 模式与 Row 模式的混合。默认是 Statement 模式，少数特殊场景会自动切换到 Row 模式

    4. 相较于 Row 模式，Statement 模式下的日志文件更小，磁盘 IO压力也小，性能更好，但是准确性较低

    5. 简单来说，Statement 模式记录了 SQL 语句，Row 模式记录实际发生了什么变化

    6. Statement 模式的 binlog：binlog 记录了一条 `UPDATE user SET age=30 WHERE id=1;` 的 SQL 语句。

        ```mysql
        # at 347
        #210105  1:28:13 server id 1  end_log_pos 383   Query   thread_id=5     exec_time=0     error_code=0
        use test_db;
        SET TIMESTAMP=1641359293;
        UPDATE user SET age=30 WHERE id=1;
        # at 383
        #210105  1:28:13 server id 1  end_log_pos 414   Xid = 4
        COMMIT/*!*/;
        ```

    7. Row 模式的 binlog：binlog 记录了一条 Write_rows 操作，表示插入了一行数据。同时，还会记录插入的数据的具体信息，如 id=2, name='Bob', age=30。

        ```mysql
        # at 273
        #210105  1:26:47 server id 1  end_log_pos 313   Write_rows: table id 6 flags: STMT_END_F
        ### INSERT INTO user
        ### SET
        ###   id=2
        ###   name='Bob'
        ###   age=30
        # at 313
        #210105  1:26:47 server id 1  end_log_pos 342   Xid = 3
        COMMIT/*!*/;
        ```

3. ##### binlog 的作用

    1. **主从复制**，依靠 binlog 来同步数据，保证数据的一致性
        1. 主从复制原理：
            1. 主库将数据库中数据的变化写入到 binlog
            2. 从库连接主库
            3. 从库会创建一个 I/O 线程向主库请求更新的 binlog
            4. 主库会创建一个 binlog dump 线程来发送 binlog，从库中的 I/O 线程负责接收
            5. 从库的 I/O 线程接收到之后同步的生成 relay log
            6. 从库的 SQL 线程读取 relay log 同步数据到本地（也就是再执行一遍 SQL）
            7. relay log（中继日志）：
                1. 是主从复制过程中产生的日志，很多方面跟 binlog 差不多
                2. relay log 针对的是主从复制中的从库
    2. **数据恢复**
        1. 前提是启用了 binlog 日志情况下
        2. 误删了整个数据库的情况下，可以使用 binlog 帮助恢复
        3. 使用 `show variables like 'log_bin';` 查看数据库是否启用了 binlog 日志

4. ##### binlog 的刷盘时机（持久化到硬盘的时机）

    1. 对于 InnoDb 存储引擎，事务在执行过程中，会先把日志写入到 binlog cache 中，只有在事务提交的时候，才会把 binlog cache 中的日志持久化到磁盘上的 binlog 文件中。（暂存到内存的速度要比直接持久化速度快）
    2. 一个事务的 binlog 不能被拆开，无论这个事务多大，也要确保一次性写入。如果超过了 binlog cache 的大小，就需要暂存到磁盘。
    3. `sync_binlog` 参数控制 binlog 的刷盘时间，取值范围是 0 - N，默认 1。
        1. 0：不去强制要求，由系统自行判断何时写入磁盘
        2. 1：每次提交事务的时候都要将 binlog 写入磁盘
        3. N：每提交 N 个事务，才会将 binlog 写入磁盘

5. ##### 什么情况下会重新生成新的 binlog

    1. MySQL 服务器停止或重启
    2. 使用 `flush logs` 命令后
    3. binlog 文件大小超过 `max_binlog_size` 的阈值后
    4. 以上三种情况，MySQL 会重新生成一个新的日志文件，且文件序号递增

#### redo log（重做日志）

1. ##### 保证事务的持久性

    1. 往 MySQL 修改数据最终都是存在于页中的，为了减少磁盘 I/O，会有一个 Buffer Pool 的区域，MySQL 先将页缓存到 Buffer Pool，然后再经过 Buffer Pool 将数据持久化到磁盘
    2. redo log 记录了页的修改，比如某个页面的偏移量修改的值等等，redo log 中每一条记录包含了表空间号、数据页号、偏移量、具体修改的值等
    3. 防止 Buffer Pool 中对应的页修改还没来得及持久化到磁盘。
    4. 重启之后能恢复未能写入磁盘的数据，保证了事务的持久性，redo log 也使 MySQL 有了崩溃恢复的能力

2. ##### 写入方式

    1. 并不是每次修改 Buffer Pool 中的页都会直接刷盘，思想是批量提交而非单次提交，从而提高性能
    2. 采用循环的方式写入，会有一个 check point 表示要擦除的位置，write pos 表示 redo log 当前记录写到的位置。当 write pos 追上 check point 时，表示 redo log 写满了，这时 MySQL 无法执行更新操作，等执行 Check Point 刷盘后，Check Point 向后移动，此时才能继续写入 redo log 日志，不再继续阻塞

#### undo log（撤销日志）

1. ##### 保证事务的原子性

    1. 每一个事务对数据的修改都会被记录到 undo log，当执行事务过程中出现错误或者需要执行回滚操作，MySQL 利用 undo log 将数据恢复到事务开始之前的状态
    2. undo log 属于逻辑日志，记录的是 SQL 语句，比如事务执行一条 DELETE 语句，那么 undo log 就会记录一条对应的 INSERT 语句

2. ##### 其他作用

    1. 在 MVCC 中，当用户读取一行记录，若该记录已经被其他事务占用，当前事务可以通过 undo log 读取之前的行版本信息，以此实现非锁定读取

### 3、并发事务的控制方式

#### 事务隔离（并发事务需要解决的前提知识）

1. ##### 并发事务的问题（部分）

    1. 脏读：一个事务 A 读取数据（20）并进行了修改（20 -> 15），这个事务 A 对其他事务是可见的，但未提交。此时另一个事务 B 读取到了第一个事务 A 中修改后的数据（15），若此时第一个事务进行回滚（15 -> 20），那么第二个事务 B 读到的修改后的数据就是脏数据（15）
    2. 不可重复读：一个事务 A 读取数据（20）且此时 A 没有提交，之后另一个事务 B 修改了该数据（20 -> 15）,紧接着事务 A 再读取该数据时，发生了变化（15），这样就发生了一个事务中两次读但是结果不同，即不可重复读
    3. 幻读：一个事务 A 读取了几行数据后，接着一个并发事务 B 插入了一些数据，此时事务 A 再去查询时，会发现跟第一次查询相比多了几条原本不存在的数据，即幻读

2. ##### 隔离级别（部分）

    1. RC（读取已提交）：允许读取并发事务已经提交的数据，可以阻止脏读，但是幻读或不可重复读仍有可能发生。
    2. RR（可重复读）：对同一字段的多次读取结果都是一致的，除非数据是被本身事务自己所修改，可以阻止脏读和不可重复读，但幻读仍有可能发生。

#### 锁（可以看做是悲观控制模式）

1. ##### 表级锁与行级锁

    1. MySQL 通过读写锁来控制并发，也就是读时可共享读，写时独占写。读锁允许多个事务同时获取。
    2. MyISAM 仅支持表级锁，InnoDb 支持表级锁和行级锁，行级锁可以只对相关的记录（一行或多行上锁），性能更高
    3. 表级锁是除全局锁之外粒度最大的一种锁，是对非索引字段加的锁，对当前操作的整张表加锁，实现简单，资源消耗的也比较少，加锁快，不会出现死锁。但是锁冲突的概率高，并发效率低
    4. 行级锁是粒度最小的一种锁，是**针对索引字段加的锁**，只针对当前操作的行记录进行加锁。行级锁能大大减少数据库操作的冲突。其加锁粒度最小，并发度高，但加锁的开销也最大，加锁慢，会出现死锁。
    5. 针对索引字段加的锁理解：
        1. 当修改的字段命中索引时：因为其他事务想要修改相同的数据时，也要走索引，因此只要锁住了索引，就可以避免其他事务修改相同的数据，只锁定索引时，范围小，也就是行级锁的优势
        2. 当修改的字段没有命中索引时：此时需要锁定修改的行，其他事务想要修改相同的数据时，因为没有索引，也会直接修改对应的行，此时锁住了对应的行，就可以避免其他事务修改相同的数据

2. ##### 行级锁的类型

    1. 记录锁（Record Lock）：对单个行记录上锁
    2. 间隙锁（Gap Lock）：锁定一个范围，不包括记录本身
    3. 临键锁（Next-Key Lock）：上述二者的结合，锁定一个范围，包括记录本身，主要目的是为了解决幻读问题
    4. 在 InnoDB 默认的隔离级别 REPEATABLE-READ 下，行锁默认使用的是 Next-Key Lock。但是，如果操作的索引是唯一索引或主键，InnoDB 会对 Next-Key Lock 进行优化，将其降级为 Record Lock，即仅锁住索引本身，而不是范围。

3. ##### 意向锁

    1. 用来快速判断是否可以对某个表使用表锁。
    2. 是由数据引擎自己维护的，用户无法手动操作意向锁，在为数据行加共享 / 排他锁之前，InooDB 会先获取该数据行所在在数据表的对应意向锁。
    3. 意向锁是表级锁，共有两种：
        - 意向共享锁（Intention Shared Lock，IS 锁）：事务有意向对表中的某些记录加共享锁（S 锁），加共享锁前必须先取得该表的 IS 锁。
        - 意向排他锁（Intention Exclusive Lock，IX 锁）：事务有意向对表中的某些记录加排他锁（X 锁），加排他锁之前必须先取得该表的 IX 锁。

#### MVCC（可以看做是乐观控制模式）

1. ##### 快照读（一致性非锁定读）

    1. 快照读（一致性非锁定读），就是单纯的 SELECT 语句。其中快照即历史版本，每行记录可能存在多个历史版本，在快照读的情况下，如果读取的记录正在执行 UPDATE / DELETE 操作，读取操作不会等待该记录排它锁的释放，而是会去读取行的一个快照

    2. InnoDb 使用快照读的场景

        1. 在 RC 隔离级别条件下，对于快照数据，一致性非锁定读总是读取被锁定行的最新一份快照数据
        2. 在 RR 隔离级别条件下，对于快照数据，一致性非锁定读总是读取本事务开始时的行数据版本

    3. 通常的做法是加一个版本号或时间戳字段，在更新数据的同时版本号 + 1 或更新时间戳。在查询时，将当前可见的版本号与对应记录的版本号进行比对，如果记录的版本小于可见版本，那么表示该记录可见。
    4. 因此快照读适合对于数据一致性要求不是特别高且追求极致性能的业务场景
    5. 在 InnoDB 存储引擎中，多版本控制 (MVCC) 就是对非锁定读的实现
2. ##### 当前读（一致性锁定读）
    1. 在当前读下，读取的是数据最新的版本，会对读取到的数据加锁
3. ##### InnoDB 对 MVCC 的实现
    - `MVCC` 的实现依赖于：**隐藏字段、Read View、undo log**。在内部实现中，`InnoDB` 通过数据行的 `DB_TRX_ID` 和 `Read View` 来判断数据的可见性，如不可见，则通过数据行的 `DB_ROLL_PTR` 找到 `undo log` 中的历史版本。每个事务读到的数据版本可能是不一样的，在同一个事务中，用户只能看到该事务创建 `Read View` 之前已经提交的修改和该事务本身做的修改
1. ###### 内置的隐藏字段
    1. 在内部，InnoDB 存储引擎为每行数据添加了三个隐藏字段
    2. `DB_TRX_ID（6字节）`：表示最后一次插入或更新该行的事务 id。此外，`delete` 操作在内部被视为更新，只不过会在记录头 `Record header` 中的 `deleted_flag` 字段将其标记为已删除
    3. `DB_ROLL_PTR（7字节）`： 回滚指针，指向该行的 `undo log` 。如果该行未被更新，则为空
    4. `DB_ROW_ID（6字节）`：如果没有设置主键且该表没有唯一非空索引时，`InnoDB` 会使用该 id 来生成聚簇索引
    2. ###### ReadView（快照）
        1. 主要是用来**做可见性判断**，里面保存了 “当前对本事务不可见的其他活跃事务”
        2. `m_low_limit_id`：目前出现过的最大的事务 ID+1，即下一个将被分配的事务 ID。大于等于这个 ID 的数据版本均不可见
        3. `m_up_limit_id`：活跃事务列表 `m_ids` 中最小的事务 ID，如果 `m_ids` 为空，则 `m_up_limit_id` 为 `m_low_limit_id`。小于这个 ID 的数据版本均可见
        4. `m_ids`：`Read View` 创建时其他未提交的活跃事务 ID 列表。创建 `Read View`时，将当前未提交事务 ID 记录下来，后续即使它们修改了记录行的值，对于当前事务也是不可见的。`m_ids` 不包括当前事务自己和已提交的事务（正在内存中）
        5. `m_creator_trx_id`：创建该 `Read View` 的事务 ID
    3. ###### undo log
        1. undo log 在 MVCC 中的作用是，当读取记录时，若该记录被其他事务占用或当前版本对该事务不可见，则可以通过 undo log 读取之前的版本数据，以此实现非锁定读
        2. **`insert undo log`** ：指在 `insert` 操作中产生的 `undo log`。因为 `insert` 操作的记录只对事务本身可见，对其他事务不可见，故该 `undo log` 可以在事务提交后直接删除。不需要进行 `purge` 操作（与 MVCC 关系不大，因此不做讨论）
        3. **`update undo log`** ：`update` 或 `delete` 操作中产生的 `undo log`。该 `undo log`可能需要提供 `MVCC` 机制，因此不能在事务提交时就进行删除。提交时放入 `undo log` 链表，等待 `purge线程` 进行最后的删除。
        4. 不同事务或者相同事务的对同一记录行的修改，会使该记录行的 `undo log` **成为一条链表，链首就是最新的记录**，链尾就是最早的旧记录。
    4. ###### 数据可见性算法
        1. 在 `InnoDB` 存储引擎中，创建一个新事务后，执行每个 `select` 语句前，都会创建一个快照（Read View），**快照中保存了当前数据库系统中正处于活跃（没有 commit）的事务的 ID 号**。其实简单的说保存的是系统中当前不应该被本事务看到的其他事务 ID 列表（即 m_ids）。当用户在这个事务中要读取某个记录行的时候，`InnoDB` 会将该记录行的 `DB_TRX_ID` 与 `Read View` 中的一些变量及当前事务 ID 进行比较，判断是否满足可见性条件
    5. ###### RC 和 RR 隔离级别下 MVCC 的差异
        1. 在 RC 隔离级别下的 **`每次select`** 查询前都生成一个`Read View 快照` (m_ids 列表)
        2. 在 RR 隔离级别下只在事务开始后 **`第一次select`** 数据前生成一个`Read View 快照`（m_ids 列表）
    
    6. ###### MVCC + Next-key-Lock 解决幻读
        1. `InnoDB`存储引擎在 RR 级别下通过 `MVCC`和 `Next-key Lock` 来解决幻读问题
        2. 执行普通 SELECT，此时会以 MVCC 快照读的方式读取数据
            1. 在快照读的情况下，RR 隔离级别只会在事务开启后的第一次查询生成 `Read View` ，并使用至事务提交。所以在生成 `Read View` 之后其它事务所做的更新、插入记录版本对当前事务并不可见，实现了可重复读和防止快照读下的 “幻读”
        3. 执行 `select...for update/lock in share mode`、`insert`、`update`、`delete` 等当前读
            1. 在当前读下，读取的都是最新的数据，如果其它事务有插入新的记录，并且刚好在当前事务查询范围内，就会产生幻读！`InnoDB` 使用 `Next-key Lockopen in new window` 来防止这种情况。当执行当前读时，会锁定读取到的记录的同时，锁定它们的间隙，防止其它事务在查询范围内插入数据。只要我不让你插入，就不会发生幻读



## 三、Redis

### 1、Redis 数据结构的应用

#### Redis 实现购物车

1. 由于购物车中的商品频繁修改和变动，购物车信息一般使用 Hash 存储
2. 用户 id 为 key，商品 id 为 field，商品信息为 value，这样就可以完成一个用户对应多个商品信息的关系。其中商品信息包含多个字段信息，可以采用 Json 格式存储
3. 在用户往购物车中添加商品时，首先根据客户 id 找到对应的 Hash，再根据商品 id 找到对应的 filed，如果有则更新商品数量，如果没有则以该商品 id 为 filed，商品信息为 value 新建 Hash
4. 在用户删除购物车中的商品时，只需要根据商品 id 删除对应的 Hash 即可
5. 在用户查询购物车中所有的商品时，先获取用户 id 对应的所有 Hash 结构，然后将所有 field 转换成商品信息返回（商品 id + 商品字段信息）

#### Redis 实现排行榜

1. 使用 Sorted Set 数据结构（也就是ZSet）进行排序，ZSet 与 Set 相比，多了一个权重的参数 score，可以使集合中的元素能够按照 score 进行有序排列，还可以通过 score 的范围来获取元素列表。因此自然有序且唯一的结构 ZSet 适合用来实现排行榜。

2. Redis 中只保存了排行榜需要展示的数据，因此如果需要用户的具体信息，还要对应的去数据库中查询

3. 还可以将 ZSet 集合的 Key 设置为日期字符串，实现按照指定日期内的排序

    1. 如 20230506 存放 2023年5月6日 的排序集合

    2. 使用 ZUNIONSTORE 指令将所有集合中的数据汇集，然后返回前 N 的集合（比如最近 7 天内的总排行）

        ```lua
        ZUNIONSTORE new_zset_collections n 20230506 20230507 ....
        ```

#### 使用 Set 实现抽奖系统

1. Set 特点是集合内的元素无序，但唯一
2. 实现抽奖系统
    1. 向指定集合中添加一个或多个元素
    2. 随机移除指定集合中一个或多个元素，并获取移除的元素作为中奖元素，适合不允许重复中奖的场景。
    3. 随机获取指定集合中指定数量的元素，适合允许重复中奖的场景。
3. 注：Set 还常用于网站 UV 统计（数据量巨大的场景还是 `HyperLogLog`更适合一些）、文章点赞、动态点赞、需要获取多个数据源交集、并集和差集的场景：共同好友(交集)、共同粉丝(交集)、共同关注(交集)、好友推荐（差集）、音乐推荐（差集） 、订阅号推荐（差集+交集） 等等、需要随机获取数据源中的元素的场景：抽奖系统、随机点名等等

#### 使用 BitMap 统计活跃用户

1. 创建一个 Bitmap，Bitmap 的长度为用户总数。假设用户总数为 N，则创建一个长度为 N 的 Bitmap。
2. 当一个用户活跃时，将对应的 Bitmap 的位设置为 1，即将 Bitmap 中的第 i 位设置为 1，表示第 i 个用户活跃了。可以把用户的 id 当做 BitMap 中的第 i 位，用来表示某用户。
3. 一张 BitMap 表示一天的活跃量，如果需要统计多日期的，可以将多个位图进行与、或等运算，进而可以得出某用户在某一段时间内是否活跃、总活跃天数等
4. 使用 Redis Bitmap 统计活跃用户的好处是空间利用率高，每个位只占用 1 bit 的空间，而不是 1 byte，可以大大节省内存空间。此外，Bitmap 支持位操作，计算速度非常快。



### 2、Redis 线程模型

#### Redis 单线程模型

1. 对于读写命令而言，Redis 一直是单线程模型。在 Redis 4.0 版本之后引入了多线程来执行一些大键值对的异步删除操作。Redis 6.0 版本之后引入了多线程来处理网络请求（提高网络 IO 读写性能）
2. Redis 的核心模式是文件事件处理器
    1. 使用 I / O 多路复用程序来同时监听多个网络请求，并根据请求类型不同关联不同的事件处理器
    2. 当被监听的网络请求准备好执行连接应答（accept）、读取（read）、写入（write）、关 闭（close）等操作时，与操作相对应的文件事件就会产生，这时文件事件处理器就会调用网络请求之前关联好的事件处理器来处理这些事件。
    3. **虽然文件事件处理器以单线程方式运行，但通过使用 I / O 多路复用程序来监听多个网络请求**，文件事件处理器既实现了高性能的网络通信模型，又可以很好地与 Redis 服务器中其他同样以单线程方式运行的模块进行对接，这保持了 Redis 内部单线程设计的简单性。
    4. **I/O 多路复用技术的使用让 Redis 不需要额外创建多余的线程来监听客户端的大量连接，降低了资源的消耗**（和 NIO 中的 `Selector` 组件很像）。

#### Redis 引入多线程的原因

1. Redis 的性能瓶颈主要是网络传输的延迟的和内存空间，因此引入多线程是为了提高网络 IO 的读写性能
2. Redis 的多线程只是在网络数据的读写这类耗时操作上使用了，执行命令仍然是单线程顺序执行。因此不需要担心线程安全问题

#### Redis 的后台线程

1. 通过 `bio_close_file` 后台线程来释放 AOF / RDB 等过程中产生的临时文件资源。
2. 通过 `bio_aof_fsync` 后台线程调用 `fsync` 函数将系统内核缓冲区还未同步到到磁盘的数据强制刷到磁盘（ AOF 文件）。
3. 通过 `bio_lazy_free`后台线程释放大对象（已删除）占用的内存空间。



### 3、Redis 内存淘汰策略

#### 概述

1. Redis 内存淘汰机制主要是为了解决内存不足的问题，当 Redis 内存使用达到了 maxmemory 限制时，Redis 会根据设置的内存淘汰策略，淘汰一些键值对来释放内存空间。

#### 策略（注意 volatile 与 allkeys 的范围）

1. volatile-lru（least recently used）：**从已设置过期时间的数据集**（`server.db[i].expires`）中挑选**最近最少使用**的数据淘汰。
2. volatile-ttl：从已设置过期时间的数据集（`server.db[i].expires`）中挑选**将要过期**的数据淘汰。
3. volatile-random：从已设置过期时间的数据集（`server.db[i].expires`）中**任意选择**数据淘汰。
4. allkeys-lru（least recently used）：**从数据集（`server.db[i].dict`）中**，移除**最近最少使用**的 key（这个是最常用的）。
5. allkeys-random：从数据集（`server.db[i].dict`）中**任意选择**数据淘汰。
6. no-eviction：禁止驱逐数据，也就是说当内存不足以容纳新写入数据时，新写入操作会报错。没人使用
7. volatile-lfu（least frequently used）：从已设置过期时间的数据集（`server.db[i].expires`）中挑选**最不经常使用**的数据淘汰。
8. allkeys-lfu（least frequently used）：当内存不足以容纳新写入数据时，从数据集（`server.db[i].dict`）中，移除**最不经常使用**的数据。



### 4、布隆过滤器

#### 概述

1. 布隆过滤器在 Redis 的上层，相当于 Redis 的保险
2. 具体做法是把所有存在的请求值都存放在布隆过滤器中，当有用户请求时，首先判断过滤器中是否有数据，如果不存在则直接返回错误信息，如果存在则查询缓存是否有对应的数据
3. 存在误判的情况：布隆过滤器说某个元素存在，小概率会误判。布隆过滤器说某个元素不在，那么这个元素一定不在。
4. 数据结构是**位数组（由 0、1 组成的数组，类似 BitMap，但是一维的）**，然后根据指定的哈希函数进行映射

#### 原理

1. 当一个元素加入过滤器中时
    1. 首先会使用过滤器中的哈希函数对元素值进行计算，得到对应的哈希值
    2. 根据得到的哈希值，在位数组中把对应的下标置为 1
2. 判断一个元素是否存在于过滤器中
    1. 将指定元素进行对应的哈希计算
    2. 得到值之后，找到对应的下标位置，如果值为 1，则说明该值在过滤器中
3. 如果存放的数据越多，那么出现哈希碰撞的可能性就越大，因此会出现误判，相应的，该元素删除的难度也很大。

#### 场景

1. 判断给定数据是否存在：如应对 Redis 的缓存穿透问题
2. 去重：比如爬给定网址的时候对已经爬取过的 URL 去重



### 5、常见的缓存更新策略（解决一致性问题）

#### 旁路缓存模式（Cache Aside Pattern）

1. 适合读多写少的场景
2. 写操作时的步骤
    1. 先更新数据库
    2. 直接删除缓存
3. 读操作时的步骤
    1. 先从缓存中读取数据，如果命中则直接返回
    2. 没有命中则读取数据库中的数据
    3. 再把数据库中的数据重建至缓存
4. 要删除缓存而不是更新缓存的原因
    1. 防止对服务端资源造成浪费：删除缓存更加直接，频繁更新缓存可能会导致没有访问到的缓存进行了更新，造成资源浪费
    2. 防止产生数据不一致问题：并发场景下，更新缓存产生数据不一致的几率更大
5. 在写数据的过程中，先删除缓存，再更新数据库
    1. 不可以，会造成数据不一致
    2. 请求 A 先把缓存中的数据删除（15），此时还未来得及更新数据库（20 -> 15），请求 B 发现缓存已删除无法命中（15），则直接访问数据库拿取数据（15），最后请求 A 更新数据库中的值（20 -> 15）。这样就会导致请求 B 拿到的值是旧值（15）
6. :star:在写数据的过程中，先更新数据库，再删除缓存（推荐结合缓存延迟双删使用）
    1. 也会造成数据不一致，但几率极低
    2. 缓存中值（15）不存在，线程 A 无法命中缓存，则去数据库中取值（15），此时线程 B 更新数据库（15-> 20），线程 A 继续完成缓存重建，将旧值写入缓存（15）
    3. 几率低：
        1. 更新的值不在缓存中
        2. 读请求 + 写请求并发
        3. 更新数据库时间要比读数据库 + 写缓存时间短
7. **不管是先删除缓存再更新数据库，还是先更新数据库再删除缓存，都会出现数据库中的值还没有更新完，线程就拿着旧值去重建缓存了**，只不过后者几率较低

#### 缓存延迟双删策略

1. 是对旁路缓存模式的一种优化，**旁路缓存提供的思路是，先更新数据库，再删除缓存，只存在极低的可能性造成数据不一致**
2. 在写数据时，先更新数据库，然后删除缓存，然后休眠一小段时间再删除一次缓存，即缓存延迟双删。目的在于第二次延迟删除缓存时，数据库中的值已经更新为新的值。
3. 更新数据库的线程在更新完成数据库之后，发送一条延时信息到消息队列，通知消费者删除缓存，从而达到延迟的效果
4. **结合消息队列用来解决更新数据库操作失败或删除缓存操作失败**
    1. 通过异步操作，将重试操作写到消息队列中，让消费者去进行操作的重试，直至成功
5. 各种策略可能不会达成强一致性，但是性能高，而且也可以达成最终一致性



### 6、Redis 的主从复制

#### 概述

1. 类似于 MySQL 的读写分离，主节点负责写，从节点复制读。而主从复制的目的是保证主从的数据一致性，而且保证了 Redis 的高可用，也是 Redis 哨兵模式和集群模式的基石
2. 主从复制方案基于 Redis Replication（默认使用异步复制），进行配置 Slave 与 Master，配置完成后，主从节点之间的数据同步会自动进行

#### 主从复制模式下节点处理过期数据的方式

1. Redis 常用的过期数据删除策略有两个
    1. 惰性删除：只会在取出 Key 的时候才会对数据进行过期检查，如果 key 已经过期，仍然会返回对应的 value，然后才会被异步的删除（也算命中了缓存）。这样对 CPU 最友好，但是可能会造成大量的过期 key 没有被删除
    2. 定期删除：每隔一段时间抽取一批 key 进行检查，如果有过期 key 则进行删除操作。这样对内存最友好，但是会加重 CPU 轮询的负担
    3. :star:定期删除 + 惰性删除：定期遍历所有键值对，找出过期的键值对进行删除，并在获取某个键的值时，检查该键是否过期，如果过期则删除。
2. 客户端读取从节点是否会读取到过期数据
    1. Redis 3.2 之后，客户端读从库首先会判断该 key 是否过期，如果过期则删除对应的 value 并返回 null
    2. 如果采用了 Expire 设置了过期时间，那么表示的是执行该命令之后的 TTL。因此如果主从节点之间同步执行命令时存在网络延迟等原因，客户端就可能会读取到过期的数据

#### 主从节点之间同步数据的方式

1. 全量重同步：初次建立主从关系时，从节点需要从主节点上复制所有的数据
    1. 从节点向主节点发送 SYNC 命令，请求进行复制
    2. 主节点收到 SYNC 命令后，会创建一个 RDB 快照文件，并将该文件的文件名和文件大小作为状态信息回复给从节点
    3. 从节点接收到主节点的状态信息后，会开启一个子进程，通过全量复制方式从主节点获取 RDB 文件，并将文件持久化到磁盘
    4. 从节点加载并应用 RDB 文件，完成数据的全量同步
    5. 从节点向主节点发送 PSYNC 命令，请求增量同步
    6. 主节点记录从节点的偏移量，将从节点加入自己的复制列表中，从此开始进行增量同步
    7. 适用于初次同步或数据不一致的情况，同步效率较低，会占用大量的带宽和内存。主节点在同步期间会被阻塞，对主节点的性能有一定影响。全量同步过程中主节点仅负责发送数据，从节点需要接收并处理数据，因此从节点的性能也会受到影响
2. 部分重同步：当主节点和从节点之间的网络断开，再次重新连接时，从节点只会请求主节点上部分缺失的数据，以减少同步数据量，提高同步效率和稳定性
    1. 主节点将新的写命令缓存到内存中，并记录在复制积压缓冲区中
    2. 当复制积压缓冲区中的命令数量达到一定阈值或者时间间隔超过一定阈值后，主节点会将积压缓冲区中的命令发送给从节点
    3. 从节点接收到主节点发送的增量复制命令后，执行这些命令，将自己的数据同步到主节点上

#### 主从全量复制使用 RDB 而不是 AOF 的原因

1. RDB 文件存储的内容是经过压缩的二进制数据，文件很小。AOF 存储的是每一次写命令，类似 binlog 日志，通常会比 RDB 文件要大，因此采用 RDB 可以节省带宽，速度更快
2. 使用 RDB 文件恢复数据，直接解析并还原数据即可，不需要一条条的执行命令，速度快。而 AOF 文件需要依次执行命令，恢复大数据集时，RDB 速度会更快
3. AOF 需要选择合适的刷盘（持久化）策略，如果不当的话会影响 Redis 的正常运行

#### 主从复制的缺陷

1. Slave 升级为 Master 时，需要手动指定（哨兵模式解决）
2. 包括哨兵模式，不支持横向扩展来缓解写的压力以及解决缓存数据量过大的问题（集群模式解决）
3. 注：横向扩展和纵向扩展
    1. 横向：在现有的服务器集群中增加新的服务器，增加集群的处理能力
    2. 纵向：现有的服务器升级为更高配置的服务器，例如增加 CPU 核心、增加内存、更换更快的硬盘



### 7、Redis Sentinel（哨兵模式）

#### 哨兵模式概述

1. 为了应对普通的主从模式下，Master 宕机后需要从 Slave 中手动选择新的 Master，同时需要修改应用的主节点地址，还要命令所有的 Slave 去同步新的主节点
2. Sentinel 只是 Redis 的一种运行模式，不提供读写服务，默认运行在 26379 端口，依赖于 Redis 工作。
3. Sentinel 实现 Redis 集群的高可用，只是在主从复制集群模式下，多了一个哨兵的角色去监控每个节点的状态。当 Master 节点出现故障时，Sentinel 自动实现故障转移，根据规则选出新的 Master

#### 配置

1. 有专门的配置文件 `sentinel.conf`
2. 指定要监视的 Master 节点地址，不用写 Slave 地址，Sentinel 会根据监控的 Master 节点自动去获取相应的 Slave 节点。而且可以选择监控多个 Master。
3. 设定节点宕机多久才会被认为是失效，并指定几个哨兵认为宕机时，才是真正的失效

#### 作用

1. 监控
    1. 监控所有的 Redis 节点（包括 Sentinel）的状态是否正常
2. 故障转移
    1. 如果一个 Master 出现故障，那么 Sentinel 自动实现故障转移，根据规则选出新的 Master
3. 通知
    1. 通知 Slave 新的 Master 连接信息，让 Slave 执行 replicaof，成为新 Master 的 Slave
4. 配置提供
    1. 客户端连接 Sentinel 请求 Master 的地址，如果发生故障转移，Sentinel 会通知新的 Master 链接信息给客户端

#### 检测节点是否下线

1. 主观下线：Sentinel 节点认为某个 Redis 节点下线了，但是不确定，需要其他 Sentinel 的投票
2. 客观下线：超过半数的 Sentinel 节点认为某个 Redis 节点下线了，那么是真的下线了
3. 每个 Sentinel 节点会以每秒钟一次的频率向整个集群中的所有节点（Master、Slave、Sentinel）发送一个 PING 命令，如果在规定时间内没有有效回复（PONG、-LOADING、-MASTERDOWN），则认为主观下线
4. 如果检测到 Slave 节点下线，Sentinel 不会进行处理
5. 进行故障转移时，Sentinel 会推选出一个 Leader 角色进行故障转移

#### Sentinel 选出新 Master 的方式

1. 首先判断 Slave 的优先级
    1. 通过配置文件进行权重的设置（为 0 则代表没有选举的资格）
2. 然后判断复制进度
    1. 选择出数据最完整，也就是与原 Master 数据最接近的节点，也就是复制进度最快的
3. 最后判断运行 id（run id）
    1. 通常前两轮已经选举成功了
    2. 如果权重和复制进度一样快，那就选择 run id 小的作为新 Master
    3. 每个 Redis 节点启动时，都有一个 40 字节的随机字符串作为运行 id

### 8、Redis Cluster（集群模式）

#### 使用集群模式的原因

1. 缓存的数据量太大
2. 并发量要求太大
3. 主从复制（包括哨兵模式）的本质是提高 Redis 服务的整体可用性和吞吐量，但是不支持横向扩展缓解写压力以及缓存数据量太大的问题
4. 因此需要使用 Redis 切片集群这种方案，部署多台 Redis 主节点（Master），节点之间平等，没有主从关系，同时对外提供读 / 写服务。客户端请求通过路由规则转发到目标 Master 上。

#### Redis Cluster 架构

1. 为了保证高可用，集群中的每个 Master 节点可以通过主从复制的模式，给每个 Master 配置一个或多个 Slave。
2. 方便横向扩展，只需要新增 Redis 节点到集群中即可
3. Redis Cluster 是 Redis 官方推出的集群解决方案，支持动态扩容和缩容，具备主从复制、故障转移（内置 Sentinel 机制，因此不需要单独部署 Sentinel）
4. 基础的架构中，至少有 3 个 Master 和 3 个 Slave，其中 Slave 不对外提供读服务，主要用来保障 Master 的高可用，当 Master 出现故障时进行替代
5. 添加新节点时，只需要重新分配哈希槽即可。同样的，在删除节点时，需要先将该节点的哈希槽移动到其他的节点上，才可以进行删除，否则会出错

#### Redis Cluster 分片方式

1. 创建并初始化 Redis Cluster 时，Redis 会自动平均的分配 16384 个哈希槽给各个节点，不需要手动分配
2. 当客户端发送请求时，会根据请求的 key 经过公式计算，得到对应的哈希槽，再根据映射关系表，找到哈希槽对应的节点
    1. 如果哈希槽是当前节点负责，那么直接响应客户端的请求返回结果
    2. 如果哈希槽不是找到的节点负责，那么该节点会返回重定向错误，并告知正确的节点位置，客户端再直接向正确的目标节点发送请求并更新缓存中映射表的信息
3. 出现误差的原因是：Redis Cluster 内部可能会重新分配哈希槽（如动态扩容），这样可能会导致客户端缓存的映射表有错误

#### 扩容期间仍会提供服务

1. 扩容和缩容的本质是重新分片，动态迁移哈希槽
2. 提供了两种重定向机制，保证在扩容和缩容期间可以对外提供服务
    1. ASK 重定向：临时重定向，后续的查询仍然发送到旧节点
    2. MOVED 重定向：永久重定向，后续的查询发送的新节点

#### Redis Cluster 各节点之间的通信

1. Redis Cluster 是一个典型的分布式系统，分布式系统中的各个节点需要相互通信，使用的是 Gossip 协议，每个 Redis 节点中都维护了一份集群的状态信息
2. 利用 Gossip 通信，可以实现各个节点的健康检测、故障转移、自动切换等
