package com.edu.binarytrees.M538;

import com.edu.binarytrees.TreeNode;

/**
 * @author riven
 * @date 2023/8/6 0006 2:54
 * @description 538. 把二叉搜索树转换为累加树
 */
public class M538 {
    TreeNode preNode;

    public TreeNode convertBST(TreeNode root) {
        if (root == null)
            return null;
        if (root.right != null)
            root.right = convertBST(root.right);
        if (preNode != null)
            root.val += preNode.val;
        preNode = root;
        if (root.left != null)
            root.left = convertBST(root.left);
        return root;
    }
}
