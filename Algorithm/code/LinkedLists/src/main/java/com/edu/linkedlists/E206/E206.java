package com.edu.linkedlists.E206;

import com.edu.linkedlists.ListNode;

/**
 * @author riven
 * @date 2023/6/28 0028 17:05
 * @description 206. 反转链表
 */
public class E206 {
    public ListNode reverseList(ListNode head) {
        ListNode currentNode = head;
        ListNode preNode = null;
        ListNode temp = null;
        while (currentNode != null) {
            temp = currentNode.next;// 保存第三个节点的地址
            currentNode.next = preNode;// 反转指向
            preNode = currentNode;// 移动指针
            currentNode = temp;// 移动指针至后面的节点地址
        }
        return preNode;// 返回新头指针（非哨兵节点）
    }
}
