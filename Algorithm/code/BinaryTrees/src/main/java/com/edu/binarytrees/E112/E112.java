package com.edu.binarytrees.E112;

import com.edu.binarytrees.TreeNode;

/**
 * @author riven
 * @date 2023/8/2 0002 17:36
 * @description 112. 路径总和
 */
public class E112 {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        // if (root == null)
        //     return false;
        // return traversal_1(root, targetSum - root.val);

        return traversal_2(root, targetSum);
    }

    /**
     * 递归法（有回溯步骤）
     *
     * @param root
     * @param targetSum
     * @return
     */
    public boolean traversal_1(TreeNode root, int targetSum) {
        if (root.left == null && root.right == null)
            return targetSum == 0;
        if (root.left != null) {
            targetSum -= root.left.val;// 左孩子逻辑
            if (traversal_1(root.left, targetSum))
                return true;
            targetSum += root.left.val;// 回溯
        }
        if (root.right != null) {
            targetSum -= root.right.val;
            if (traversal_1(root.right, targetSum))
                return true;
            targetSum += root.right.val;
        }
        return false;
    }

    /**
     * 递归法（精简回溯步骤）
     * @param root
     * @param targetSum
     * @return
     */
    public boolean traversal_2(TreeNode root, int targetSum) {
        if (root == null)
            return false;
        if (root.left == null && root.right == null)
            return targetSum == root.val;
        return traversal_2(root.left, targetSum - root.val) || traversal_2(root.right, targetSum - root.val);// 左右孩子路径和
    }
}
