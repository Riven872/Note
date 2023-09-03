package com.edu.backtrackings.M40;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author riven
 * @date 2023/9/3 0003 14:44
 * @description 40. 组合总和 II
 */
public class M40 {
    public static void main(String[] args) {
        System.out.println(new M40().combinationSum2(new int[]{2, 2, 2}, 2));
    }
    List<List<Integer>> res = new ArrayList<>();

    List<Integer> path = new ArrayList<>();

    int sum = 0;

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Boolean[] visited = new Boolean[candidates.length];
        Arrays.fill(visited,false);
        Arrays.sort(candidates);
        backtracking(target, candidates, 0, visited);
        return res;
    }

    private void backtracking(int target, int[] candidates, int startIndex, Boolean[] visited) {
        if (sum == target) {
            res.add(new ArrayList<>(path));
            return;
        }
        if (sum > target)
            return;
        for (int i = startIndex; i < candidates.length; i++) {
            if (i > 0 && candidates[i - 1] == candidates[i] && !visited[i - 1])
                continue;
            path.add(candidates[i]);
            visited[i] = true;
            sum += candidates[i];
            backtracking(target, candidates, i + 1, visited);
            sum -= candidates[i];
            visited[i] = false;
            path.remove(path.size() - 1);
        }
    }
}
