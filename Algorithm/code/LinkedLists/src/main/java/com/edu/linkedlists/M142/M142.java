package com.edu.linkedlists.M142;

import com.edu.linkedlists.ListNode;

/**
 * @author riven
 * @date 2023/6/30 0030 20:54
 * @description 142. 环形链表 II
 */
public class M142 {
    public ListNode detectCycle(ListNode head) {
        ListNode slowIndex = head;
        ListNode fastIndex = head;
        while (fastIndex != null && fastIndex.next != null) {
            fastIndex = fastIndex.next.next;// 快指针每次遍历两个结点
            slowIndex = slowIndex.next;// 慢指针每次遍历一个结点
            // 找到指针相遇点，即有环
            if (fastIndex == slowIndex) {
                ListNode index1 = head;// 从头结点出发每次遍历一个
                ListNode index2 = fastIndex;// 从相遇结点出发每次遍历一个
                while (index1 != index2) {
                    index1 = index1.next;
                    index2 = index2.next;
                }
                return index1;
            }
        }
        return null;
    }
}
