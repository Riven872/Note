package com.edu.binarytrees.E572;

import com.edu.binarytrees.TreeNode;

/**
 * @author riven
 * @date 2023/7/31 0031 22:39
 * @description 572. 另一棵树的子树
 */
public class E572 {
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null && subRoot == null)
            return true;
        if (root == null || subRoot == null)
            return false;
        return isSameTree(root, subRoot) ||
                isSubtree(root.left, subRoot) ||
                isSubtree(root.right, subRoot);
    }

    private boolean isSameTree(TreeNode root, TreeNode subRoot) {
        if (root == null && subRoot == null)
            return true;
        if (root == null || subRoot == null)
            return false;
        return root.val == subRoot.val &&
                isSameTree(root.left, subRoot.left) &&
                isSameTree(root.right, subRoot.right);
    }
}
