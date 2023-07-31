package com.edu.binarytrees.E94;

import com.edu.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author riven
 * @date 2023/7/31 0031 13:27
 * @description 94. 二叉树的中序遍历
 */
public class E94 {
    public List<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        inorder(root, res);
        return res;
    }

    /**
     * 递归法
     *
     * @param root
     * @param list
     */
    private void inorder(TreeNode root, List<Integer> list) {
        if (root == null)
            return;
        inorder(root.left, list);
        list.add(root.val);
        inorder(root.right, list);
    }

    /**
     * 迭代法
     *
     * @param root
     * @return
     */
    private List<Integer> inorder(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        ArrayList<Integer> res = new ArrayList<>();
        if (root == null)
            return res;
        TreeNode currentNode = root;
        while (currentNode != null || !stack.isEmpty()) {
            if (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.left;
            } else {
                currentNode = stack.pop();
                res.add(currentNode.val);
                currentNode = currentNode.right;
            }
        }
        return res;
    }
}
