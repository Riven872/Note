package com.edu.linkedlists.M707.single_;

public class MySingleLinkedList {
    /**
     * 链表大小
     */
    public int size;

    /**
     * 链表中哨兵节点
     */
    public SingleListNode singleListNode;

    /**
     * 初始化链表
     */
    public MySingleLinkedList() {
        this.size = 0;// 初始化大小
        this.singleListNode = new SingleListNode(0, null);// 初始化哨兵节点
    }

    /**
     * 获取指定索引节点的值
     *
     * @param index 索引位置
     * @return 节点值
     */
    public int get(int index) {
        if (index > size - 1 || index < 0)
            return -1;
        SingleListNode currentNode = this.singleListNode;
        for (int i = 0; i < index + 1; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.val;
    }

    /**
     * 在链表头部添加节点
     *
     * @param val 节点值
     */
    public void addAtHead(int val) {
        this.addAtIndex(-1, val);
    }

    /**
     * 在链表尾部添加节点
     *
     * @param val 节点值
     */
    public void addAtTail(int val) {
        this.addAtIndex(this.size, val);
    }

    /**
     * 在指定的索引位置添加节点
     *
     * @param index 索引位置
     * @param val   节点信息
     */
    public void addAtIndex(int index, int val) {
        if (index > size)
            return;
        if (index < 0) {
            index = 0;
        }
        SingleListNode currentNode = this.singleListNode;// 从哨兵节点开始算起，因为可能在最前面加节点
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        currentNode.next = new SingleListNode(val, currentNode.next);
        this.size++;
    }

    /**
     * 删除指定索引的节点
     *
     * @param index 索引值
     */
    public void deleteAtIndex(int index) {
        if (index > size - 1 || index < 0)
            return;
        this.size--;
        if (index == 0) {
            this.singleListNode = this.singleListNode.next;
            return;
        }
        SingleListNode currentNode = this.singleListNode;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        currentNode.next = currentNode.next.next;
    }
}
