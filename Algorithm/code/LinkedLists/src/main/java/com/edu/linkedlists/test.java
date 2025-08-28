package com.edu.linkedlists;

public class test {
    public static void main(String[] args) {
        ListNode _5 = new ListNode(5, null);
        ListNode _4 = new ListNode(4, _5);
        ListNode _3 = new ListNode(3, _4);
        ListNode _2 = new ListNode(2, _3);
        ListNode _1 = new ListNode(1, _2);

        System.out.println(reverseKGroup(_1, 2));
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k <= 0) {
            return head;
        }
        ListNode sentinel = new ListNode(-1, head);
        // 外链表遍历指针
        ListNode i = sentinel;
        // 因为是从头指针开始遍历，因此需要从 0 号位开始
        int n = 0;
        while (i.next != null) {
            // 内链表遍历指针
            ListNode j = i;
            //遍历得到k个长度的链表
            while (j.next != null && n < k) {
                j = j.next;
                n++;
            }
            // 链表长度正好为 k 时
            if (n == k) {
                // 下一组的头指针
                ListNode nextHeadNode = j.next;
                // 上一组的头指针
                ListNode preHeadNode = i.next;
                // 将一组链表置为有界
                j.next = null;
                // 将上一组反转，返回值为反转后的最后一个节点，也就是 i 的新起点
                // 因为 i 一直是哨兵节点，因此它的 next 指向头结点，其实就是更新 i 位置的
                i.next = reverse(preHeadNode);
                // 上一组的头指针反转后会指向下一组未反转的头指针，此处的负责将每一组反转后的链表重新关联起来
                preHeadNode.next = nextHeadNode;
                // i 连接完成后，开始指向下一组
                i = preHeadNode;
                n = 0;
            } else {
                break;
            }
        }
        return sentinel.next;
    }

    private static ListNode reverse(ListNode head) {
        ListNode preNode = null;
        ListNode currentNode = head;
        while (currentNode != null) {
            ListNode temp = currentNode.next;
            currentNode.next = preNode;
            preNode = currentNode;
            currentNode = temp;
        }
        return preNode;
    }
}
