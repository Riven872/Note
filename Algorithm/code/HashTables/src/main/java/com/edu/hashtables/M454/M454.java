package com.edu.hashtables.M454;

import java.util.HashMap;

/**
 * @author riven
 * @date 2023/7/4 0004 2:30
 * @description 454. 四数相加 II
 */
public class M454 {
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int res = 0;

        for (int num1 : nums1) {
            for (int num2 : nums2) {
                int sum = num1 + num2;
                map.put(sum, map.getOrDefault(sum, 0) + 1);
            }
        }

        for (int num3 : nums3) {
            for (int num4 : nums4) {
                int sum = num3 + num4;
                res += map.getOrDefault(-sum, 0);
            }
        }
        return res;
    }
}
