package com.edu.M34;

import java.util.Arrays;

/**
 * 34. 在排序数组中查找元素的第一个和最后一个位置
 */
public class M34 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8)));
        // System.out.println(Arrays.toString(searchRange(new int[]{1}, 1)));
    }

    public static int[] searchRange(int[] nums, int target) {
        int[] range = {-1, -1};
        int leftRange = getLeftRange(nums, target);
        int rightRange = getRightRange(nums, target);
        if (nums.length == 0 || target > nums[nums.length - 1] || target < nums[0]) {
            return range;
        }
        if (rightRange - leftRange > 1) {
            return new int[]{leftRange + 1, rightRange - 1};
        }
        return range;
    }

    /**
     * 获取左边界（拿到最左侧 target 下标前一位）
     *
     * @param nums
     * @param target
     * @return
     */
    private static int getLeftRange(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        int res = -1;
        while (low <= high) {
            int mid = (high - low) / 2 + low;
            if (nums[mid] >= target) {
                high = mid - 1;
                res = high;
            } else {
                low = mid + 1;
            }
        }
        return res;
    }

    /**
     * 获取右边界（拿到最右侧 target 下标后一位）
     *
     * @param nums
     * @param target
     * @return
     */
    private static int getRightRange(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        int res = -1;
        while (low <= high) {
            int mid = (high - low) / 2 + low;
            if (nums[mid] <= target) {
                low = mid + 1;
                res = low;
            } else {
                high = mid - 1;
            }
        }
        return res;
    }
}
