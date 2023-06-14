package com.edu.E27;

/**
 * 27. 移除元素
 */
public class E27 {
    public static void main(String[] args) {

    }

    public static int removeElement(int[] nums, int val) {
        int fast = 0, slow = 0;
        while (fast < nums.length) {
            if (nums[fast] != val) {
                nums[slow++] = nums[fast];
            }
            fast++;
        }
        return slow;
    }

}