package com.edu.linkedlists.M24;

import com.edu.linkedlists.ListNode;

/**
 * @author riven
 * @date 2023/6/29 0029 2:40
 * @description 24. 两两交换链表中的节点
 */
public class M24 {
    public ListNode swapPairs(ListNode head) {
        ListNode sentinel = new ListNode(-1, head);// 哨兵节点
        ListNode currentNode = sentinel;// 遍历节点
        ListNode firstNode, secondNode, temp;
        while (currentNode.next != null && currentNode.next.next != null) {
            firstNode = currentNode.next;// 交换的第一个节点
            secondNode = currentNode.next.next;// 交换的第二个节点
            temp = currentNode.next.next.next;// 两个节点后的一个节点

            currentNode.next = secondNode;
            secondNode.next = firstNode;
            firstNode.next = temp;
            currentNode = firstNode;// 交换后的第一个节点，实质上是位置上的第二个节点
        }
        System.out.println(sentinel.next == head);
        return sentinel.next;
    }
}
