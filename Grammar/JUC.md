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



### 六、多线程锁

- synchronized 实现同步的基础：Java 中的没一个对象都可以作为锁，具体表现为：
    - 对于普通同步方法，锁是当前实例的对象（this）
    - 对于静态同步方法， 锁是当前类的对象（Class）
    - 对于同步方法块，锁是括号中配置的对象
- synchronized 作用于不同方法时，锁的类型也不同：如果没取到静态方法的锁，但是可以继续去取普通方法的锁

#### 1、非公平锁

- 创建可重入锁时，使用无参构造或传入 false，创建出来的对象就是非公平锁
- 多个线程共同执行一个任务时，非公平锁会导致只有第一个取到锁的线程完成整个任务
- 会导致线程饥饿，但效率较高

#### 2、公平锁

- 创建可重入锁时，使用有参构造传入 true，创建出来的对象就是公平锁
- 公平锁会让线程共同竞争，获取到锁的线程即可执行任务，不存在线程独占的情况
- 多线程工作，但是效率较低

#### 3、可重入锁

- 同一线程在获取到锁之后，可以在第一把锁没有释放的前提下，再获取一次锁

#### 4、死锁

##### 4.1、概述

- 两个或以上的线程在执行过程中，因为争夺资源而造成互相等待的过程称为死锁，如果没有外力干涉将无法执行下去

##### 4.2、产生死锁的原因

- 系统资源不足
- 进程运行推进顺序不合理
- 资源分配不当

##### 4.3、验证是否发生了死锁

- 使用 bin 包中的 jps 来定位**进程**（类似 Linux 中的 ps -ef），命令为 jps -l
- 使用 jvm 自带的 jstach 用来追踪堆栈，命令为 jstack 进程号
- 如果有死锁发生，控制台中输出死锁发生的堆栈信息



### 七、Callable 接口

#### 1、概述

- 创建线程的方式：
    - 继承 Thread 类
    - 实现 Runnable 接口
    - 实现 Callable 接口
    - 使用线程池
- 使用 Thread 或 Runnable 创建的线程，在线程终止时（即 run 方法执行完毕时）， 无法使线程返回结果，因此可以使用 Callable 接口创建线程，而且在无法计算结果或有异常时，可以抛出，实现方法为 call()，而非 run()

#### 2、使用 Callable 创建线程

- Thread 构造函数无法直接传入实现了 Callable 接口的类，但可以传入实现了 Runnable 接口的实现类，即 FutureTask，而 FutureTask 的构造函数中又可以传入 Callable，因此使用 FutureTask 做中介
- 跟 Runnable 接口相同，只有一个 call 方法，因此可以使用 lambda 表达式直接创建 FutureTask
- FutureTask 的 get 方法用来取得 Callable 的返回值，但即使调用多次也不会使线程执行多次。如果 get 方法还没有取到返回值时，会在此进行**阻塞**并等待返回值



### 八、JUC的辅助类

#### 1、减少计数器 CountDownLatch

- 给 CountDownLatch 设置一个初始值，当有线程调用 countDown 方法时，会将计数器的值减一（调用 countDown 方法的线程不会被阻塞）

- 使用 CountDownLatch 中的 await 方法，等待计数器的值不大于 0（除非线程被中断或超出了指定的时间），然后才执行 await 之后的语句

- 当计数器的值大于0时，阻塞运行到 await 的线程，当归零时，释放所有等待的线程

- ```java
    // 创建 CountDownLatch 并设置计数器的初始值
    CountDownLatch countDownLatch = new CountDownLatch(5);
    // 计数器减一
    countDownLatch.countDown();
    // 计数器不为 0 时，阻塞线程执行 await 以下的语句
    countDownLatch.await();
    ```

#### 2、循环栅栏 CyclicBarrier

- 给 CyclicBarrier 设置一个初始值 N，和一个回调方法（实现了 Runnable 接口的实现类），在每个线程执行过程中都执行一次 await 方法，执行完 N 次时，最后一次执行的线程去执行回调方法

- 如果一个线程多次 await，并不会触发回调方法

- 在此过程中只会阻塞要 await 的线程，不会阻塞其他线程，因此 await 是根据阻塞的线程来计算的

- ```java
    // 创建 CyclicBarrier，规定 await 次数和回调方法
    CyclicBarrier cyclicBarrier  = new CyclicBarrier(7, () -> {
        System.out.println(Thread.currentThread().getName() + " ok");
    });
    // 阻塞线程
    cyclicBarrier.await();
    ```

#### 3、信号量 Semaphore 

- 给信号量一个初始值 N，相当于给某个临界资源分配了 N 把钥匙，某线程要访问该临界资源时，需要先 acquire 获取钥匙，如果钥匙不足，则在原地阻塞，直到有线程进行 release 释放信号量时，才可以去继续尝试获取钥匙

- ```java
    // 创建 Semaphore 并设置信号量的初始值
    Semaphore semaphore = new Semaphore(6);
    // 在某一线程中尝试获得信号量
    semaphore.acquire();
    // 在某一个线程中释放信号量
    semaphore.release();
    ```