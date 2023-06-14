package com.edu.E26;

/**
 * 26. 删除有序数组中的重复项
 */
public class E26 {
    public static void main(String[] args) {

    }

    public static int removeDuplicates(int[] nums) {
        int slowIndex = 0;
        for (int fastIndex = 0; fastIndex < nums.length; fastIndex++) {
            if (nums[slowIndex] != nums[fastIndex]) {
                nums[++slowIndex] = nums[fastIndex];
            }
        }
        return slowIndex + 1;
    }
}