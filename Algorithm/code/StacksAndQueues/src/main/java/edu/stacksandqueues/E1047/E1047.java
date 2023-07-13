package edu.stacksandqueues.E1047;

import java.util.*;

/**
 * @author riven
 * @date 2023/7/13 0013 2:58
 * @description 1047. 删除字符串中的所有相邻重复项
 */
public class E1047 {
    public static void main(String[] args) {
        long m = System.nanoTime();
        System.out.println(new E1047().removeDuplicates("abbaca"));
        long n = System.nanoTime();
        System.out.println(m - n);
    }

    public String removeDuplicates(String s) {
        Deque<Character> deque = new ArrayDeque<>();
        char[] chars = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (!deque.isEmpty() && c == deque.peekLast()) {
                deque.removeLast();
                continue;
            }
            deque.addLast(c);
        }
        for (char c : deque) {
            sb.append(c);
        }
        return String.valueOf(sb);
    }
}