package com.edu.arrays.M904;

import java.util.HashMap;

/**
 * 904. 水果成篮
 * 2023年6月12日19:27:31
 */
public class M904 {
    public static void main(String[] args) {
        System.out.println(totalFruit(new int[]{3, 3, 3, 1, 2, 1, 1, 2, 3, 3, 4}));
    }

    public static int totalFruit(int[] fruits) {
        int slowIndex = 0;
        int maxLength = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int fastIndex = 0; fastIndex < fruits.length; fastIndex++) {
            map.put(fruits[fastIndex], map.getOrDefault(fruits[fastIndex], 0) + 1);
            while (map.size() > 2) {
                map.put(fruits[slowIndex], map.getOrDefault(fruits[slowIndex], 0) - 1);
                if (map.get(fruits[slowIndex]) == 0)
                    map.remove(fruits[slowIndex]);
                slowIndex++;
            }
            maxLength = Math.max(fastIndex - slowIndex + 1, maxLength);
        }
        return maxLength;
    }
}
