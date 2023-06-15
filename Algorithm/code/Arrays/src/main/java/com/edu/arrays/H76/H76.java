package com.edu.arrays.H76;

import java.util.HashMap;

/**
 * 76. 最小覆盖子串
 * 2023年6月13日14:27:30
 */
public class H76 {
    public static void main(String[] args) {
        String s = "ADOBECODEBANC";
        String t = "ABC";
        System.out.println(minWindow(s, t));
    }

    public static String minWindow(String s, String t) {
        String res = "";
        int need = t.length();
        int slowIndex = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        for (int fastIndex = 0; fastIndex < t.length(); fastIndex++) {
            map.put(t.charAt(fastIndex), map.getOrDefault(t.charAt(fastIndex), 0) + 1);
        }
        // 快指针右移
        for (int fastIndex = 0; fastIndex < s.length(); fastIndex++) {
            if (map.containsKey(s.charAt(fastIndex))) {
                // 该字符需求量 > 0 时，总需求量才 - 1，负数说明仍有盈余
                if (map.get(s.charAt(fastIndex)) > 0) {
                    need--;
                }
                // 该字符需求量可以为负数，表明需求过多
                map.put(s.charAt(fastIndex), map.get(s.charAt(fastIndex)) - 1);
            }
            // 窗口达到目标，慢指针右移
            while (need == 0) {
                if ("".equals(res) || res.length() > (fastIndex - slowIndex + 1)) {
                    res = s.substring(slowIndex, fastIndex + 1);
                }
                // 该字符需求量 > 0 时，总需求量才 + 1，负数说明仍有盈余
                if (map.containsKey(s.charAt(slowIndex))) {
                    if (map.get(s.charAt(slowIndex)) >= 0) {
                        need++;
                    }
                    // 表明该字符的需求量
                    map.put(s.charAt(slowIndex), map.get(s.charAt(slowIndex)) + 1);
                }
                slowIndex++;
            }
        }
        return res;
    }
}
