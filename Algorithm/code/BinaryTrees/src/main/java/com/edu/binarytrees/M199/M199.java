package com.edu.binarytrees.M199;

import com.edu.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author riven
 * @date 2023/7/31 0031 17:24
 * @description 199. 二叉树的右视图
 */
public class M199 {
    public List<Integer> rightSideView(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root == null)
            return list;
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            list.add(queue.peek().val);
            while (size-- > 0){
                TreeNode node = queue.remove();
                if (node.right != null)
                    queue.add(node.right);
                if (node.left != null)
                    queue.add(node.left);
            }
        }
        return list;
    }
}
