package com.edu.binarytrees.E101;


import com.edu.binarytrees.TreeNode;

import java.util.Stack;

/**
 * @author riven
 * @date 2023/7/31 0031 21:45
 * @description 101. 对称二叉树
 */
public class E101 {
    public boolean isSymmetric(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        if (root == null)
            return true;
        stack.push(root.left);
        stack.push(root.right);
        while (!stack.isEmpty()) {
            TreeNode rightNode = stack.pop();
            TreeNode leftNode = stack.pop();
            if (rightNode == null && leftNode == null)
                continue;
            if (rightNode == null || leftNode == null || (rightNode.val != leftNode.val)) {
                return false;
            }
            stack.push(leftNode.left);
            stack.push(rightNode.right);
            stack.push(leftNode.right);
            stack.push(rightNode.left);
        }
        return true;
    }
}
