package com.edu.binarytrees.M654;

import com.edu.binarytrees.TreeNode;

import java.util.Arrays;

/**
 * @author riven
 * @date 2023/8/3 0003 21:22
 * @description 654. 最大二叉树
 */
public class M654 {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums.length == 0)
            return null;
        int maxVal = Arrays.stream(nums).max().getAsInt();
        int index;
        for (index = 0; index < nums.length; index++)
            if (nums[index] == maxVal)
                break;
        TreeNode root = new TreeNode(maxVal);
        if (nums.length == 1)
            return root;
        int[] leftNums = Arrays.copyOfRange(nums, 0, index);
        int[] rightNums = Arrays.copyOfRange(nums, index + 1, nums.length);
        root.left = constructMaximumBinaryTree(leftNums);
        root.right = constructMaximumBinaryTree(rightNums);
        return root;
    }
}
