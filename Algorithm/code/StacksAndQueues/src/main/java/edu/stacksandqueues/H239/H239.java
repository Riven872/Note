package edu.stacksandqueues.H239;

import java.util.*;

/**
 * @author riven
 * @date 2023/7/13 0013 4:01
 * @description 239. 滑动窗口最大值
 */
public class H239 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(new H239().maxSlidingWindow(new int[]{1,3,1,2,0,5}, 3)));
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        ArrayList<Integer> list = new ArrayList<>();
        Deque<Integer> deque = new ArrayDeque<>();
        int slowIndex = 0;
        int fastIndex = 0;
        while (fastIndex < nums.length) {
            // 滑进元素
            while (!deque.isEmpty() && nums[fastIndex] > deque.peekLast()) {
                deque.pollLast();
            }
            deque.offerLast(nums[fastIndex]);
            // 滑出元素
            if (fastIndex - slowIndex + 1 >= k) {
                list.add(deque.peekFirst());
                if (nums[slowIndex] == deque.getFirst()) {
                    deque.pollFirst();
                }
                slowIndex++;
            }
            fastIndex++;
        }
        return list.stream().mapToInt(x -> x).toArray();
    }
}
