package com.edu.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class ThreadPoolUtils {
    /**
     * 线程池实例
     */
    private static ThreadPoolExecutor threadPoolInstance = null;

    /**
     * 核心线程数
     */
    private static final int CORE_THREAD_SIZE = 16;

    /**
     * 最大线程数
     */
    private static final int MAX_THREAD_SIZE = 33;

    /**
     * 空闲线程存活时间
     */
    private static final int KEEP_ALIVE_TIME = 60 * 1000;

    /**
     * 阻塞队列长度
     */
    private static final int BLOCKING_QUEUE_LENGTH = 1000;

    /**
     * 私有化构造方法
     */
    private ThreadPoolUtils() {
        throw new IllegalStateException("ThreadPoolUtils is Singleton Class!");
    }

    /**
     * 获取线程池
     *
     * @return
     */
    private static synchronized ThreadPoolExecutor getThreadPool() {
        if (threadPoolInstance == null) {
            return new ThreadPoolExecutor(
                    CORE_THREAD_SIZE,
                    MAX_THREAD_SIZE,
                    KEEP_ALIVE_TIME,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingDeque<>(BLOCKING_QUEUE_LENGTH),
                    new ThreadPoolExecutor.AbortPolicy() {
                        @Override
                        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                            log.warn("任务过多，当前运行线程总数：[{}]，活动线程总数：[{}]。阻塞队列已满，等待运行任务数：[{}]",
                                    e.getPoolSize(),
                                    e.getActiveCount(),
                                    e.getQueue().size());
                        }
                    }
            );
        }

        return threadPoolInstance;
    }

    /**
     * 无返回值任务
     *
     * @param task
     */
    public static void executeTask(Runnable task) {
        getThreadPool().execute(task);
    }

    /**
     * 有返回值任务
     *
     * @param task
     * @param <T>
     * @return
     */
    public static <T> Future<T> submitTask(Callable<T> task) {
        return getThreadPool().submit(task);
    }

    /**
     * completableFuture 任务
     *
     * @param task
     * @param <T>
     * @return
     */
    public static <T> CompletableFuture<T> submitCompletableFutureTask(Callable<T> task) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();

        getThreadPool().submit(() -> {
            try {
                /*
                 * T res = task.call();
                 * call() 是同步方法，会阻塞当前的工作线程（而非主线程），包括使用 CompletableFuture 原生的 supplyAsync，也会阻塞，
                 * 只不过阻塞的是线程池中的工作线程。因此该处不会阻塞执行 submitCompletableFutureTask 方法的主线程。
                 */
                T res = task.call();

                /*
                 * completableFuture.complete(res);
                 * CompletableFuture.supplyAsync 是自动执行，会自动的将线程执行完的结果（或无返回值）放入创建的 CompletableFuture 对象中，不需要手动干预任务的结果设置。
                 * 而该处是手动显性的完成一个 CompletableFuture 且将执行完的结果（或无返回值）放入创建的 CompletableFuture 对象中。
                 */
                completableFuture.complete(res);
            } catch (Exception e) {
                // 以异常的方式完成 completableFuture
                completableFuture.completeExceptionally(e);
            }
        });

        // 此时 completableFuture 已经携带了异步执行的结果，包括异常
        return completableFuture;
    }
}
