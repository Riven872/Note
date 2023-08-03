package com.edu.binarytrees.E700;

import com.edu.binarytrees.TreeNode;

/**
 * @author riven
 * @date 2023/8/3 0003 22:19
 * @description 700. 二叉搜索树中的搜索
 */
public class E700 {
    /**
     * 递归法
     *
     * @param root
     * @param val
     * @return
     */
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null)
            return null;
        else if (root.val < val)
            return searchBST(root.right, val);
        else if (root.val > val)
            return searchBST(root.left, val);
        return root;
    }

    /**
     * 迭代法
     *
     * @param root
     * @param val
     * @return
     */
    public TreeNode searchBST2(TreeNode root, int val) {
        while (root != null) {
            if (root.val > val)
                root = root.right;
            else if (root.val < val)
                root = root.left;
            else
                return root;
        }
        return null;
    }
}
