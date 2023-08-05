package com.edu.binarytrees.M235;

import com.edu.binarytrees.TreeNode;

/**
 * @author riven
 * @date 2023/8/4 0004 16:03
 * @description 235. 二叉搜索树的最近公共祖先
 */
public class M235 {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root.val < p.val && root.val < q.val)
            return lowestCommonAncestor(root.right, p, q);
        if (root.val > p.val && root.val > q.val)
            return lowestCommonAncestor(root.left, p, q);
        return root;
    }
}
