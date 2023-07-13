package edu.stacksandqueues.M150;

import java.util.Stack;

/**
 * @author riven
 * @date 2023/7/13 0013 3:36
 * @description 150. 逆波兰表达式求值
 */
public class M150 {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String s : tokens) {
            if ("+".equals(s) || "-".equals(s) || "*".equals(s) || "/".equals(s)) {
                Integer num1 = stack.pop();
                Integer num2 = stack.pop();
                if ("+".equals(s)) stack.push(num1 + num2);
                if ("-".equals(s)) stack.push(num1 - num2);
                if ("*".equals(s)) stack.push(num1 * num2);
                if ("/".equals(s)) stack.push(num1 / num2);
            } else
                stack.push(Integer.valueOf(s));
        }
        return stack.peek();
    }
}
