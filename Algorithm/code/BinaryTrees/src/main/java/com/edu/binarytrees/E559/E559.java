package com.edu.binarytrees.E559;

import com.edu.binarytrees.Node;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author riven
 * @date 2023/7/31 0031 23:22
 * @description 559. N 叉树的最大深度
 */
public class E559 {
    public int maxDepth(Node root) {
        Queue<Node> queue = new LinkedList<>();
        int maxDepth = 0;
        if (root == null)
            return maxDepth;
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                Node node = queue.remove();
                if (node.children != null)
                    queue.addAll(node.children);
            }
            maxDepth++;
        }
        return maxDepth;
    }
}
