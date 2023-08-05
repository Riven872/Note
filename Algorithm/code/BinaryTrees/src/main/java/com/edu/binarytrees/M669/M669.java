package com.edu.binarytrees.M669;

import com.edu.binarytrees.TreeNode;

/**
 * @author riven
 * @date 2023/8/4 0004 19:27
 * @description 669. 修剪二叉搜索树
 */
public class M669 {
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if (root == null)
            return null;
        if (root.val > high)
            return trimBST(root.left, low, high);
        if (root.val < low)
            return trimBST(root.right, low, high);
        root.left = trimBST(root.left, low, high);
        root.right = trimBST(root.right, low, high);
        return root;
    }
}
