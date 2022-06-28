###### JVM、JDK、JRE

- JVM Java虚拟机 Java Virtual Machine
- JDK Java开发工具包 Java Development Kit
- JRE Java运行环境 Java Runtime Environment
- JDK = JRE + (javac、java、javap等)
- JRE = JVM + Java核心类库
- .java(源文件) ->（javac编译）->.class->（java运行）->JVM虚拟机上运行
- 一个Java源文件中最多只能有一个public类，当有一个public类时，源文件名必须与之一致，否则无法编译
- 如果源文件中没有public类，那么源文件的名字可以任意取，这样编译是没问题的
- 一个java文件中可包含多个main方法，不同类中都可以包含main方法，JVM运行时选择指定类的main方法运行



###### 代码块

- 类什么时候被加载到方法区？
    - 创建对象实例时(new)
    - 创建子类对象实例，父类也会被加载
    - 使用类的静态成员时（静态属性，静态方法）

- static代码块随着类的加载而执行，且只会执行一次

- 普通代码块，每创建一个对象就执行一次（跟类加载没有关系 ）

- 可以理解成：普通代码块是构造器的补充

- 创建一个对象时，在一个类的调用顺序是

    - 静态代码块和静态属性初始化优先级相同，按定义顺序进行调用

    - 普通代码块和普通属性初始化优先级相同，按定义顺序进行调用
    - 构造方法

- 构造器的最前面其实隐含了super()和调用普通代码块——即先去找父类的构造函数，然后是父类的代码块，最后是自身的代码块
- 创建一个子类对象时（继承关系），他们的静态代码块、静态属性初始化、普通代码块、普通属性初始化、构造函数调用顺序如下
    - 父类的静态代码块和静态属性
    - 子类的静态代码块和静态属性
    - 父类的普通代码块和普通属性初始化
    - 父类的构造方法
    - 子类的普通代码块和普通属性初始化
    - 子类的构造函数

- 静态代码块只能直接调用静态成员，普通代码块可以调用任意成员 



###### 单例设计模式（饿汉式）

​	—还没有使用对象时，已经在内部实例化好了，可能创建了对象但是没有使用，因此适用于重量级

- 构造器私有化，防止直接New
- 类的内部创建对象
- 向外暴露一个静态的公共方法
- 代码实现

```java
class Foo{
    private String name;
    //2、在类的内部直接创建（为了能在static方法返回Foo对象，因此需要static修饰）
    private static Foo foo = new Foo("嗷嗷嗷");
    //1、将构造器私有化
    private Foo(String name){
        this.name = name;
    }
    //3、提供一个公共的static方法，返回Foo对象
    public static Foo getInstance(){
        return foo;
    }
}

//4、直接通过类而非实例化得到对象
Foo instance = Foo.getInstance();
```



###### 单例设计模式（懒汉式）

​	—使用对象时，才进行实例化

- 构造器私有化
- 定义一个static静态属性对象
- 提供一个public的static方法
- 只有当用户使用getInstance方法时，才进行实例化，后面再次调用getInstance方法时，返回的仍然是第一次创建的对象

```java
class Bar{
    private String name;
    //2、定义一个static静态属性对象
    private Bar bar;
    //1、将构造器私有化
    private Foo(String name){
        this.name = name;
    }
    //3、提供一个public的static方法，返回Bar对象
    public static Bar getInstance(){
        if(bar == null){
            bar = new Bar("嗷嗷嗷");
        }
        return bar;
    }
}

//4、直接通过类而非实例化得到对象
bar instance = Bar.getInstance();
```

- 两个单例模式对比

    - 二者的主要区别是创建对象的时机不同。

        - 饿汉式：类加载时就实例化

        - 懒汉式：在使用时才实例化
    - 饿汉式不存在线程安全问题，懒汉式存在线程安全问题
    - 饿汉式存在浪费资源的可能



###### static关键字

```java
public static final foo = a
```

- `static`和`final`一起使用，表明foo是一个常量，且存放在静态空间，不会在程序运行时被释放，它永远占着内存直到程序终止
- 当一个常数或字符串我们需要在程序里反复反复使用的时候，我们就可以把它定义为static final，这样内存就不用重复的申请和释放空间



###### 内部类

- 定义在外部类局部位置上（如方法内）：局部内部类、匿名内部类
- 定义在外部类的成员位置：成员内部类、静态内部类
- 局部内部类可以直接访问外部类的成员
- 外部类在方法中，可以创建内部类的对象，然后调用其方法即可
- 外部其他类不可访问局部内部类，因为局部内部类的作用域仅在外部类的方法中
- 如果外部类和局部内部类的成员重名时，遵循就近原则，若想访问外部类成员，则需要使用`外部类名.this.成员`去访问
- 外部其他类不可访问匿名内部类，作用域问题



###### 包装类

- 手动装箱

```java
int a = 1;
Integer b = Integer.valueOf(a);//自动装箱底层使用
//或
Integer b = new Integer(a);
```

- 手动拆箱

```java
int c = b.intValue();
```

- 自动拆装箱就是在手动的基础上封装的



###### Integer包装类

````java
Integer i1 = new Integer(127);
Integer i2 = new Integer(127);
System.out.println(i1 == i2);//F

Integer i3 = 127;
Integer i4 = 127;
System.out.println(i3 == i4);//T

//自动装箱时，如果范围在-127~128之间，则直接从IntegerCache中取，如果超出范围，则是通过new实例化来装箱，故i1、i2实例化不是同一个对象，后者相当于直接值类型比较

Integer m = 128;
int n = 128;
System.out.println(m == n);//T
//只要有基本数据类型，则判断的就是值是否相同
````



###### String类

```java
String a = "hsp";
String b = new String("hsp");
//a指向常量池
//b指向堆中的value属性，value再指向常量池
//因此a == b是false

String c = a + b;//c在堆中，用StringBuilder进行Append追加
String d = "hsp" + "edu";//d在常量池中，因为是常量相加  
```

- toString（）方法的底层也是new,因此toString()的返回值指向堆，堆再指向常量池
- .intern()返回的是字符串是常量池中的
- String是final类型的，不可变长度。StringBuffer是可变长度的。
- String保存的是字符串常量，里面的值不能修改，每次String类的更新实际上就是更改地址（即不用每次都创建新对象），效率低`底层是private final char[] value`。指向常量池。
- StringBuffer保存的是字符串常量，里面的值可以修改，StringBuffer的更新实际上可以更新其内容，而不用每次更新地址（除非空间不足需要另申请），效率高，`底层是char[] value`，指向堆。
- StringBuilder不是线程安全的，但如果是单线程使用，则优先选择StringBuilder。
- StringBuilder对象字符序列仍然是存放在其父类AbstractStringBuilder中的char[] value中，因此字符序列是堆中。
- String的优势是复用性，如果创建了一个"hello"的字符串，那么该字符串会一直在常量池中，不用重复建立，新String也可以指向它。



###### 单列集合(相当于List)

- Collection : List、Set

- List集合类中
    - 元素有序（即添加顺序和取出顺序一致）、且可重复
    - 每个元素都有对应的索引顺序，通过`.get(索引数字)`来获取指定索引的值

- （相当于List）List : Vector、ArrayList、LinkedList 

- ArrayList:
    - ArrayList放入值类型时，会自动装箱为对象类型再放入
    - ArrayList是线程不安全的，但是效率高
    - ArrayList的底层是Object类型的数组`transient Object[] elementData //transient表示该属性不会被序列化`
    - 当创建ArrayList对象时，如果使用的是无参构造器，则初始elementData容量为0，第一次添加时，则扩容elementData为10，如需再次扩容，则扩容elementData为1.5倍
    - 如果使用的是指定大小的构造器，则初始elementData容量为指定大小，如果需要扩容，则直接扩容elementData1.5倍

- Vector:
    - Vector的底层是Object类型的数组`protected Object[] elementData`
    - Vector是线程同步的，即线程安全，但是效率不高
    - 扩容机制跟ArrayList类似，无参时，初始为10，每次扩2倍，有参时，直接扩2倍

- LinkedList:
    - LinkedList实现了双向链表和双端队列的特点，底层维护了一个双向链表
    - 增删效率高，改查效率低

- Set : HashSet、TreeSet

- Set集合类中
    - 元素无序（添加和取出顺序不一致）、且没有索引
    - 不允许元素重复，所以最多包含一个null

- HashSet:
    - HashSet的底层是HashMap，而HashMap的底层是数组+链表+红黑树，HashMap的数据结构是哈希链表（数组+链表）

    - 第一次添加时，table数组扩容到16，临界值(threshold)\*加载因子(loadFactor)，即16*0.75=12

    - 如果table数组使用到了临界值12，就会扩容到16\*2=32，新的临界值就是32*0.75=24，以此类推(加载因子不变，一直是0.75)

    - 如果一条链表的元素个数到达TREEIFY_THRESHOLD（默认8），并且table的大小>=MIN_TREEIFY_CAPACITY（默认64），就会进行树化（红黑树），如果数组table大小不满足条件，则新结点继续hash并加到链表后，数组进行一次扩容，直至数组扩容到64时，进行树化

    - 每加一个结点，table的数组内元素总量就加1（不管是直接加到table表，还是延伸到链表），所以并不是只添加在table时就+1

    - HashSet不能添加相同的元素/数据

      - ```java
        HashSet set = new HashSet();
        set.add("tom");//添加成功
        set.add("tom");//无法添加
        //"tom"是字符串常量，指向常量池，因此二者是一致的
        
        set.add(new Dog("king"));//添加成功
        set.add(new Dog("king"));//添加成功
        //两个new Dog只是其中一个属性相同，但二者不是同一对象，因此添加成功
        
        set.add(new String("tom"));//添加成功
        set.add(new String("tom"));//无法添加
        ```

      - 添加元素时，先进行hashCode()，判断插入数组的下标，如果该下标位置没有元素，则直接插入，若该下标位置有元素，则进行hashCode()比较，若相同则插入失败，反之插入成功，插入到数组下标对应的链表中。因此可以重写hashCode()和hashCode()来自定义HashSet插入重复元素的规则
      - 添加元素的顺序和取出元素的顺序不同是因为每次插入元素时，都要进行一次hashCode()来判断插入元素的位置，因此顺序跟哈希值有关而非插入顺序
      - 注意：判断位置不只是用hashCode(),还会用其他的计算方法来保证更大的离散程度，以减少哈希冲突(但是主要还是计算哈希码，其余就是加减乘除某些给定的值)

    - ```java
      //其中Person已经重写了HashCode和Equals，只要name和id相同，则相同
      HashSet set = new HashSet();
      Person p1 = new Person(1001, "AA");
      Person p2 = new Person(1002, "BB");
      set.add(p1);//添加成功
      set.add(p2);//添加成功
      p1.name = "CC";
      set.remove(p1);//进行remove时，会通过HashCode进行定位所要删除元素的位置，但此时p1.name已经改变了，因此HashCode之后找不到原p1对象，因此删除失败
      sout(set);//会输出p1、p2
      set.add(new Person(1001, "CC"));//p1是先添加后改元素，因此p3的HashCode与p1不同，添加成功
      sout(set);//会输出p1、p2、p3
      set.add(new Person(1001, "AA"));//此时p1的name已经改变，与p4不相同，因此p4在p1的后面行程链表
      sout(set);
      ```
      
    - 
    
    - LinkedHashSet:
    
      - LinkedHashSet是HashSet的子类，底层是LinkedHashMap，而LinkedHashMap底层是数组+双向链表
      - LinkedHashSet根据元素的HashCode()来决定元素的存储位置，同时使用链表维护元素的次序，这使得元素看起来是以插入顺序保存的（第一个存入的元素作为头结点，然后每新增一个元素，就在双向链表中新增一个结点，因此是按插入顺序保存的，哈希值的不同决定了结点是在哪个数组下标里保存）
      - LinkedHashSet的双向链表可以保证添加顺序和取出顺序相同，但是仍不能存储相同的元素（原理同HashSet）
    
- TreeSet：

    - TreeSet的底层是TreeMap

    - TreeSet可以排序

    - 当使用无参构造器时，TreeSet仍然是无序的

    - 有参构造器接收一个比较器，通过比较器来排序（匿名内部类，面向接口编程），可以补一下比较器的知识

    - ```java
        TreeSet treeSet = new TreeSet(new Comparator(){
           @Override
            public int compare(Object o1, Object o2){
                //调用String的compareTo方法进行字符串大小大小比较，如果大小相同，则视为相同元素，不会加入到TreeSet中
                return ((String)o1).compareTo((String)o2);
                //通过字符长度进行比较，如果长度相同，则视为相同元素，不会加入到TreeSet中
                //return ((String)o1).length() - ((String)o2).length();
            }
        });
        ```

    - 如果用有参的构造器传入了比较器，那么判断是否重复交给传入的比较器进行判断。如果用无参的构造器，那么会有默认的比较器来判断是否元素重复，而保证不会重复添加（默认比较器的底层还是字符串的比较器）

    - ```java
        TreeSet treeSet = new TreeSet();
        TreeSet.add(new Person());
        //会报错，因为调用的无参的构造器，在底层会将Person转换为Comparable类型，但由于没有实现Comparable接口，因此会报类型转换的错误
        
        //法一：自定义类实现Comparable接口
        Class Peron implement Comparable{
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }
        }
        
        //法二：调用有参的构造器并传入自定义比较器
        TreeSet treeSet = new TreeSet(new Comparator() {
            @Override
            public int compare(Object o) {
                return 0;
            }
        });
        ```

    - 

- foreach的底层其实就是迭代器

    

###### 双列集合(相当于Dictionary)

- Map:HashMap、HashTable、Properties、TreeMap、LinkedHashMap
- HashMap:
    - 元素无序（添加顺序和取出顺序不一致），因为Key的底层是HashMap，根据散列函数进行放置元素
    - key、value可以是Object类型的数据，会封装到`HashMap$Node`对象中，但是通常情况下，使用String类型当做Key值
    - Map中的key不可以重复，原因跟HashSet一致，如果key一致，则value进行替换（后添加的覆盖以前的）
    - Map中的Value可以重复
    - Key和Value都可以为null，但是Key为null的只能有一个，原因是不可重复
    - key和value之间存在单向一对一的关系，即通过指定的key总能找到对应的value
    - Key-Value特点：
      - k-v最后是`HashMap$Node foo = newNode(hash, key, value, null);//用newNode方法传入哈希值、key和value的值`
      - k-v为了方便遍历，会创建EntrySet集合，该集合存放的元素类型是Entry，而一个Entry对象就有k,v 即`EntrySet<Entry<K,V>>`，在源码中为`transient Set<Map.Entry<K,v>> entrySet;`
      - entrySet中，定义类型是Map.Entry，但是实际上存放的类型还是HashMap$Node，是因为`Node<K,V> implements Map.Entry<K,V>`
      - 把HashMap$Node对象存放到entrySet就方便我们的遍历，因为Map.Entry提供了重要的方法K getKey(); V getValue();
      - 关系就是底层为HashMap$Node->entry->Set（底层是HashMap$Node，然后封装到entry里面，最后再放到Set中，最终只是指向的关系，没有新增元素）

- Map接口的遍历方法

  - containsKey：查找键是否存在、keySet：获取所有的键、entrySet：获取所有关系、values：获取所有的值

  - 法一：先取出所有的Key，通过Key取出对应的Value，然后再通过增强for或迭代器去遍历

    - ```java
      Set foo = map.keySet();//通过Set类型接收，得到所有的key值集合，可以采用遍历Set的方法来遍历Map
      for(Object bar : foo){
          map.get(bar);//map.get(key)得到对应的value值
      }
      ```

  - 法二：把所有的value取出，通过增强for或迭代器去遍历

    - ```java
      Collection foo = map.values();//得到所有的value值集合，可以使用所有Collection使用的遍历方法
      for(Object bar : foo){
          bar;
      }
      ```

  - 法三：通过EntrySet来获取k-v

    - ```java
      Set entrySet = map.entrySet();
      for(Object entry : entrySet){
          Map.Entry m = (Map.Entry)entry;//一对k-v就是一个Entry(实际类型为Map.Entry)，向下转型，将Object类型的转换为Map.Entry
          m.getKey();
          m.getValue();
      }
      //一对k-v就是一个Entry，存放Entry的集合是entrySet(实际类型为Map.entrySet)
      ```

- HashMap线程不安全

- HashMap的扩容机制跟HashSet一样（因为HashSet的底层就是HashMap）
- Hashtable:
  - k和v都不允许设置为null，否则会抛出空指针异常
  - 线程安全
  - 其他使用方法基本与HashMap一致
  - 底层有数组 Hashtable$Entry[]，初始化大小为11（Hashtable$Entry是指Hashtable的内部类Entry）
  - 临界值计算同上

- Properties：
  - 继承自Hashtable
  - 常用于从xxx.properties文件中，加载数据到Properties类对象中，并进行读取和修改（xxx.properties文件通常作为配置文件）

- TreeMap：
  - 与HashMap、HashTable并列关系
  - 使用默认的构造器，创建的TreeMap是无序的
  - 有参构造器接收一个比较器，通过比较器来排序（匿名内部类，面向接口编程），可以补一下比较器的知识（同TreeSet）
  - 如果根据比较器比较后，键相同，那么新元素无法加入而不是覆盖
- 总结：
  - 一组对象（单列）：Collection接口
    - 允许重复：List
      - 增删多：LinkedList（底层是双向链表）
      - 改查多：ArrayList（底层是Object类型的可变数组）
    - 不允许重复：Set
      - 无序：HashSet（底层是HashMap,即哈希表：数组+链表+红黑树）
      - 排序：TreeSet
      - 插入顺序和取出顺序一致：LinkedHashSet（数组+双向链表）
  - 一组键值对（双列）：Map接口
    - 键无序：HashMap（底层是哈希表：数组+链表+红黑树，且jdk7以前无红黑树）
    - 键排序：TreeMap
    - 键插入和取出顺序一致：LinkedHashMap（双链表）
    - 读取文件：Properties



###### 泛型

- 编译时，检查添加元素的类型，提高了安全性（在编译层面就会暴露错误）

- 减少了类型转换的次数，提高效率

- 泛型类型只能用引用类型，不能用值类型：不能用int，要用Integer

- 在指定泛型具体类型后，可以传入该类型或者其子类类型

- ```java
    ArrayList list = new ArrayList();
    ArrayList<Object> list = new ArrayList<>();
    //二者等价，如果不指定泛型，则默认为Object类型
    ```

- 使用泛型的数组无法直接初始化，只可以声明，因为数组不确定类型时，就无法在内存中开空间

- 静态方法和属性不能使用泛型，因为静态是和类相关的，在类加载时，对象还没有创建，所以，如果静态方法和静态属性使用了泛型，JVM就无法完成初始化（加载类->加载静态成员->创建类->指定泛型类型），而此时泛型类型还没有指定就加载泛型静态成员就是错误的

- 泛型接口的类型，在继承接口或者实现接口时确定

- 泛型方法，可以使用类声明的泛型，也可以使用自己声明的泛型

  - ```java
    public void hi(T t){
        ...
    }
    //该方法不是泛型方法，而是方法使用了该类声明的泛型
    
    public <E> void say(E e){
        ...
    }
    //该方法是泛型方法，且使用了自己声明的泛型
    ```

- 泛型不具备继承性

    - ```java
        List<Object> list = new List<String>();
        //这个是错误的，虽然String是Object的子类，但泛型不具备继承性
        ```

- `<?>`支持任意泛型类型
- `<? extends Foo>`支持Foo类以及Foo的子类（即规定了泛型的上限）
- `<? super Bar>`支持Bar类以及Bar的父类（即规定了泛型的下限）



###### 多线程

- 创建线程的两种方式
    - 1、继承Thread类，重写run方法
    - 2、实现Runnable接口，重写run方法

- Thread：
    - 当一个类继承了Thread时，该类就可以当做线程使用
    - 重写run方法，并在其中写上自己的业务代码
    - Thread类中的run方法实际是实现了Runnable接口中的run方法
    - 如果直接调用run方法，只会进行普通的调用。而使用start才会启动线程，因此真正实现多线程的是start0而不是run
    - 底层：调用start方法后，start调用start0方法，而start0方法是本地的(native修饰)，由JVM去调用。
    - start调用start0后，该线程并不一定会立马执行，只是将线程变成了可运行状态，具体什么时候执行，取决于CPU，由CPU统一调度

- Runnable：

    - java是单继承，如果一个类继承了另一个的情况下依然要使用多线程，则可以选择实现Runnable接口

    - 但是Runnable内没有start方法，因此使用时，需要先实例化Thread类，将实现了Runnable接口的类放入Thread类中，如下

        ```java
        //Dog类继承了Animals类，因此无法再继承Thread，此时就需要实现Runnable接口
        class Dog extends Animals implements Runnable {
            @Override
            public void run(){
                ...
            }
        	...
        }
        //使用start
        Dog dog = new Dog();
        Thread thread = new Thread(dog);//将实现类放入Thread中
        thread.start();//此时就可以启动多线程
        
        //注意：在Thread中放入了dog，使用了设计模式中的静态代理模式
        ```

        

- 总结：

    - 通过继承Thread或者实现Runnable来创建线程本质上没有区别，因为Thread类本身就实现了Runnable接口

    - 实现Runnable接口方式更适合多个线程共享一个资源的情况，并且避免了单继承的限制。资源共享如：

        ```java
        //可以在两个线程中放入一个对象
        T3 t3 = new T3("hello");
        Thread thread1 = new Thread(t3);
        Thread thread2 = new Thread(t3);
        thread1.start();
        thread2.start();
        //如果用继承的方式，则一个对象必须new一下，才能用start，不能用同一个对象
        ```

    - 继承用法：继承类直接调用start方法
    - 接口用法：需要手动新建一个Thread线程，将实现类实例化放进该线程的构造器中

- interrupt，中断线程，但是并没有真正的结束线程，一般用于中断正在休眠的线程（即唤醒线程）

- yield，线程的礼让，让出CPU让其他线程执行，但礼让的时间不确定，所以礼让也不一定成功

- join，线程的插队，插队的线程一旦插队成功，则肯定先执行完插入的线程所有的任务（在t1线程中调用t2线程插队方法时，t2执行完毕之后再执行t1，但是t3等其他线程不受影响，还是并发执行）

- 用户线程：也叫工作线程，当线程的任务执行完或通知方式结束

- 守护线程：一般是为工作线程服务的，当所有的用户线程结束，守护线程自动结束。最常见的守护线程：垃圾回收机制。使用方法：`.setDaemon(true);`

- 线程的生命周期：

    - new：新创建出来的线程，还尚未启用
    - Runnable：new状态后经过start0变为运行态
        - Ready：就绪状态，Running态的线程被挂起或主动yeild放弃CPU
        - Running：运行态，运行在CPU上，CPU选中Ready态的进程可转为此状态

    - TimedWaiting：超时等待，Runnable态的线程经过`sleep(time)、wait(time)、join(time)`等方法时变成此状态，等time结束时再次变为Runnable态
    - Waiting：等待，Runnable态的线程经过`wait()、join()`等方法时变成此状态，可以通过`notify()`等方法重新变为Runnable态
    - Blocked：阻塞，Runnable态的线程等待进入同步代码块的锁时变成此状态，当该线程获得锁时重现变为Runnable态
    - Teminated：终止，线程从Runnable态结束运行，并进行销毁

- 线程同步机制：保证数据在任何同一时刻，最多有一个线程访问，以保证数据的完整性（即当有一个线程在对内存进行操作时，其他线程都不可以对这个内存地址进行操作，直到该线程完成操作，其他线程才能对该内存地址进行操作）

- 同步关键字Synchronized（互斥锁），锁是指锁某个操作的对象，而非代码：

    - 1、同步代码块

        ```java
        synchronized (对象) {//得到对象的锁，才能操作同步代码
            //需要被同步的代码
        }
        ```

    - 2、synchronized放在方法声明中，表示整个方法为同步方法

        ```java
        public synchronized void foo(String bar) {
            //需要被同步的代码
        }
        ```


- 同步的局限性：会导致程序的执行效率降低

- 每个对象都对应于一个可称为”互斥锁“的标记，这个标记用来保证在任意时刻，只能有一个线程访问该对象

- 同步代码块：如果该同步方法是非静态的，那么锁是加在当前对象上的，即this对象，当然也可以是其他对象。

  - ```java
    public void foo() {
        synchronized (this) {//此处填this或其他线程共同操作的对象
        	//需要被同步的代码
    	}
    }

- 如果该同步方法是静态的，那么锁是加在当前类本身。


  - ```java
    public static void foo() {
        synchronized (当前类名.class) {//此处填当前类名.class
        	//需要被同步的代码
    	}
    }
    ```

- 互斥锁注意：
  - 同步方法如果没有使用static修饰：默认锁对象为：this
  - 如果方法使用static修饰，默认锁对象为：当前类.class
  - 需要先分析上锁的代码
  - 选择同步代码块或者同步方法
  - 一定一定一定：要求多个线程的锁对象为同一个（即共享的对象，如果对象不同的话，可以理解为不同的线程争夺不同的锁，那就没有意义，对象相同的话，才是争夺同一把锁）

- 什么操作会释放所占用的锁
  - 1、当前线程的同步代码块、同步方法执行结束
  - 2、当前线程在同步代码块、同步方法中遇到break、return
  - 3、当前线程在同步代码块、同步方法中出现了未处理的Error或Exception，导致异常结束
  - 4、当前线程在同步代码块、同步方法中执行了线程对象的wait()方法，当前线程暂停，并释放锁
- 下面的操作不会释放占用的锁（可以参考生命周期来理解）
  - 1、线程执行同步代码块或同步方法时，程序调用`Thread.sleep()`、`Thread.yield()`方法暂停当前线程的执行，不会释放锁
  - 2、线程执行同步代码块时，其他线程调用了该线程的`suspend()`方法将该线程挂起，该线程不会释放锁
  - 注：应尽量避免使用`suspend()`和`resume()`来控制线程，方法不再推荐使用

 

###### IO流

- 文件流：文件在程序中以流的形式来操作的
  - java程序（内存）->输入流->文件（磁盘）
  - 流：数据在数据源（文件）和程序（内存）之间经历的路径
  - 输入流：数据从数据源（文件）到程序（内存）的路径
  - 输出流：数据从程序（内存）到数据源（文件）的路径

- 创建文件

  - ```java
    new File(String pathName);//根据路径构建一个File对象
    new File(File parent, String child);//根据父目录文件 + 子路径构建
    new File(String parent, String child);//根据父目录 + 子路径构建

  - ```java
    File foo = new File("文件路径");//只是在内存中新开了对象
    foo.createNewFile();//执行了创建后，才会把对象写入到磁盘中
    ```

- 查看文件

  - ```java
    File foo = new File("文件路径");
    //先将文件或目录foo取出来，然后再调用foo.各种方法查看其信息

- 操作目录
  - mkdir()创建一级目录
  - mkdirs()创建多级目录
  - delete()删除空目录或文件

- 流的分类
  - 按操作数据单位不同：
    - 字节流（8bit）：效率比较低，用于传输二进制文件（图片、声音、视频等），可以保证无损操作（因为是以单字节为单位进行传输）
      - 字节输入流：`InputStream`
      - 字节输出流：`OutPutStream`
    - 字符流（按字符）：效率较高，用于传输文本文件
      - 字符输入流：`Reader`
      -  字符输出流：`Writer`
  - 按数据流的流向不同：
    - 输入流：输入到内存
    - 输出流：从内存输出
  - 按流的角色的不同：
    - 节点流：从一个特定的数据源（如文件类型、数组类型、管道类型、字符串类型等）读写数据，如：FileReader、FileWriter
    - 处理流/包装流：“连接”在已存在的流（节点流或处理流）之上，为程序提供更为强大的读写功能，如：BufferedReader、BufferedWriter

- 流和文件：文件与文件之间无法直接通信，可以将流理解为运输媒介，进行文件间的数据交换

- 字节输入流InputStream：（InputStream抽象类是所有类字节输入类的父类）
  - FileInputStream：文件输入流
  - BufferedInputStream：缓冲字节输入流（其直接父类为FilterInputStream）
  - ObjectInputStream：对象字节输入流

- 字节输出流OutputStream：

    - FileOutPutStream：文件输出流

        - ```java
            new FileOutputStream(filePath)//该创建方式写入内容时，会覆盖原来的内容
            new FileOutputStream(filePath, true)//该创建方式写入内容时，是追加到文件后面
            ```

        - ​	注：如果文件不存在，则创建该文件

- 字符输入流FileReader

- 字符输出流FileWriter（直接父类是OutputStreamWriter）

    - 使用后，必须要关闭(close)或者刷新(flush)，否则只是在内存中，并没有写入到指定的文件，从底层源码可知，写入操作是在.close()方法中实现的

- 注：不管是字节还是字符流输入（读取）时，尽量采用`(byte[])、(char[])`字节或字符数组来接收读取，该方法会返回读取到的字节/字符数。同理，不管是字节还是字符流输出时，尽量采用`(byte[], off, len)、(char[], off, len)`方法进行写入，其中参数1为数据源，参数2为数据源从下标几开始写入，参数3为写入的长度，以防`(byte[])`这种情况，会将整个数组写入，但实际数组中的值并没有达到数组长度，而造成意外数据的损失

    - ```java
        //此处以字节为例，字符情况也一模一样
        byte[] bytes = new byte[1024];
        //但该文件只有1025的字节，进行一次读取后，第二次读取时，字节数组只有一个字节
        .write(bytes);
        //该方法是读入整个bytes，但实际上该数组并不满，因此会导致意外的数据问题，因此建议采用三个参数的方法进行写入
        ```

    - 使用完之后，要进行.close()释放资源

- 节点流和处理流的区别与联系

    - 1、节点流是底层流/低级流，直接与数据源相连
    - 2、处理流（包装流）包装了节点流，既可以消除不同节点流的实现差异，也可以提供更方便的方法来完成输入输出
    - 3、处理流对节点流进行包装，使用了修饰器设计模式，不会直接与数据源相连

- 处理流的功能主要体现在
  - 1、性能的提高：主要以增加缓冲的方式来提高输入输出的效率
  - 2、操作的便捷：处理流可能提供了一系列便捷的方法来一次输入输出大批量的数据，使用更加灵活方便

- 关闭处理流时，只需要关闭外层流即可

- 处理字符输入流BufferedReader

  ```java
  String filePath = "c:\\a.java";
  BufferedReader bufferedReader = new BufferedReader(new fileReader(filePath));//处理流对象中放的还是节点流
  ...;
  bufferedReader.close();//只需关闭外层流即可，因为底层会自动去关闭节点流
  ```

- 处理字符输出流BufferedWriter

  ```java
  BufferedWriter bufferedWriter = new BufferedWriter(new fileWriter(filePath, true));
  //BufferedWriter没有提供追加的方法，因此实际上还是在节点流构造器中加true进行内容的追加
  ...;
  bufferedWriter.close();
  ```

- 处理字节输出流BufferedOutputStream

  ```java
  BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filePath));//处理流对象中放的还是节点流
  ...;
  bufferedOutputStream.close();
  ```

- 处理字节输入流BufferedInputStream

  同处理字符输入流

- 字节流既可以操作二进制文件，也可以操作文本文件

- 对象流就是可以将基本数据类型或者对象进行序列化和反序列化操作
  - ObjectOutputStream提供了序列化功能
  - ObjectInputStream提供了反序列化功能

- 序列化和反序列化（操作对象，需要用对象字节输入\输出流`ObjectInputStream、ObjectOutputStream`）

  - 1、序列化就是在保存数据时，保存数据的值和数据类型

  - 2、反序列化就是在恢复数据时，恢复数据的值和数据类型

  - 3、需要让某个对象支持序列化机制，则必须让其类是可序列化的，所以该类必须实现两个接口之一（推荐使用Serializable）：
    - `Serializable//这是一个标记接口，只是标记性质，里面没有方法` 
    - `Externalizable//该接口有方法需要去实现，也是实现了Serializable接口`

  - 4、序列化的类中建议添加`private static final log serialVersionUID = 1L;`，为了提高版本的兼容性

  - 5、序列化对象时，默认里面所有属性都进行序列化，但除了`static`或`transient`修饰的成员

  - 6、序列化对象时，要求类里面属性的类型也要实现序列化接口：如Animal类中，有个属性是Dog类，那么要求Dog类也实现了序列化接口，否则报错

  - 7、序列化具备可继承性，如果某类已经实现了序列化，则它的所有子类也已经默认实现了序列化

      ```java
      //序列化
      String destPath = "src\\dog.dat";
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(destPath));
      oos.writeObject(dog);
      oos.close();
      
      //反序列化
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream(destPath));
      Dog dog2 = (Dog) ois.readObject();
      System.out.println(dog2);
      ois.close();
      ```

      

- 标准输入输出流
  - `System.in`标准输入，编译类型是InputStream，运行类型是BufferedInputStream，默认设备为键盘
  - `System.out`标准输出，编译类型是PrintStream，运行类型是PrintStream，默认设备为显示器

- 转换流：把字节流转成字符流，而字节流本身又可以指定编码方式，再转成字符流时就可以避免乱码

  - InputStreamReader：父类是Reader，可以将InputStream（字节流）包装成Reader（字符流）

    ```java
    InputStreamReader(InputStream, Charset);//参数1：字节输入流 参数2：指定的编码格式
    ```

  - OutputStreamWriter：父类是Writer，可以将OutputStream（字节流）包装成Writer（字符流）

    ```java
    OutputStreamReader(OutputStream, Charset);//参数1：字节输出流 参数2：指定的编码格式
    ```

  - 在处理纯文本数据时，如果使用字符流效率更高，并且可以有效解决中文问题，并且可以在使用时指定编码格式

    ```java
    String filePath = "e:\\a.txt";//指定读取数据源
    InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath, "gbk"));//转换流，将字节流转换成字符流
    BufferedReader br = new BufferedReader(isr);//将转换流放入到包装流，其实还是用BufferedReader进行读取
    String s = br.readLine();//使用包装流读取
    sout(s);//输出
    br.close();//关流，关闭最外层的
    //字节流->转换流->包装流字符
    ```

    ```java
    String filePath = "e:\\a.txt";//指定文件位置
    OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(filePath, "UTF-8"));//转换流，将字节流转换成字符流
    osw.write("写入foo");//直接使用转换流写入
    osw.close();//关闭流
    ```

- 打印流：只有输出流，没有输入流

    - PrintStream字节打印流，在默认情况下，PrintStream输出数据的位置是标准输出，即显示器，因此也可以指定输出到文件
    - PrintWriter字符打印流

- Properties类（HashTable子类）：

    - 1、专门用于读写配置文件的集合类，且配件文件格式为：

        键=值

        键=值

        ...

    - 2、键值对不需要有空格，值不需要用引号。默认类型为String

    - 3、常用方法：

        - 1、`load`：加载配件文件的键值对到Properties对象中

        - 2、`list`：将数据显示到指定设备

        - 3、`getProperty(key)`：根据键获取值

        - 4、`setProperty(key,value)`：设置键值对到Properties对象，如果文件无key就创建，有key就覆盖

        - 5、`store`：将properties中的键值对存储到配置文件，如果没有文件则新建。如果有中文则会变为相应的unicode编码

            ```java
            //创建Properties集合对象
            Properties pro = new Properties();
            //向Properties中添加键值对信息
            pro.setProperty("charset","utf-8");
            //存储到文件中
            pro.store(new FileOutputStream("e:\\a.properties"), null);
            //注：null的地方可以写String，表示添加头注释
            //注：而且在此处只是新建了一个FileOutputStream对象，并没有实际应用，因此不用.close关闭流
            ```

            

###### 网络编程

- IP地址：主机的唯一标识，由网络地址+主机地址组成

- IPv4地址分类（4个字节共32位）：`×.×.×.×`
    - A类：0 7位网络号 24位主机号
    - B类：1 0 14位网络号 16位主机号
    - C类：1 1 0 21位网络号 8位主机号
    - D类：1 1 1 0 28位多播组号
    - E类：1 1 1 1 0 27位留待后用
    - 特殊类：127.0.0.1表示本机地址

- IPv6使用16个字节128位：`×:×:×:×:×:×`

- 域名：
    - 如：`www.baidu.com`，目的是为了方便记忆，解决记ip的困难
    - 可以通过HTTP协议将ip地址映射为域名

- 端口号：用于表示计算机上某个特定的网络程序
    - 1、每个网络程序可以监听一个端口，访问特定的网络程序时，通过ip+端口号进行访问
    - 2、以整数表示，范围为0~65535（2个字节表示端口，共16位）
    - 3、0~1024端口已经被占用，如：ssh22，ftp21，smtp25，http80
    - 4、常见网络程序端口：tomcat8080，mysql3306，oracle1521，sqlserver1433

- 协议：
    - 在网络编程中，数据的组织形式就是协议
    - 协议的价值就是将信息准确无误的发送到对面
    - TCP/IP协议即网络通讯协议，简单的说，由网络层的IP协议和传输层的TCP协议组成

- 网络通信协议

    ```markdown
    OSI模型（理论）			TCP/IP模型			TCP/IP模型各层对应协议
    应用层					  应用层				  HTTP、ftp、telnet、DNS...
    表示层
    会话层
    传输层					  传输层（TCP层）			TCP、UDP...
    网络层					  网络层（IP层）			IP、ICMP、ARP...
    数据链路层				 物理+数据链路层		  Link
    物理层
    ```

- TCP和UDP协议

    - TCP协议：传输控制协议
        - 1、使用TCP协议前，必须先建立TCP连接，形成传输数据通道
        - 2、传输前，采用“三次握手”方式，是可靠的
        - 3、TCP协议进行通信的两个应用进程：客户端、服务端
        - 4、在连接中可进行大数据量的传输
        - 5、传输完毕，需释放已建立的连接，效率低（如果不释放连接会产生类似“占线”的后果）
    - UDP协议：用户数据协议
        - 1、将数据、源、目的封装成数据包，不需要建立连接
        - 2、每个数据包的大小限制在64K内，不适合传输大量数据
        - 3、因无需连接，故是不可靠的
        - 4、发送数据结束时无需释放资源（因为不是面向连接的），速度快

- InetAddress类
    - 1、获取本机InetAddress对象`getLocalHost()`（返回计算机名+IP地址）
    - 2、根据指定主机名/域名获取ip地址对象`getByName("计算机名")`（返回计算机名+IP地址）
    - 3、获取InetAddress对象的主机名`getHostName("域名")`（返回域名+IP地址）
    - 4、通过InetAddress对象获取InetAddress对象的地址`InetAddress.getHostAddress()`（返回IP地址）
    - 5、通过InetAddress对象获取InetAddress的主机名或者域名`InetAddress.getHostName()`（返回主机名或者域名）

- Socket：套接字
    - 1、通信的两段都要有Socket，是两台机器间通信的端点
    - 2、网络通信其实就是Socket间的通信
    - 3、Socket允许程序把网络连接成一个流，数据在两个Socket间通过IO传输
    - 4、一般主动发起通信的应用程序属客户端，等待通信请求的为服务端
    - 5、分为TCP编程和UDP编程

- TCP网络通信编程——字节流（注意顺序：服务器端先运行进行监听，等待客户端启动）

  - 服务器端：

  ```java
  //在本机9999端口监听，等待连接，但是如果9999端口被占用，会抛异常
  ServerSocket serverSocket = new ServerSocket(9999);
  //当没有客户端连接9999端口时，程序会阻塞，等待连接，如果有客户端连接，则会返回Socket对象，程序继续
  Socket socket = serverSocket.accept();
  //通过对应的socket对象的输入流获取数据通道内的数据，如果客户端没有发消息，则会阻塞等客户端写入
  InputStream inputStream = socket.getInputStream();
  //IO读取
  byte[] bytes = new byte[1024];
  int readLine = 0;
  while((readLine = inputStream.read(bytes)) != -1) {
      sout(new String(bytes, 0, readLine));
  }
  //获取socket相关的输出流
  OutputStream outputStream = socket.getOutputStream();
  //回送给客户端消息
  outputStream.write("hello client".getBytes());
  //设置结束写入标记，否则接收方会以为没有写完而阻塞
  socket.shutdownOutput();
  //关闭流和socket
  inputStream.close();
  outputStream.close();
  socket.close();
  //关闭ServerSocket，因为ServerSocket可以对应多个socket，就相当于多个主机连接到一台服务器（即多并发）
  serverSocket.close();
  ```

  - 客户端

  ```java
  //连接服务端（ip、端口），连接参数1主机的9999端口,如果连接成功会返回Socket对象
  //正常情况下，参数1应该写IP地址，此处是为了显示用的本机 实际应该是new Socket("192.168.1.1",9999)
  Socket socket = new Socket(InetAddress.getLocalHost(),9999);
  //连接上后，生成Socket，通过socket.getOutputStream()得到和Socket对象关联的输出流对象
  OutputStream outputStream = socket.getOutputStream();
  //通过输出流，写入数据到数据通道
  outputStream.Write("hello server".getBytes());
  //设置结束写入标记，否则接收方会以为没有写完而阻塞
  socket.shutdownOutput();
  //获取和socket相关联的输入流读取服务端回送的消息（字节）
  InputStream inputStream = socket.getInputStream();
  //IO读取
  byte[] bytes = new byte[1024];
  int readLine = 0;
  while((readLine = inputStream.read(bytes)) != -1) {
      sout(new String(bytes, 0, readLine));
  }
  //必须关闭流对象和socket
  outputStream.close();
  inputStream.close();
  socket.close();
  ```

  - 注：客户端和服务器端各有一个Socket对象

- TCP网络通信编程——字符流

    - 服务器端：

    ```java
    ServerSocket serverSocket = new ServerSocket(9999);
    Socket socket = serverSocket.accept();
    InputStream inputStream = socket.getInputStream();
    //使用转换流，将字节流转成字符流
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    //使用readLine读取数据
    String s = bufferedReader.readLine();
    //输出读到的数据
    sout(s);
    //使用字符流回写
    OutputStream outputStream = socket.getOutputStream();
    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
    //写入回写数据
    bufferedWriter.write("hello client");
    //结束符
    bufferedWriter.newLine();
    //需要手动刷新
    bufferedWriter.flush();
    
    bufferedReader.close();
    bufferedWriter.close();
    socket.close();
    serverSocket.close();
    ```

    - 客户端

    ```java
    Socket socket = new Socket(InetAddress.getLocalHost(),9999);
    OutputStream outputStream = socket.getOutputStream();
    //字节流转换成字符流
    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OutputStream));
    //用字符流写入
    bufferedWriter.write("hello server");
    //插入一个换行符，表示写入的内容结束，而且要求接收方使用readLine()！！！！！！！！！否则读不到结束符
    bufferedWriter.newLine();
    //如果使用字符流，则需要手动刷新，否则数据不会写入数据通道
    bufferedWriter.flush();
    //此时就不需要结束标记了，有换行符代替 socket.shutdownOutput();
    //读取回写数据
    InputStream inputStream = socket.getInputStream();
    //使用转换流，将字节流转成字符流
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    //使用readLine读取数据
    String s = bufferedReader.readLine();
    //输出读到的数据
    sout(s);
    
    bufferedWriter.close();
    bufferedReader.close();
    socket.close();
    ```

- 只有字符流时，才会用到`.flush()`、`.readLine()`，使用字节流时，结束标记使用`socket.shutdownOutput()`

- `netstat -an`可以查看当前主机网络情况，包括端口监听情况和网络连接情况
    - 0.0.0.0和127.0.01表示本机地址
    - 外部地址：外部连接进来的ip地址
    - 内部地址：外部连接进来后与哪个内部地址相连
- `netstat -anb`可以查看哪个应用在使用哪个端口
- 当客户端连接到服务端后，服务端是指定的端口进行监听，而客户端的端口是由TCP/IP来分配的，是不确定的随机的

- UDP网络通信编程（了解）
    - 1、类DatagramSocket（数据报套接字）和DatagramPacket（数据报/数据包）实现了基于UDP协议网络程序
    - 2、UDP数据报通过DatagramSocket数据报套接字发送和接受，系统不保证UDP数据报一定能够安全送到目的地，也不能确定什么时候可以抵达
    - 3、DatagramPacket对象封装了UDP数据报，在数据报中包含了发送端的IP地址和端口号以及接收端的IP地址和端口号
    - 4、UDP协议中每个数据报都给出了完整的地址信息，因此无须建立发送方和接收方的连接
    - 5、没有明确的服务端和客户端，演变成数据的发送端和接收端
    - 6、接受数据和发送数据是通过DatagramSocket对象完成
    - 7、发送数据时，会将数据封装到DatagramPacket对象中发送（装包）
    - 8、当接收到DatagramPacket对象，需要进行拆包，取出数据
    - 9、DatagramSocket可以指定在接收端的某个端口接收数据
- UDP网络编程基本流程
    - 1、核心的两个类/对象DatagramSocket和DatagramPacket
    - 2、建立发送端，接收端（没有服务端和客户端的概念）
    - 3、建立数据包DatagramPacket
    - 4、调用DatagramSocket的发送、接收方法
    - 5、关闭DatagramSocket

- 客户端当有多个socket时，会有相应的多线程进行管理，而不同的socket功能不同，如传输文字的、传输视频等，因此客户端不止有一个socket
- 服务端会指定一个端口进行监听时，会有多个服务端连接进来，因此服务端也会有多线程管理多个不同socket，而不同socket对应不同的客户端或相同客户端的不同功能
