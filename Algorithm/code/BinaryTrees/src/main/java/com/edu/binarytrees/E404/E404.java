package com.edu.binarytrees.E404;

import com.edu.binarytrees.TreeNode;

/**
 * @author riven
 * @date 2023/8/2 0002 15:15
 * @description 404. 左叶子之和
 */
public class E404 {
    public int sumOfLeftLeaves(TreeNode root) {
        int sum = 0;
        if (root == null)
            return 0;
        if (root.left == null && root.right == null)
            return 0;
        int leftLeaves = sumOfLeftLeaves(root.left);
        if (root.left != null && root.left.left == null && root.left.right == null)
            leftLeaves += root.left.val;
        int rightLeaves = sumOfLeftLeaves(root.right);
        sum += leftLeaves + rightLeaves;
        return sum;
    }
}
