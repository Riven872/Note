package com.edu.hashtables.E349;

import java.util.*;

/**
 * @author riven
 * @date 2023/7/3 0003 3:54
 * @description 349. 两个数组的交集
 */
public class E349 {
    public int[] intersection(int[] nums1, int[] nums2) {
        HashSet<Integer> set = new HashSet<>();
        HashSet<Integer> res = new HashSet<>();
        for (int num : nums1) {
            set.add(num);
        }
        for (int num : nums2) {
            if (set.contains(num))
                res.add(num);
        }
        return res.stream().mapToInt(x -> x).toArray();
    }
}
