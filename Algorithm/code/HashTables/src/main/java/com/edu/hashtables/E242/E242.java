package com.edu.hashtables.E242;

/**
 * @author riven
 * @date 2023/7/1 0001 1:19
 * @description 242. 有效的字母异位词
 */
public class E242 {
    public static void main(String[] args) {
        System.out.println(isAnagram("anagram", "nagaram"));
    }

    public static boolean isAnagram(String s, String t) {
        int[] hashtable = new int[26];
        for (int i = 0; i < s.length(); i++) {
            hashtable[s.charAt(i) % 26]++;
        }
        for (int i = 0; i < t.length(); i++) {
            hashtable[t.charAt(i) % 26]--;
        }
        for (int h : hashtable) {
            if (h != 0)
                return false;
        }
        return true;
    }
}
