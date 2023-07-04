package com.edu.hashtables.M18;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author riven
 * @date 2023/7/4 0004 17:10
 * @description 18. 四数之和
 */
public class M18 {
    public static void main(String[] args) {
        System.out.println(fourSum(new int[]{-2, -1, -1, 1, 1, 2, 2}, 0));
    }

    public static List<List<Integer>> fourSum(int[] nums, int target) {
        ArrayList<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        // 一级剪枝
        if (nums[0] > 0 && nums[nums.length -1] > target)
            return list;
        // 一层循环去重
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i - 1] == nums[i])
                continue;
            // 二层循环去重
            for (int j = i + 1; j < nums.length; j++) {
                if (j > i + 1 && nums[j - 1] == nums[j])
                    continue;
                int slowIndex = j + 1;
                int fastIndex = nums.length - 1;
                while (slowIndex < fastIndex) {
                    int sum = nums[i] + nums[j] + nums[slowIndex] + nums[fastIndex];
                    if (sum > target)
                        fastIndex--;
                    else if (sum < target)
                        slowIndex++;
                    else {
                        list.add(Arrays.asList(nums[i], nums[j], nums[slowIndex], nums[fastIndex]));
                        while (slowIndex < fastIndex && nums[slowIndex] == nums[slowIndex + 1])
                            slowIndex++;
                        while (slowIndex < fastIndex && nums[fastIndex] == nums[fastIndex - 1])
                            fastIndex--;
                        slowIndex++;
                        fastIndex--;
                    }
                }
            }
        }
        return list;
    }
}
