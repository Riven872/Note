package com.edu.strings.O58;

/**
 * @author riven
 * @date 2023/7/7 0007 22:48
 * @description 剑指 Offer 58 - II. 左旋转字符串
 */
public class O58 {
    public String reverseLeftWords(String s, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = n - 1; i < s.length(); i++)
            stringBuilder.append(s.charAt(i));
        for (int i = 0; i < n; i++)
            stringBuilder.append(s.charAt(i));
        return String.valueOf(stringBuilder);
    }
}
