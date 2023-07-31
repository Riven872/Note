package com.edu.binarytrees.M515;

import com.edu.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author riven
 * @date 2023/7/31 0031 18:32
 * @description 515. 在每个树行中找最大值
 */
public class M515 {
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root == null)
            return res;
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            int maxVal = Integer.MIN_VALUE;
            while (size-- > 0) {
                TreeNode node = queue.remove();
                maxVal = Math.max(maxVal, node.val);
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
            res.add(maxVal);
        }
        return res;
    }
}
