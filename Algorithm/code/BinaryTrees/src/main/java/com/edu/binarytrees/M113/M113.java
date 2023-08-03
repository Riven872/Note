package com.edu.binarytrees.M113;

import com.edu.binarytrees.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author riven
 * @date 2023/8/2 0002 21:38
 * @description 113. 路径总和 II
 */
public class M113 {
    public static void main(String[] args) {
        TreeNode _7 = new TreeNode(7);
        TreeNode _2 = new TreeNode(2);
        TreeNode _11 = new TreeNode(11, _7, _2);
        TreeNode _4 = new TreeNode(4, _11, null);
        TreeNode _5 = new TreeNode(5, _4, null);
        System.out.println(new M113().pathSum(_5, 22));
        ;
    }

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        ArrayList<List<Integer>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        traversal_1(root, targetSum, list, res);
        // traversal_2(root, targetSum, list, res);
        return res;
    }

    private void traversal_1(TreeNode root, int targetSum, List<Integer> paths, List<List<Integer>> res) {
        if (root == null)
            return;
        paths.add(root.val);// 根结点逻辑
        targetSum -= root.val;
        // 叶子结点逻辑
        if (root.left == null && root.right == null) {
            if (targetSum == 0)
                res.add(new ArrayList<>(paths));
            return;
        }
        if (root.left != null) {
            traversal_1(root.left, targetSum, paths, res);// 左孩子逻辑
            paths.remove(paths.size() - 1);// 回溯
        }
        if (root.right != null) {
            traversal_1(root.right, targetSum, paths, res);
            paths.remove(paths.size() - 1);// 回溯
        }
    }

    /**
     * 错误示范，见标注
     *
     * @param root
     * @param targetSum
     * @param paths
     * @param res
     */
    private void traversal_2(TreeNode root, int targetSum, List<Integer> paths, List<List<Integer>> res) {
        if (root == null)
            return;
        paths.add(root.val);
        if (root.left == null && root.right == null) {
            if (targetSum == root.val)
                res.add(new ArrayList<>(paths));
            return;
        }
        /*
          标注：
          temp 是可变的，当下层的 paths 发生变化时，上层的 temp 同步发生了变化
          无法保留当前层的 temp 数据，因此不可以省略回溯步骤，需要手动的从路径中
          删除以前的路径。
         */
        ArrayList<Integer> temp = new ArrayList<>(paths);
        if (root.left != null)
            traversal_2(root.left, targetSum - root.left.val, temp, res);
        if (root.right != null)
            traversal_2(root.right, targetSum - root.right.val, temp, res);
    }
}
