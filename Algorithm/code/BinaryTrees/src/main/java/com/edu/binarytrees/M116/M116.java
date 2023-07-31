package com.edu.binarytrees.M116;

import com.edu.binarytrees.Node2;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author riven
 * @date 2023/7/31 0031 18:39
 * @description 116. 填充每个节点的下一个右侧节点指针
 */
public class M116 {
    public Node2 connect(Node2 root) {
        Queue<Node2> queue = new LinkedList<>();
        if (root == null)
            return null;
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            Node2 node = null;
            while (size-- > 0) {
                node = queue.remove();
                node.next = queue.peek();
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
            node.next = null;
        }
        return root;
    }
}
