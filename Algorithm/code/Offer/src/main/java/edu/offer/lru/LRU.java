package edu.offer.lru;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author riven
 * @date 2025/8/28 0028 16:57
 * @description 146. LRU 缓存
 */
public class LRU {
    private final int capacity;

    private final Map<Integer, Integer> cache = new LinkedHashMap<>();// 该类型的结构中，同一个 bucket 中的键值对之间是双向链表

    public LRU(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        synchronized (cache) {
            // 如果缓存中存在，则取出 value 重新 put。否则为 null，则不存在
            // remove 的目的在于重置位置
            Integer value = cache.remove(key);
            if (Objects.nonNull(value)) {
                // 通过 get 刚刚使用，因此重置该 key 的热度，将该 key 放至头部
                cache.put(key, value);
                return value;
            }
            return -1;
        }
    }

    public void put(int key, int value) {
        synchronized (cache) {
            // 缓存中存在，则更新键值对及其位置，放至头部
            if (cache.remove(key) != null) {
                cache.put(key, value);
                return;
            }
            // 缓存中不存在且新增时发现超限
            if (cache.size() == capacity) {
                // 通过自我迭代，找到最尾部的 key，也就是最长时间没有访问的 key
                Integer leastKey = cache.keySet().iterator().next();
                // 移除最长时间没有访问的 key
                cache.remove(leastKey);
            }
            cache.put(key, value);
        }
    }
}
