package com.edu.backtrackings.M216;

import java.util.ArrayList;
import java.util.List;

/**
 * @author riven
 * @date 2023/9/2 0002 18:49
 * @description 216. 组合总和 III
 */
public class M216 {
    List<List<Integer>> res = new ArrayList<>();

    List<Integer> path = new ArrayList<>();

    int sum = 0;

    public List<List<Integer>> combinationSum3(int k, int n) {
        backtracking(k, n, 1, sum);
        return res;
    }

    private void backtracking(int k, int n, int startIndex, int sum) {
        if (path.size() == k) {
            if (sum == n)
                res.add(new ArrayList<>(path));
            return;
        }
        for (int i = startIndex; i <= 9; i++) {
            if (sum > n)
                return;
            path.add(i);
            sum += i;
            backtracking(k, n, i + 1, sum);
            sum -= path.get(path.size() - 1);
            path.remove(path.size() - 1);
        }
    }
}
