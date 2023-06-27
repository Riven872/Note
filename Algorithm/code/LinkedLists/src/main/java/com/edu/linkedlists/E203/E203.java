package com.edu.linkedlists.E203;

import com.edu.linkedlists.ListNode;


/**
 * @author riven
 * @date 2023/6/15 0015 18:52
 * @description 203. 移除链表元素
 */
public class E203 {
    public static void main(String[] args) {

    }

    public static ListNode removeElements(ListNode head, int val) {
        ListNode sentinel = new ListNode(-1, head);
        ListNode pre = sentinel;
        ListNode current = sentinel.next;
        while (current != null) {
            if (current.val == val)
                pre.next = current.next;
            else
                pre = current;
            current = current.next;
        }
        return sentinel.next;
    }
}
