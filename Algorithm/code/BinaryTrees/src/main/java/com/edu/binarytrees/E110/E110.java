package com.edu.binarytrees.E110;

import com.edu.binarytrees.TreeNode;

/**
 * @author riven
 * @date 2023/8/1 0001 14:59
 * @description 110. 平衡二叉树
 */
public class E110 {
    public boolean isBalanced(TreeNode root) {
        return getDepth(root) != -1;
    }

    private int getDepth(TreeNode root) {
        if (root == null)
            return 0;
        // 单层递归逻辑，获得左右子树的深度
        int leftDepth = getDepth(root.left);
        int rightDepth = getDepth(root.right);
        // 递归出口，不满足平衡树的高度差
        if (Math.abs(leftDepth - rightDepth) > 1)
            return -1;
        // 递归出口，当前结点深度不满足条件时直接返回
        if (leftDepth == -1)
            return -1;
        if (rightDepth == -1)
            return -1;
        // 递归出口，当前结点深度满足平衡条件时返回深度值
        return Math.max(leftDepth, rightDepth) + 1;
    }
}
