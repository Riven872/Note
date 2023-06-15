package com.edu.arrays.E977;

import java.util.Arrays;

/**
 * 977. 有序数组的平方
 */
public class E977 {
    public static void main(String[] args) {
        int[] nums = {-7,-3,2,3,11};
        System.out.println(Arrays.toString(sortedSquares(nums)));
    }

    public static int[] sortedSquares(int[] nums) {
        int slowIndex = 0;
        int fastIndex = nums.length - 1;
        int[] newNums = new int[nums.length];
        nums = Arrays.stream(nums).map(e -> e * e).toArray();
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[fastIndex] > nums[slowIndex])
                newNums[i] = nums[fastIndex--];
            else
                newNums[i] = nums[slowIndex++];
        }
        return newNums;
    }
}
