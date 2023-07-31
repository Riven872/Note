package com.edu.binarytrees.E104;

import com.edu.binarytrees.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author riven
 * @date 2023/7/31 0031 19:38
 * @description 104. 二叉树的最大深度
 */
public class E104 {
    public int maxDepth(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        int treeDeep = 0;
        if (root == null)
            return treeDeep;
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode node = queue.remove();
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
            treeDeep++;
        }
        return treeDeep;
    }
}
