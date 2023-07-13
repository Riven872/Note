package edu.stacksandqueues.E225;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author riven
 * @date 2023/7/13 0013 0:39
 * @description 225. 用队列实现栈
 */
public class E225 {
    /**
     * 队列
     */
    Queue<Integer> queue;

    /**
     * 初始化
     */
    public E225() {
        queue = new LinkedList<>();
    }

    /**
     * 压栈
     *
     * @param x 压入栈的元素
     */
    public void push(int x) {
        queue.add(x);
    }

    /**
     * 弹栈
     *
     * @return 弹出栈的元素
     */
    public int pop() {
        reSize();
        return queue.remove();
    }

    /**
     * 返回顶部元素
     * @return 顶部元素
     */
    public int top() {
        reSize();
        int res = queue.remove();
        // 只是返回顶部元素，不会删除队列中的元素
        queue.add(res);
        return res;
    }

    /**
     * 判断是否栈空
     * @return true:栈空 false:栈非空
     */
    public boolean empty() {
        return queue.isEmpty();
    }

    /**
     * 出队之前重新调整顺序
     */
    private void reSize() {
        int size = queue.size();
        while (--size > 0)
            queue.add(queue.poll());
    }
}
