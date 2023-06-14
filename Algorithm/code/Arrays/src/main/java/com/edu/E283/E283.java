package com.edu.E283;

/**
 * 283. 移动零
 */
public class E283 {
    public static void main(String[] args) {

    }

    public static void moveZeroes(int[] nums) {
        int slowIndex = 0;
        for (int fastIndex = 0; fastIndex < nums.length; fastIndex++) {
            if (nums[fastIndex] != 0) {
                int temp = nums[fastIndex];
                nums[fastIndex] = nums[slowIndex];
                nums[slowIndex++] = temp;
            }
        }
    }
}