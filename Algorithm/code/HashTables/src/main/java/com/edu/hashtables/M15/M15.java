package com.edu.hashtables.M15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author riven
 * @date 2023/7/4 0004 14:38
 * @description 15. 三数之和
 */
public class M15 {
    public static void main(String[] args) {
        System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        ArrayList<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        // 排序后如果第一位数字大于 0，则不可能有合适的三元组
        if (nums[0] > 0)
            return list;
        for (int i = 0; i < nums.length; i++) {
            // 去重遍历指针 i
            if (i > 0 && nums[i] == nums[i - 1])
                continue;
            int slowIndex = i + 1;
            int fastIndex = nums.length - 1;
            while (slowIndex < fastIndex) {
                int temp = nums[i] + nums[slowIndex] + nums[fastIndex];
                if (temp > 0)
                    fastIndex--;
                else if (temp < 0)
                    slowIndex++;
                else {
                    list.add(Arrays.asList(nums[i], nums[slowIndex], nums[fastIndex]));
                    // 去重慢指针
                    while (slowIndex < fastIndex && nums[slowIndex] == nums[slowIndex + 1])
                        slowIndex++;
                    // 去重快指针
                    while (slowIndex < fastIndex && nums[fastIndex] == nums[fastIndex - 1])
                        fastIndex--;
                    slowIndex++;
                    fastIndex--;
                }
            }
        }
        return list;
    }
}
