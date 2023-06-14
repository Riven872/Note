package com.edu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author riven
 * @date 2023/6/14 0014 13:23
 * @description 二刷及以上 coding
 */
public class reviewDemo {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(sortedSquares(new int[]{-7,-3,2,3,11})));
    }

    public static int[] sortedSquares(int[] nums) {
        int length = nums.length;
        int[] arr = new int[length];
        int slowIndex = 0;
        int fastIndex = nums.length - 1;
        while (slowIndex <= fastIndex) {
            if (Math.abs(nums[slowIndex]) > Math.abs(nums[fastIndex])) {
                arr[--length] = nums[slowIndex] * nums[slowIndex];
                slowIndex++;
            } else {
                arr[--length] = nums[fastIndex] * nums[fastIndex];
                fastIndex--;
            }
        }
        return arr;
    }
}
