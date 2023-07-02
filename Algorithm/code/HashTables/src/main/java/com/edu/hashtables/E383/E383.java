package com.edu.hashtables.E383;

/**
 * @author riven
 * @date 2023/7/1 0001 2:00
 * @description 383. 赎金信
 */
@SuppressWarnings("all")
public class E383 {
    public boolean canConstruct(String ransomNote, String magazine) {
        int[] hashtable = new int[26];
        for (int i = 0; i < magazine.length(); i++) {
            hashtable[magazine.charAt(i) % 26]++;
        }
        for (int i = 0; i < ransomNote.length(); i++) {
            if (--hashtable[ransomNote.charAt(i) % 26] < 0)
                return false;
        }
        return true;
    }
}
