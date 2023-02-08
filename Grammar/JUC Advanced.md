### 一、JUC 基础

#### 1、start 方法

- Java 线程是通过 start 的方法启动执行的，主要内容在 native 方法 start0 中，调用的是底层 C 语言的函数
- 底层的函数中，是让 JVM 执行启动线程的操作
- 进程和线程都是操作系统层面的资源，因此会交由操作系统去创建并运行线程

#### 2、多线程相关概念

- 并发：是在**同一实体**上的多个事件，是在**一台处理器**上“同时”处理多个任务，同一时刻只有一个事件在发生
- 并行：是在**不同实体**上的多个事件，是在**多台处理器**上同时处理多个任务，同一时刻你做你的我做我的，大家互不干涉
- 守护线程：一种特殊的线程为其他线程提供服务，在后台完成一些系统性的服务。因为当没有服务对象时，就意味着程序需要完成的业务操作已经结束了，守护线程也就没有存在（服务）的意义，系统就可以退出了。所以当系统只剩下守护线程时，JVM 会自动退出



### 二、CompletableFuture

#### 1、Future 接口

- Future 接口（实现类是 FutureTask）**定义**了操作**异步任务**执行的一些方法，如获取异步任务的执行结果、取消任务的执行、判断任务是否被取消、判断任务执行是否完毕等
- 比如：主线程让一个子线程去执行任务，子线程可能比较耗时，主线程将子线程启动完毕后，主线程就去忙自己的，过了一段时间才去获取子线程执行任务的结果或变成任务状态
- 即 Future 接口可以为主线程新开一个分支任务，专门为主线程处理耗时和费力的复杂业务，此时主线程不会被中断

#### 2、FutureTask 实现类

- 满足多线程 、有返回值、异步任务（能获取执行结果且能改变任务的状态）
- 优点：
    - Future + 线程池的异步多线程任务配合，能显著提高程序的执行效率
- 缺点：
    - get() 阻塞：一旦调用 get() 方法求结果，如果计算没有完成容器导致程序阻塞
    - isDone() 轮询：轮询的方式会耗费无谓的 CPU 资源
- 因此 Future 对于结果的获取不是很友好，只能通过阻塞或轮询的方法得到任务的结果

#### 3、CompletableFuture 对 Future 的改进

##### 3.1、概述

- CompletableFuture 提供了一种观察者模式类似的机制，可以让任务执行完成之后通知监听的一方
- CompletableFuture 实现了 CompletionStage 接口，CompletionStage 代表异步计算过程中的某一个阶段，一个阶段完成后可能触发另外一个阶段（就是说存在一个异步任务的执行可能依赖于上一个异步任务的结果）
- CompletableFuture 也实现了 Future 接口，因此 CompletableFuture 可能代表一个明确完成的 Future，也可能代表完成一个阶段 CompletionStage 

##### 3.2、使用

- 不推荐使用无参构造 new 一个实例，推荐使用四个静态构造方法创建

    - 无返回值
        - runAsync(Runnable runnable)
        - runAsync(Runnable runnable, Executor executor)
    - 有返回值
        - supplyAsync(Supplier\<U> supplier)
        - supplyAsync(Supplier\<U> supplier, Executor executor)
    - Executor executor 参数说明：如果没有指定的 Executor 方法，直接使用默认的 ForkJoinPool 作为它的线程池执行异步代码

- 注意：主线程结束时，会关闭 CompletableFuture 默认使用的线程池，而自定义的线程池需要手动关闭

- 优点：

    - 异步任务结束时，会自动回调某个对象的方法
    - 主线程设置好回调后，不再关心异步任务的执行，异步任务之间可以顺序执行
    - 异步任务出错时，会自动回调某个对象的方法

    ```java
    /**
    	CompletableFuture 的使用
    	感觉类似 Js 的Promise 对象 .then() 和 .catch() 的处理
    */
    CompletableFuture.supplyAsync(() -> {
        // 执行异步逻辑，并设置返回值
        return xxx;
    }, threadPool).whenComplete((res, error) -> {
        // res 参数代表异步任务的返回值
        // error 参数代表执行异步任务的过程中触发的异常，无异常则为 null
        // 当异步任务执行完毕后，自动触发此回调，并异步任务处理之后的逻辑
        System.out.println(res);
    }).exceptionally(error -> {
        // 若触发异常，则可以进入此回调，并处理出现异常的逻辑
        System.out.println(error.getMessage());
    })
    ```

##### 3.3、常见方法

- 获得结果和触发计算：
    - 获得结果：
        - get()：没有计算完成时阻塞
        - get(long timeout, TimeUnit unit)：在超时时间内未完成时阻塞，超出时间后仍未完成，抛出超时异常
        - join()：与无参 get 作用相同，区别在于 join 不会抛出编译异常
        - getNow：传入一个 String，如果取值时计算完成则返回，未完成则返回传入的 String 值
    - 主动触发计算：
        - complete(T value)：是否打断 get 或 join 方法立即返回括号中的值，若打断了则该方法返回 true，get 或 join 方法返回括号内的值，若没有打断，则该方法返回 false，get 或 join 方法返回正常计算的值

- 对计算结果进行处理：
    - :star:thenApply：计算结果存在依赖关系，使这相关的线程**串行化**（类似于 .then() 之后再 .then()，但当其中一个 .then() 出了异常，那么会立即执行 .catch()，并不会走下一步）
    - handle：与上的区别是，当有异常时，可以带着异常的参数继续往下执行，而且 thenApply 只需要传一个返回值的参数，而 handle 需要传返回值和异常两个参数
    - 总结：exceptionnally 类似于 try / catch，whenComplete 和 handle 类似于 try / finally

- 对计算结果进行消费：

    - thenAccept：接收任务的处理结果，并消费处理，无返回结果

- 以上的总结：

    - thenRun：任务 A 执行完执行 B，并且 B 不需要 A 的结果
    - thenAccept：任务 A 执行完执行 B，B 需要 A 的结果，但是任务  B 无返回值
    - thenApply：任务 A 执行完执行 B，B 需要 A 的结果，且任务 B 有返回值

- thenRun 和 thenRunAysnc 的区别：

    - 没有传入自定义线程池时，两个方法都使用默认的线程池 ForkJoinPool

    - 当使用自定义线程池，即执行第一个任务的时候，传入了一个自定义线程池

        - 调用 thenRun 方法执行第二个任务时，则第二个任务和第一个任务共用同一个线程池
        - 调用 thenRunAsync 方法执行第二个任务时，则第一个任务使用传入的自定义线程池，第二个任务使用默认的 ForkJoinPool 线程池

    - 其他的如 thenAccept 和 thenAcceptAsync，thenApply 和 thenApplyAsync 等，区别也是同理

    - 底层源码

    - ```java
        // thenRun 方法
        public CompletableFuture<Void> thenRun(Runnable action){
            return uniRunStage(null, action);
        }
        // thenRunAysnc 方法
        // 其中，传入的 asyncPool 是一个 final 变量，其值为 ForkJoinPool.commonPool()，因此异步 Async 方法会调用默认的线程池
        public CompletableFuture<Void> thenRunAysnc(Runnable action){
            return uniRunStage(asyncPool, action);
        }
        ```

- 对计算速度进行选用：

    - applyToEither：两个 CompletableFuture 线程选择之中返回结果最快的一个

- 对计算结果进行合并：

    - thenCombine：两个 CompletionStage 任务都完成后，最终能把两个任务的结果一起交给 thenCombine 来处理，先完成的先等待其他分支任务的完成



### 三、线程锁

#### 1、悲观锁和乐观锁

##### 1.1、悲观锁

- 如 Synchronized 关键字和 Lock 实现类
- 认为自己在使用数据时，一定会有其他的线程来争抢临界资源，为了确保数据的一致性，会在获取数据时先加锁
- 适合**写操作多**的场景，先加锁可以保证写操作时的数据正确，显式的锁定之后再操作临界资源

##### 1.2、乐观锁

- 认为自己使用数据时，不会有别的线程来争抢临界资源，因此不会加锁
- 在 Java 中通过无锁编程去实现，只是在更新数据的时候去判断，之前有没有别的线程更新了这个数据，如果没有更新则当前线程将自己的数据写入，如果被其他线程更新则根据不同的实现方式执行不同的操作，如放弃修改、重试抢锁等
- 适合**读操作多**的场景，不加锁的特点能够使其读操作的性能大幅提升
- 判断规则
    - 版本号机制 Version
    - CAS 算法

#### 2、synchronized

##### 2.1、对象锁和类锁

- synchronized 锁普通方法时，锁的是当前调用的对象 this，被锁定之后，其他线程不允许进入到**当前对象**其他的 synchronized 方法，即**对象锁**
- synchronized 锁静态方法时，锁的是当前整个的 Class，被锁定之后，其他线程不允许进入到所有当前类实例化对象中的 synchronized 的静态方法，即**类锁**
- 但如果 synchronized 既有静态的，也有普通的，那么任何进程任何同类实例对象都无法访问静态方法， 但普通方法不限制实例对象（因为一个是类锁，一个是对象锁，性质不同），即静态同步方法和普通同步方法之间没有竞争条件
- 总结：
    - 作用于实例对象：当前实例加锁，进入同步代码前要获得**当前实例的锁**
    - 作用于代码块：对括号里配置的对象加锁
    - 作用于静态方法：当前类加锁，进去同步代码前要获得**当前类对象的锁**

##### 2.2、C++ 源码底层

- 通过 objectMonitor.hpp 可知，每个对象天生都带着一个监视器，每一个被锁住的对象都会和 Monitor 关联起来
- 比如在 objectMonitor 中，有 owner 字段指向持有 objectMonitor 对象的线程，还有比如记录锁的重入次数的字段、记录获取锁的次数的字段等

#### 3、公平锁和非公平锁

##### 3.1、公平锁

- 指多个线程按照申请锁的顺序来获取锁

##### 3.2、非公平锁（默认）

- 指多个线程获取锁的顺序不是按照申请锁的顺序，有可能后申请的线程比先申请的线程优先获取锁，在高并发环境下，有可能造成优先级翻转或线程饥饿的状态

##### 3.3、设计原理

- 线程的切换存在 CPU 时间片的浪费，因此非公平锁能更充分利用 CPU 的时间片，尽量减少 CPU 的空闲时间
- 采用非公平锁时，一个线程请求锁获取到同步状态，然后释放同步状态，所以刚释放锁的线程在此刻再次获取同步状态的概率就变的非常大，所以减少了线程切换的开销
- 如果为了更高的吞吐量，可以使用非公平锁（因为节省了线程切换的开销），否则可以使用公平锁

#### 4、可重入锁（递归锁）

##### 4.1、概述

- 概念balabala
- 可重入锁的优点是一定程度上避免死锁

##### 4.2、种类

- 隐式锁（Synchronized 关键字）：使用的锁是默认可重入的
- 显式锁（Lock 实现类）：获取到锁时要显式的加锁，进入内部方法获取到锁时，也要相应的加锁，内部方法完成时要显式的释放锁，最外部时也要进行释放。因此加锁释放是成对出现的，而且获取一次就要加一次锁，等“计数器”真正为 0 时，才是完全释放了锁