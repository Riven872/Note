package com.edu.arrays.M209;

/**
 * 209. 长度最小的子数组
 * 2023年6月12日17:16:47
 */
public class M209 {
    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 2, 4, 3};
        int target = 7;
        System.out.println(minSubArrayLen(target, nums));
    }

    public static int minSubArrayLen(int target, int[] nums) {
        int minLength = Integer.MAX_VALUE;
        int currentLength;// 滑动窗口的长度
        int slowIndex = 0;// 慢指针，也是滑动窗口的起始位置
        int sum = 0;
        for (int fastIndex = 0; fastIndex < nums.length; fastIndex++) {
            sum += nums[fastIndex];
            while (sum >= target) {
                currentLength = fastIndex - slowIndex + 1;
                minLength = Math.min(currentLength, minLength);
                sum -= nums[slowIndex++];
            }
        }
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
}
