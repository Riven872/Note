package com.edu.binarytrees.E530;

import com.edu.binarytrees.TreeNode;

/**
 * @author riven
 * @date 2023/8/3 0003 23:24
 * @description 530. 二叉搜索树的最小绝对差
 */
public class E530 {
    TreeNode pre = null;

    int minVal = Integer.MAX_VALUE;

    public int getMinimumDifference(TreeNode root) {
        if (root == null)
            return 0;
        getMinimumDifference(root.left);
        if (pre != null)
            minVal = Math.min(minVal, root.val - pre.val);
        pre = root;
        getMinimumDifference(root.right);
        return minVal;
    }
}
