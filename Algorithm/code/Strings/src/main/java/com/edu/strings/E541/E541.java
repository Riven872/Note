package com.edu.strings.E541;

/**
 * @author riven
 * @date 2023/7/6 0006 20:05
 * @description 541. 反转字符串 II
 */
public class E541 {
    public static void main(String[] args) {
        System.out.println(new E541().reverseStr("abcd", 4));
    }

    public String reverseStr(String s, int k) {
        char[] chars = s.toCharArray();
        for (int i = 0; i < s.length(); i += 2 * k) {
            if (i + k >= s.length()) {
                this.reverse(chars, i, s.length() - 1);
                break;
            }
            this.reverse(chars, i, i + k - 1);
        }
        return String.valueOf(chars);
    }

    private void reverse(char[] chars, int slowIndex, int fastIndex) {
        while (slowIndex < fastIndex) {
            char temp = chars[slowIndex];
            chars[slowIndex] = chars[fastIndex];
            chars[fastIndex] = temp;
            slowIndex++;
            fastIndex--;
        }
    }
}
