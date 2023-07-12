package com.edu.strings.M28;

public class M28 {
    public static void main(String[] args) {
        // System.out.println(new M28().strStr("sadbutsad", "sad"));
        System.out.println(new M28().strStr("leetcode", "leeto"));
    }

    public int strStr(String haystack, String needle) {
        int[] next = new int[needle.length()];
        getNext(next, needle);
        int i = 0;
        for (int j = 0; j < haystack.length(); j++) {
            while (i > 0 && needle.charAt(i) != haystack.charAt(j))
                i = next[i - 1];
            if (needle.charAt(i) == haystack.charAt(j))
                i++;
            if (i == needle.length())
                return j - needle.length() + 1;
        }
        return -1;
    }

    private void getNext(int[] next, String s) {
        int i = 0;
        next[0] = 0;
        for (int j = 1; j < s.length(); j++) {
            while (i > 0 && s.charAt(i) != s.charAt(j))
                i = next[i - 1];
            if (s.charAt(i) == s.charAt(j))
                i++;
            next[j] = i;// i 也同时代表最长相等前后缀的长度
        }
    }
}
