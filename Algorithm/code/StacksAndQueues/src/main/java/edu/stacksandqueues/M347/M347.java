package edu.stacksandqueues.M347;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author riven
 * @date 2023/7/14 0014 13:05
 * @description 347. 前 K 个高频元素
 */
public class M347 {
    public int[] topKFrequent(int[] nums, int k) {
        // 建立优先级队列且从小到大排序，小顶堆形式，其中元素为键值对形式的数组，用于保存元素及其频次
        PriorityQueue<int[]> queue = new PriorityQueue<>((x, y) -> x[1] - y[1]);
        // 返回值数组
        int[] res = new int[k];
        // 元素-频次 键值对
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        // 遍历每一个键值对，放入小顶堆内
        for (Map.Entry<Integer, Integer> m : map.entrySet()) {
            int[] temp = new int[2];
            temp[0] = m.getKey();
            temp[1] = m.getValue();
            queue.offer(temp);
            // 优先级队列是无界的，因此小顶堆满 k 个时，就将较小的元素弹出，只留下较大的
            if (queue.size() > k)
                queue.poll();
        }
        for (int i = 0; i < k; i++) {
            res[i] = queue.remove()[0];
        }
        return res;
    }
}
