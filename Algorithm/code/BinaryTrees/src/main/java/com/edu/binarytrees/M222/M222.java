package com.edu.binarytrees.M222;

import com.edu.binarytrees.TreeNode;

/**
 * @author riven
 * @date 2023/8/1 0001 14:40
 * @description 222. 完全二叉树的节点个数
 */
public class M222 {
    public int countNodes(TreeNode root) {
        if (root == null)
            return 0;
        int leftDepth = 1;
        int rightDepth = 1;
        TreeNode left = root.left;
        TreeNode right = root.right;
        while (left != null) {
            left = left.left;
            leftDepth++;
        }
        while (right != null) {
            right = right.right;
            rightDepth++;
        }
        if (rightDepth == leftDepth)
            return (int) Math.pow(2, rightDepth) - 1;
        return countNodes(root.left) + countNodes(root.right) + 1;
    }
}
