package com.edu.binarytrees.E108;

import com.edu.binarytrees.TreeNode;

/**
 * @author riven
 * @date 2023/8/5 0005 18:33
 * @description 108. 将有序数组转换为二叉搜索树
 */
public class E108 {
    public TreeNode sortedArrayToBST(int[] nums) {
        return buildTree(nums, 0, nums.length - 1);
    }

    private TreeNode buildTree(int[] nums, int begin, int end) {
        if (begin > end)
            return null;
        int mid = begin + (end - begin) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = buildTree(nums, begin, mid - 1);
        root.right = buildTree(nums, mid + 1, end);
        return root;
    }
}
