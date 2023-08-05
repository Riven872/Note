package com.edu.binarytrees.E501;

import com.edu.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author riven
 * @date 2023/8/4 0004 0:59
 * @description 501. 二叉搜索树中的众数
 */
public class E501 {
    public int[] findMode(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null)
            return new int[0];
        int maxCount = 0;
        int count = 0;
        TreeNode pre = null;
        TreeNode current = root;
        Stack<TreeNode> stack = new Stack<>();
        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                current = stack.pop();
                if (pre == null || pre.val != current.val)
                    count = 1;
                else
                    count++;
                if (count >= maxCount) {
                    if (count > maxCount) {
                        maxCount = count;
                        res.clear();
                    }
                    // 众数可能不止一个，如果 count = maxCount 时，说明有相同数量的众数
                    // 此时只需要添加元素即可，不需要清空集合
                    res.add(current.val);
                }
                pre = current;
                current = current.right;
            }
        }
        return res.stream().mapToInt(x -> x).toArray();
    }
}
