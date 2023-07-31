package com.edu.binarytrees;

/**
 * 二叉树结点
 */
public class TreeNode {
    /**
     * 结点存储的值
     */
    public int val;

    /**
     * 左子树
     */
    public TreeNode left;

    /**
     * 右子树
     */
    public TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode() {
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
