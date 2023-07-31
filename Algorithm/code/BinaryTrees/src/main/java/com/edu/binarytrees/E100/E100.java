package com.edu.binarytrees.E100;

import com.edu.binarytrees.TreeNode;

import java.util.Stack;

/**
 * @author riven
 * @date 2023/7/31 0031 22:26
 * @description 100. 相同的树
 */
public class E100 {
    /**
     * 迭代法
     *
     * @param p
     * @param q
     * @return
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        Stack<TreeNode> stack = new Stack<>();
        if (p == null && null == q)
            return true;
        stack.push(p);
        stack.push(q);
        while (!stack.isEmpty()) {
            TreeNode node1 = stack.pop();
            TreeNode node2 = stack.pop();
            if (node1 == null && node2 == null)
                continue;
            if (node1 == null || node2 == null || node1.val != node2.val)
                return false;
            stack.push(node1.left);
            stack.push(node2.left);
            stack.push(node1.right);
            stack.push(node2.right);
        }
        return true;
    }
}
