package com.edu.E844;

/**
 * 844. 比较含退格的字符串
 */
public class E844 {
    public static void main(String[] args) {

    }

    public static boolean backspaceCompare(String s, String t) {
        return splitStr(s).equals(splitStr(t));
    }


    public static String splitStr(String str) {
        char[] chars = str.toCharArray();
        int slowIndex = 0;
        for (int fastIndex = 0; fastIndex < chars.length; fastIndex++) {
            if (chars[fastIndex] != '#')
                chars[slowIndex++] = chars[fastIndex];
            else if (slowIndex > 0)
                slowIndex--;
        }
        return new String(chars).substring(0, slowIndex);
    }
}