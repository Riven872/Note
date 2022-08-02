package com.LeetCode.Array_.RemoveElements;

@SuppressWarnings({"all"})
public class E_27 {
    public static void main(String[] args) {
        E_27 test = new E_27();
        int[] nums = {0, 1, 2, 3, 3, 0, 4, 2};
        int val = 2;

        System.out.println(test.removeElement(nums, val));
        ;
    }

    public int removeElement(int[] nums, int val) {
        int fast = 0, slow = 0;
        while (fast < nums.length) {
            if (nums[slow] == val) {
                nums[slow] = nums[fast];
                fast++;
            }
            slow++;
            fast++;
        }
        return slow + 1;
    }
}
