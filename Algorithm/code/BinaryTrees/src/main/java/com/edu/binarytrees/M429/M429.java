package com.edu.binarytrees.M429;

import com.edu.binarytrees.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author riven
 * @date 2023/7/31 0031 18:21
 * @description 429. N 叉树的层序遍历
 */
public class M429 {
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        if (root == null)
            return res;
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            while (size-- > 0) {
                Node node = queue.remove();
                list.add(node.val);
                if (node.children != null)
                    queue.addAll(node.children);
            }
            res.add(list);
        }
        return res;
    }
}
