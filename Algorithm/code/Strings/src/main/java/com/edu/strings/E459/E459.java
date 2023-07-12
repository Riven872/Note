package com.edu.strings.E459;

/**
 * @author riven
 * @date 2023/7/12 0012 20:11
 * @description 459. 重复的子字符串
 */
public class E459 {
    public boolean repeatedSubstringPattern(String s) {
        int[] next = new int[s.length()];
        getNext(next, s);
        if (next[s.length() - 1] == 0)
            return false;
        return s.length() % (s.length() - next[s.length() - 1]) == 0;
    }

    private void getNext(int[] next, String s) {
        int i = 0;
        next[0] = 0;
        for (int j = 1; j < s.length(); j++) {
            while (i > 0 && s.charAt(i) != s.charAt(j))
                i = next[i - 1];
            if (s.charAt(i) == s.charAt(j))
                i++;
            next[j] = i;
        }
    }
}
