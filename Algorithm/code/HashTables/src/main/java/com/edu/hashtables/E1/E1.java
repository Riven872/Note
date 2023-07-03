package com.edu.hashtables.E1;

import java.util.HashMap;

/**
 * @author riven
 * @date 2023/7/4 0004 1:47
 * @description 1. 两数之和
 */
public class E1 {
    public int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int key = target - nums[i];
            if (map.containsKey(key)) {
                res[0] = i;
                res[1] = map.get(key);
                return res;
            }
            map.put(nums[i], i);
        }
        return res;
    }
}
