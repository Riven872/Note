package com.edu.hashtables.M438;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author riven
 * @date 2023/7/3 0003 2:04
 * @description 438. 找到字符串中所有字母异位词
 */
public class M438 {
    public static void main(String[] args) {
        System.out.println(findAnagrams("cbaebabacd", "abc"));
    }

    public static List<Integer> findAnagrams(String s, String p) {
        ArrayList<Integer> list = new ArrayList<>();

        int slowIndex = 0;
        int fastIndex = 0;

        int[] sHash = new int[26];
        int[] pHash = new int[26];

        char[] pChars = p.toCharArray();
        char[] sChars = s.toCharArray();
        for (char pChar : pChars) {
            pHash[pChar % 26]++;
        }
        while (fastIndex < s.length()) {
            sHash[sChars[fastIndex] % 26]++;
            if (Arrays.equals(sHash, pHash)) {
                list.add(slowIndex);
            }
            if (++fastIndex - slowIndex + 1 > p.length())
                sHash[sChars[slowIndex++] % 26]--;
        }
        return list;
    }
}
