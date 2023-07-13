package edu.stacksandqueues.E232;

import java.util.Stack;

/**
 * @author riven
 * @date 2023/7/12 0012 22:54
 * @description 232. 用栈实现队列
 */
public class E232 {
    /**
     * 输入栈
     */
    Stack<Integer> stackIn;

    /**
     * 输出栈
     */
    Stack<Integer> stackOut;

    /**
     * 初始化队列
     */
    public E232() {
        stackIn = new Stack<>();
        stackOut = new Stack<>();
    }

    /**
     * 入队
     *
     * @param x 入队的值
     */
    public void push(int x) {
        stackIn.push(x);
    }

    /**
     * 出队
     *
     * @return 出队的值
     */
    public int pop() {
        dumpStackIn();
        return stackOut.pop();
    }

    /**
     * 获取队列首位元素
     *
     * @return 首位元素值
     */
    public int peek() {
        dumpStackIn();
        return stackOut.peek();
    }

    /**
     * 判断是否为空队
     *
     * @return true:空队 false:队内有元素
     */
    public boolean empty() {
        return stackIn.empty() && stackOut.empty();
    }

    /**
     * 当输出栈为空时，将输入栈内的所有元素压入输出栈内
     */
    private void dumpStackIn() {
        // 若输出栈内还有元素，则无法压栈
        if (!stackOut.empty())
            return;
        // 将输入栈所有元素压入输出栈
        while (!stackIn.empty())
            stackOut.push(stackIn.pop());
    }
}
