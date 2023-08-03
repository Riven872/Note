package com.edu.binarytrees.E617;

import com.edu.binarytrees.TreeNode;

/**
 * @author riven
 * @date 2023/8/3 0003 21:50
 * @description 617. 合并二叉树
 */
public class E617 {
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null)
            return root2;
        if (root2 == null)
            return root1;
        root1.val += root2.val;
        root1.left = mergeTrees(root1.left, root2.left);
        root2.right = mergeTrees(root1.right, root2.right);
        return root1;
    }
}
