package com.edu.linkedlists.E160;

import com.edu.linkedlists.ListNode;

public class E160 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int aSize = 0;// 链表 A 长度
        int bSize = 0;// 链表 B 长度
        ListNode currentNode = headA;// 遍历长度节点
        ListNode aNode = headA;
        ListNode bNode = headB;
        while (currentNode != null) {
            currentNode = currentNode.next;
            aSize++;
        }
        currentNode = headB;
        while (currentNode != null){
            currentNode = currentNode.next;
            bSize++;
        }
        if (aSize > bSize && aNode != null){
            for (int i = 0; i < aSize - bSize + 1; i++) {
                aNode = aNode.next;
            }
        }
        if (bSize >= aSize && bNode != null){
            for (int i = 0; i < bSize - aSize; i++) {
                bNode = bNode.next;
            }
        }
        while (aNode != null && bNode != null){
            if (aNode == bNode)
                return aNode;
            else {
                aNode = aNode.next;
                bNode = bNode.next;
            }
        }
        return null;
    }
}
