package com.edu.binarytrees.M106;

import com.edu.binarytrees.TreeNode;

import java.util.Arrays;

/**
 * @author riven
 * @date 2023/8/2 0002 23:16
 * @description 106. 从中序与后序遍历序列构造二叉树
 */
public class M106 {
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (postorder.length == 0 || inorder.length == 0)
            return null;
        int val = postorder[postorder.length - 1];
        TreeNode root = new TreeNode(val);
        if (postorder.length == 1)
            return root;
        int index;// 代表左孩子区间的长度
        for (index = 0; index < inorder.length; index++)
            if (root.val == inorder[index])
                break;
        int[] inorderLeft = Arrays.copyOfRange(inorder, 0, index);
        int[] inorderRight = Arrays.copyOfRange(inorder, index + 1, inorder.length);
        int[] postorderLeft = Arrays.copyOfRange(postorder, 0, index);
        int[] postorderRight = Arrays.copyOfRange(postorder, index, inorder.length - 1);
        root.left = buildTree(inorderLeft, postorderLeft);
        root.right = buildTree(inorderRight, postorderRight);
        return root;
    }
}
