package com.edu.backtrackings.M17;

import java.util.ArrayList;
import java.util.List;

/**
 * @author riven
 * @date 2023/9/3 0003 2:00
 * @description 17. 电话号码的字母组合
 */
public class M17 {
    public static void main(String[] args) {
        System.out.println(new M17().letterCombinations("23"));
    }

    List<String> res = new ArrayList<>();

    StringBuilder string = new StringBuilder();

    public static final String[] letterMap = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    public List<String> letterCombinations(String digits) {
        if (digits.isEmpty())
            return res;
        backtracking(digits, 0);
        return res;
    }

    private void backtracking(String digits, int index) {
        if (index == digits.length()) {
            res.add(string.toString());
            return;
        }
        String str = letterMap[Character.getNumericValue(digits.charAt(index))];
        for (int i = 0; i < str.length(); i++) {
            string.append(str.charAt(i));
            backtracking(digits, index + 1);
            string.deleteCharAt(string.length() - 1);
        }
    }
}
