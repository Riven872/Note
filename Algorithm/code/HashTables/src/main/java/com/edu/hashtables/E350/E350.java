package com.edu.hashtables.E350;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author riven
 * @date 2023/7/3 0003 3:54
 * @description 350. 两个数组的交集 II
 */
public class E350 {
    public int[] intersect(int[] nums1, int[] nums2) {
        ArrayList<Integer> res = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums1) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        for (int num : nums2) {
            if (map.containsKey(num) && map.get(num) > 0) {
                res.add(num);
                map.put(num, map.get(num) - 1);
            }
        }
        return res.stream().mapToInt(x -> x).toArray();
    }
}
