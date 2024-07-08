package com.edu.threadpoolutil.demo;

import com.edu.demo.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SpringBootTest
@Slf4j
public class DemoTest {
    @Test
    void testExecuteTask() {
        log.info("start");
        ThreadPoolUtils.executeTask(() -> {
            log.info("无返回值");
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        log.info("end");
    }

    @Test
    void testCompletableFuture() {
        List<CompletableFuture<Integer>> tasks = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            tasks.add(ThreadPoolUtils.submitCompletableFutureTask(() -> 1));
        }

        CompletableFuture<Void> all = CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]));

        // 执行额外的异步操作
        all.thenRun(() -> {
            log.info("All tasks completed and need to do something else......");
        });

        // 等待所有的异步任务完成
        try {
            all.get();

            // 获取 CompletableFuture 的返回值
            for (CompletableFuture<Integer> task : tasks) {
                Integer res = task.join();
                log.info("Task is over and res = [{}]", res);
            }

            log.info("All tasks completed and function is over");
        } catch (InterruptedException | ExecutionException e) {
            log.error("occurs an error", e);
        }
    }
}
