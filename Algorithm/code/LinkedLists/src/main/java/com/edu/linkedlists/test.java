package com.edu.linkedlists;

import java.util.*;

public class test {
    public <T> List<T> getTopN(ListNode header, int n) {
        HashMap<Integer, Integer> map = new HashMap<>();

        List<T> res = new ArrayList<>();

        while (Objects.nonNull(header.next)) {
            map.put(map.get(header.val), map.getOrDefault(header.val, 0));
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int max = entry.getValue();

            if (entry.getValue().compareTo(max) > 0) {
                res.add(entry.getKey());
            }
        }
    }
}
