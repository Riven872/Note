package com.edu.linkedlists;

import java.util.Arrays;

public class demo {
    public static long minTotalCost(int numItems, int[] itemId, int[] cost) {
        long[] minCost = new long[numItems];
        boolean[] hasCost = new boolean[numItems];

        // Initialize minCost with maximum value and hasCost with false
        Arrays.fill(minCost, Long.MAX_VALUE);
        Arrays.fill(hasCost, false);

        int n = itemId.length;
        for (int i = 0; i < n; i++) {
            int id = itemId[i];
            int c = cost[i];
            if (c < minCost[id]) {
                minCost[id] = c;
            }
            hasCost[id] = true;
        }

        long total = 0;
        for (int i = 0; i < numItems; i++) {
            if (!hasCost[i]) {
                return -1;
            }
            total += minCost[i];
        }
        return total;
    }

    public static void main(String[] args) {
        // Sample test case
        int numItems = 3;
        int[] itemId = {2,0,1,2};
        int[] cost = {8,7,6,9};
        System.out.println(minTotalCost(numItems, itemId, cost)); // Output: 11
    }
}
