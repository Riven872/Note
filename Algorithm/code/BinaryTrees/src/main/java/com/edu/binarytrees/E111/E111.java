package com.edu.binarytrees.E111;

import com.edu.binarytrees.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author riven
 * @date 2023/7/31 0031 19:43
 * @description 111. 二叉树的最小深度
 */
public class E111 {
    public int minDepth(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        int minDepth = 0;
        if (root == null)
            return minDepth;
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode node = queue.remove();
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
                if (node.left == null && node.right == null)
                    return minDepth + 1;
            }
            minDepth++;
        }
        return minDepth;
    }
}
