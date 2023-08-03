package com.edu.binarytrees.E257;

import com.edu.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author riven
 * @date 2023/8/2 0002 13:51
 * @description 257. 二叉树的所有路径
 */
public class E257 {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        List<Integer> paths = new ArrayList<>();
        traversal(root, paths, res);
        return res;
    }

    private void traversal(TreeNode root, List<Integer> paths, List<String> res) {
        paths.add(root.val);
        // 遇到叶子结点
        if (root.left == null && root.right == null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < paths.size() - 1; i++)
                sb.append(paths.get(i)).append("->");
            sb.append(paths.get(paths.size() - 1));// 拼接最后一个结点
            res.add(String.valueOf(sb));
            return;
        }
        if (root.left != null) {
            traversal(root.left, paths, res);
            paths.remove(paths.size() - 1);// 回溯
        }
        if (root.right != null) {
            traversal(root.right, paths, res);
            paths.remove(paths.size() - 1);// 回溯
        }
    }
}
