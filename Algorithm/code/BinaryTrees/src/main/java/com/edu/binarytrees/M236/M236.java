package com.edu.binarytrees.M236;

import com.edu.binarytrees.TreeNode;

/**
 * @author riven
 * @date 2023/8/4 0004 14:47
 * @description 236. 二叉树的最近公共祖先
 */
public class M236 {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null)
            return null;
        if (root == p || root == q)
            return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null)
            return root;
        else if (left == null && right != null)
            return right;
        else if (left != null && right == null)
            return left;
        else
            return null;
    }
}
