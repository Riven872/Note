package com.edu.binarytrees.M107;

import com.edu.binarytrees.TreeNode;

import java.util.*;

/**
 * @author riven
 * @date 2023/7/31 0031 17:14
 * @description 107. 二叉树的层序遍历 II
 */
public class M107 {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root == null)
            return res;
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            while (size-- > 0) {
                TreeNode node = queue.remove();
                list.add(node.val);
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
            res.add(list);
        }
        Collections.reverse(res);
        return res;
    }
}
