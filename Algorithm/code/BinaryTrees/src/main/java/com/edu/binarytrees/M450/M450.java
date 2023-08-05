package com.edu.binarytrees.M450;

import com.edu.binarytrees.TreeNode;

/**
 * @author riven
 * @date 2023/8/4 0004 18:35
 * @description 450. 删除二叉搜索树中的节点
 */
public class M450 {
    public TreeNode deleteNode(TreeNode root, int key) {
        // 没有找到要删除的结点
        if (root == null)
            return null;
        // 找到了要删除的结点
        if (root.val == key) {
            // 删除结点的左右孩子都为空
            if (root.left == null && root.right == null)
                return null;
                // 删除结点的左孩子为空，右孩子补位
            else if (root.left == null)
                return root.right;
                // 删除结点的右孩子为空，左孩子补位
            else if (root.right == null)
                return root.left;
                // 删除的结点左右孩子都不为空
            else {
                // 找右孩子的最左侧结点
                TreeNode currentNode = root.right;
                while (currentNode.left != null)
                    currentNode = currentNode.left;
                // 将左孩子变成右孩子的附属
                currentNode.left = root.left;
                // 右孩子补位
                return root.right;
            }
        }
        // 根据 BST 特性找到需要删除的结点
        if (root.val > key)
            root.left = deleteNode(root.left, key);
        if (root.val < key)
            root.right = deleteNode(root.right, key);
        return root;
    }
}
