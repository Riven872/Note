package com.edu.binarytrees.M513;

import com.edu.binarytrees.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author riven
 * @date 2023/8/2 0002 15:53
 * @description 513. 找树左下角的值
 */
public class M513 {
    public int findBottomLeftValue(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int res = root.val;
        while (!queue.isEmpty()) {
            int size = queue.size();
            res = queue.peek().val;
            while (size-- > 0) {
                TreeNode node = queue.remove();
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
        }
        return res;
    }
}
