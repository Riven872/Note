package com.edu.backtrackings.M77;

import java.util.ArrayList;
import java.util.List;

/**
 * @author riven
 * @date 2023/9/2 0002 17:41
 * @description 77. 组合
 */
public class M77 {
    List<List<Integer>> res = new ArrayList<>();

    List<Integer> path = new ArrayList<>();

    public List<List<Integer>> combine(int n, int k) {
        backtracking(n, k, 1);
        return res;
    }

    private void backtracking(int n, int k, int startIndex) {
        if (path.size() == k) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = startIndex; i <= n; i++) {
            path.add(i);
            backtracking(n, k, i + 1);
            path.remove(path.size() - 1);
        }
    }
}
