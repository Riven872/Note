package com.edu.strings.E344;

/**
 * @author riven
 * @date 2023/7/6 0006 15:22
 * @description 344. 反转字符串
 */
public class E344 {
    public void reverseString(char[] s) {
        int slowIndex = 0;
        int fastIndex = s.length - 1;
        while (slowIndex < fastIndex) {
            char temp = s[slowIndex];
            s[slowIndex] = s[fastIndex];
            s[fastIndex] = temp;
            slowIndex++;
            fastIndex--;
        }
    }
}
