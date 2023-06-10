package com.edu.E35;

/**
 * 35. 搜索插入位置
 */
public class E35 {
    public static void main(String[] args) {
        int[] nums1 = {1, 3, 5, 6};
        int target1 = 5;
        int[] nums2 = {1, 3, 5, 6};
        int target2 = 2;
        int[] nums3 = {1, 3, 5, 6};
        int target3 = 7;
        System.out.println(searchInsert(nums1, target1));
        System.out.println(searchInsert(nums2, target2));
        System.out.println(searchInsert(nums3, target3));
    }

    public static int searchInsert(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            int mid = (high - low) / 2 + low;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }
}
