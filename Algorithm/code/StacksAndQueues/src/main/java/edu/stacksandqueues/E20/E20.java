package edu.stacksandqueues.E20;

import java.util.Stack;

/**
 * @author riven
 * @date 2023/7/13 0013 2:35
 * @description 20. 有效的括号
 */
public class E20 {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (c == '(' || c == '[' || c == '{')
                stack.push(c);
            if (c == ')' || c == ']' || c == '}') {
                if (stack.empty())
                    return false;
                Character pop = stack.pop();
                if (!((pop == '(' && c == ')') || (pop == '[' && c == ']') || (pop == '{' && c == '}')))
                    return false;
            }
        }
        return stack.empty();
    }
}
