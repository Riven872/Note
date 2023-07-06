package com.edu.strings.O05;

/**
 * @author riven
 * @date 2023/7/7 0007 1:41
 * @description 剑指 Offer 05. 替换空格
 */
public class O05 {
    public static void main(String[] args) {
        System.out.println(replaceSpace("We are happy."));
    }
    public static String replaceSpace(String s) {
        StringBuilder sb = new StringBuilder(s);
        for (char c : s.toCharArray()) {
            if (c == ' ') {
                sb.append("  ");
            }
        }
        int oldIndex = s.length() - 1;
        String s1 = String.valueOf(sb);
        int newIndex = s1.length() - 1;
        char[] chars = s1.toCharArray();
        for (; oldIndex >= 0; oldIndex--) {
            chars[newIndex] = chars[oldIndex];
            if (chars[oldIndex] == ' ') {
                chars[newIndex] = '0';
                chars[--newIndex] = '2';
                chars[--newIndex] = '%';
            }
            newIndex--;
        }
        return String.valueOf(chars);
    }
}
