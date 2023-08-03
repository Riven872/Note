package com.edu.binarytrees.M98;

import com.edu.binarytrees.TreeNode;

import java.util.Stack;

/**
 * @author riven
 * @date 2023/8/3 0003 22:39
 * @description 98. 验证二叉搜索树
 */
public class M98 {
    TreeNode pre;

    /**
     * 递归法
     *
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {
        if (root == null)
            return true;
        boolean left = isValidBST(root.left);
        if (!left)
            return false;
        if (pre != null && pre.val >= root.val)
            return false;
        pre = root;
        boolean right = isValidBST(root.right);
        return right;
    }

    /**
     * 迭代法
     *
     * @param root
     * @return
     */
    public boolean isValidBST2(TreeNode root) {
        if (root == null)
            return true;
        TreeNode node = root;
        TreeNode pre = null;
        Stack<TreeNode> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            if (pre != null && pre.val >= node.val)
                return false;
            pre = node;
            node = node.right;
        }
        return true;
    }
}
