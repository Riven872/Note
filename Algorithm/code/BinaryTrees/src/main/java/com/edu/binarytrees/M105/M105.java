package com.edu.binarytrees.M105;

import com.edu.binarytrees.TreeNode;

import java.util.Arrays;

/**
 * @author riven
 * @date 2023/8/3 0003 16:43
 * @description 105. 从前序与中序遍历序列构造二叉树
 */
public class M105 {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length == 0 || inorder.length == 0)
            return null;
        TreeNode root = new TreeNode(preorder[0]);
        if (preorder.length == 1)
            return root;
        int index;
        for (index = 0; index < inorder.length; index++)
            if (root.val == inorder[index])
                break;
        int[] inorderLeft = Arrays.copyOfRange(inorder, 0, index);
        int[] inorderRight = Arrays.copyOfRange(inorder, index + 1, inorder.length);
        int[] preorderLeft = Arrays.copyOfRange(preorder, 1, inorderLeft.length);
        int[] preorderRight = Arrays.copyOfRange(preorder, inorderLeft.length, inorder.length);
        root.left = buildTree(preorderLeft, inorderLeft);
        root.right = buildTree(preorderRight, inorderRight);
        return root;
    }
}
