package com.LeetCode.Array_.BinarySearch;

@SuppressWarnings({"all"})
public class M_34_AC {
    public static void main(String[] args) {
        M_34_AC test = new M_34_AC();
        int[] nums = {1};
        int target = 1;
        test.searchRange(nums, target);
    }

    public int[] searchRange(int[] nums, int target) {
        int leftBorder = getBorder(nums, target - 1);
        int rightBorder = getBorder(nums, target) - 1;
        if (rightBorder >= leftBorder && nums[leftBorder] == target) {
            return new int[]{leftBorder, rightBorder};
        }
        return new int[]{-1, -1};
    }

    private int getBorder(int[] nums, int target) {
        int length = nums.length;
        int low = 0;
        int high = length - 1;
        int res = length;
        while (high >= low) {
            int mid = (low + high) / 2;
            if (target < nums[mid]) {
                high = mid - 1;
                res = mid;
            } else {
                low = mid + 1;
            }
        }
        return res;
    }
}
