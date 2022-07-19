package com.LeetCode.Array_;

@SuppressWarnings({"all"})
public class M_34 {
    public static void main(String[] args) {
        int[] nums = {5, 7, 7, 8, 8, 10};
        int target = 5;

        int leftBorder = new M_34().getBorder(nums, target - 1);
        int rightBorder = new M_34().getBorder(nums, target) - 1;
    }

    /**
     * 在排序数组中查找元素的第一个和最后一个位置
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        int leftBorder = getLeftBorder(nums, target);
        int rightBorder = getRightBorder(nums, target);
        //情况一：target 在数组范围的右边或者左边
        if (leftBorder == -1 || rightBorder == -1)
            return new int[]{-1, -1};
        //情况三：target 在数组范围中，且数组中存在target
        if (rightBorder - leftBorder > 1)
            return new int[]{leftBorder + 1, rightBorder - 1};
            //情况二：target 在数组范围中，且数组中不存在target
        else
            return new int[]{-1, -1};
    }

    /**
     * 找左边界值
     *
     * @param nums
     * @param target
     * @return
     */
    private int getLeftBorder(int[] nums, int target) {
        int length = nums.length;
        int low = 0;
        int high = length - 1;
        int leftBorder = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (target <= nums[mid]) {
                high = mid - 1;
                //找左边界值时，二分法最后的状态是：high指针在mid也就是给定值的左边一位，此时high<low，因此跳出循环，因此实际边界值是leftBorder+1
                leftBorder = high;
            } else
                low = mid + 1;
        }
        return leftBorder;
    }

    /**
     * 找右边界值
     *
     * @param nums
     * @param target
     * @return
     */
    private int getRightBorder(int[] nums, int target) {
        int length = nums.length;
        int low = 0;
        int high = length - 1;
        int rightBorder = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (target >= nums[mid]) {
                low = mid + 1;
                //找左边界值时，二分法最后的状态是：low指针在mid也就是给定值的右边一位，此时high<low，因此跳出循环，因此实际边界值是rightBorder-1
                rightBorder = low;
            } else
                high = mid - 1;
        }
        return rightBorder;
    }

    /**
     * 合并（未完成）
     *
     * @param nums
     * @param target
     * @return
     */
    private int getBorder(int[] nums, int target) {
        int length = nums.length;
        int low = 0;
        int high = length - 1;
        int mid = -1;
        while (high >= low) {
            mid = (low + high) / 2;
            if (target < nums[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return mid;
    }
}