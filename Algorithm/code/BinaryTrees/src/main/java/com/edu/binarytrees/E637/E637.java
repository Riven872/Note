package com.edu.binarytrees.E637;

import com.edu.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author riven
 * @date 2023/7/31 0031 18:03
 * @description 637. 二叉树的层平均值
 */
public class E637 {
    public List<Double> averageOfLevels(TreeNode root) {
        ArrayList<Double> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root == null)
            return res;
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            int nums = queue.size();
            double sum = 0.0;
            while (size-- > 0) {
                TreeNode node = queue.remove();
                sum += node.val;
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
            res.add(sum / nums);
        }
        return res;
    }
}
