package com.edu.binarytrees.M701;

import com.edu.binarytrees.TreeNode;

/**
 * @author riven
 * @date 2023/8/4 0004 18:14
 * @description 701. 二叉搜索树中的插入操作
 */
public class M701 {
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null)
            return new TreeNode(val);
        if (root.val < val)
            root.right = insertIntoBST(root.right, val);
        if (root.val > val)
            root.left = insertIntoBST(root.left, val);
        return root;
    }
}
