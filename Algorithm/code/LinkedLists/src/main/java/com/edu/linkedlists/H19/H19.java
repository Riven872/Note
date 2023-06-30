package com.edu.linkedlists.H19;

import com.edu.linkedlists.ListNode;

/**
 * @author riven
 * @date 2023/6/30 0030 14:41
 * @description 19. 删除链表的倒数第 N 个结点
 */
public class H19 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode sentinel = new ListNode(-1, head);
        ListNode slowIndex = sentinel;
        ListNode fastIndex = sentinel;
        while (n > 0) {
            fastIndex = fastIndex.next;
            n--;
        }
        while (fastIndex.next != null) {
            slowIndex = slowIndex.next;
            fastIndex = fastIndex.next;
        }
        slowIndex.next = slowIndex.next.next;
        return sentinel.next;
    }
}
