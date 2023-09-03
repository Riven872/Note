package com.edu.backtrackings.M39;

import java.util.ArrayList;
import java.util.List;

/**
 * @author riven
 * @date 2023/9/3 0003 14:10
 * @description 39. 组合总和
 */
public class M39 {
    public static void main(String[] args) {
        System.out.println(new M39().combinationSum(new int[]{2, 3, 6, 7}, 7));
    }

    List<List<Integer>> res = new ArrayList<>();

    List<Integer> list = new ArrayList<>();

    int sum = 0;

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        backtracking(target, candidates, 0);
        return res;
    }

    private void backtracking(int target, int[] candidates, int startIndex) {
        if (sum == target) {
            res.add(new ArrayList<>(list));
            return;
        }
        if (sum > target)
            return;
        for (int i = startIndex; i < candidates.length; i++) {
            list.add(candidates[i]);
            sum += candidates[i];
            backtracking(target, candidates, i);
            sum -= candidates[i];
            list.remove(list.size() - 1);
        }
    }
}
