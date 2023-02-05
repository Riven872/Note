### 一、JUC概述

#### 1、进程与线程

- 进程：是系统进行资源分配和调度的基本单位
- 线程：是系统分配处理器时间资源的基本单位，包含在进程之中，是进程中的实际运作单位，一个进程中可以并发多个线程，每条线程并行执行不同的任务

#### 2、wait 和 sleep 的区别

- wait 是 Object 的方法，sleep 是 Thread 的静态方法
- wait 会释放锁，但调用它的前提是当前线程占有锁（即代码在 synchronized 中），sleep 不会释放锁，同事也不需要占有锁
- 都可以被 interrupted 方法中断

#### 3、管程

- Java 中称为锁，操作系统中称为监视器，是一个同步机制，保证同一时间，只能有一个线程访问临界资源
- jvm 同步基于进入和退出，是使用管程对象实现的

#### 4、用户线程和守护线程

- 用户线程：如自定义的线程，守护线程：在后台运行的线程，如 GC
    - 执行 .start() 方法时，底层执行的是 start0() 方法，再底层是调用的 native，即操作系统帮我们去执行一个新的线程，因此执行 start 时，不一定会立刻创建新线程，而是由操作系统决定
- 主线程（Main）结束时，如果进程内还有用户线程在运行，那么 JVM 不会停止，如果进程内没有用户线程，即使守护线程在运行，主线程结束时，JVM 也会停止



### 二、Lock 接口

#### 1、synchronized

- Java 中的关键字，是一种同步锁，修饰对象为：
    - 修饰一个代码块，被修饰的代码块称为同步语句块，作用的范围是大括号范围内的代码，作用的对象是**调用这个代码块的对象**
    - 修饰一个方法，被修饰的方法称为同步方法，其作用范围是整个方法，作用的对象是**调用这个方法的对象**
        - synchronized 关键字不可被继承，因此如果子类 override，那么需要自己显式的使用 synchronized 关键字
    - 修饰一个静态的方法，其作用的范围是整个静态方法，作用的对象是**这个类的所有对象**
    - 修饰一个类，其作用的范围是 synchronized 后面括号括起来的部分，作用的对象是**这个类的所有对象**

#### 2、Lock

- Lock 是一个类，通过这个类可以实现同步访问，而 Synchronized 是关键字
- synchronized 不需要用户去手动释放锁，当 synchronized 修饰的方法或代码块执行完毕之后，会自动让线程释放锁（出了异常也会自动释放），而 Lock 必须要用户手动去释放，如果不主动释放会出现死锁的现象
- Lock 可以让等待锁的线程响应中断，而 synchronized 不行，等待的线程会一直等待下去，不能被响应中断
- 通过 Lock 可以感知到有没有成功获取到锁，而 synchronized 不行
- Lock 可以提高多个线程进行读操作的效率（因为Lock可以让线程响应中断，因此 Lock 性能要高于 synchronized）
- Object 的 wait 操作在 Lock 中对应为 await，notify 对应为 signal



### 三、线程间通信

#### 虚假唤醒问题

- wait() 的特点是，在哪里等待，唤醒时就在哪里醒，因此需要循环判断而不是单次判断

- 如果不指定对象，直接使用 wait()、notify() 时，只要执行到此处的线程都会被等待、唤醒。（随机抽取幸运线程执行 wait 或 notify）

    ```java
    /**
    当 i != 0 时，线程进入 wait 方法并释放锁进行等待，但如果此时其他线程 notify 唤醒了该线程，那么判断就会失效，在 wait 行继续执行，会执行到不应该执行的 i++ 操作
    */
    if(i != 0){
        wait();
    }
    i++;
    
    /**
    改良，需要 while 判断条件，即使被唤醒，也需要再次的判断条件
    */
    while(i != 0){
    	wait();
    }
    i++;
    ```



### 四、线程间定制化通信

- Lock 是用来替代 synchronized 的，Condition 是用来替代 Object 类的监视器相关方法的

- Condition 接口提供了类似于 Object 类中用于进行线程间通信的 wait / notify / notifyAll 方法的替代方法 await / signal / signalAll

- Condition 将Object 监视器方法(wait / notify 和 notifyAll)分解，使其能作用在不同的条件对象上，达到每个锁对象具有多个等待集的效果，也即达到指定线程执行顺序的线程间定制化通信

    ```java
    // 创建 Lock 锁（可重入锁）
    private Lock lock = new ReentrantLock();
    
    // 创建多个条件对象，但多个条件对象扔与一个 Lock 绑定
    // 可以理解为：锁只有一个，但是有多个钥匙
    private Condition c1 = lock.new Condition();
    private Condition c2 = lock.new Condition();
    private Condition c3 = lock.new Condition();
    
    // 上锁操作
    lock.lock();
    
    // 等待操作，指定持有特定条件对象（特定钥匙的）线程进行等待，如下代码，只有 c1 才会等待
    c1.await();
    
    // 唤醒操作，指定持有特定条件对象的线程唤醒，如下代码，只有 c2 才会被唤醒，其他线程不受影响
    c2.signal();
    
    // 释放锁操作
    lock.unlock();
    ```



### 五、集合的线程安全

#### 1、ArrayList 集合线程的不安全性

- 如 ArrayList 的 add 方法，会抛出 java.util.ConcurrentModificationException 出现了并发修改期望值与修改值不同，快速失败机制
- 多线程添加操作下，并发争抢修改导致的问题，一个线程正在写，另一个线程抢夺，导致数据不一致问题

##### 1.1、使用 Vector 解决

- Vector 实现类中，使用的方法都使用了 synchronized 关键字，因此可以保证线程安全，但也因此性能较低（而且版本是 JDK1.0，不是 JUC 包）

##### 1.2、使用 Collections 工具类解决

- 使用 Collections.synchronizedList(new ArrayList<>()) 返回指定列表支持同步（线程安全的）collection，性能较低（而且版本是 JDK1.0，不是 JUC 包）

##### 1.3、使用 CopyOnWriteArrayList\<E> 解决:star:

- 使用 new CopyOnWriteArrayList\<>() 生成的 List 对象可以保证线程的安全性
- 底层使用的是写时复制技术，创建一个集合时，允许多线程**并发读**，然后复制出一个副本集合，该副本只允许**独立写**，写完之后合并到之前的集合，这样就可以避免产生并发修改的问题

#### 2、HashSet 集合线程的不安全性

- 与 ArrayList 类似，在使用 add 方法时，多线程的情况下也可能会出现并发读的异常（ConcurrentModificationException）

##### 2.1、使用 CopyOnWriteArraySet\<E> 解决

- 同 ArrayList
- 创建线程安全的集合 foo：Set\<String> foo = new CopyOnWriteArraySet\<E>;

#### 3、HashMap 集合线程的不安全性

- 使用 put 方法时，同样抛异常（ConcurrentModificationException）

##### 3.1、使用 ConcurrentHashMap\<K,V> 解决

- 同上
- 创建线程安全的集合 bar：HashMap\<String, Object> bar = new ConcurrentHashMap\<>();