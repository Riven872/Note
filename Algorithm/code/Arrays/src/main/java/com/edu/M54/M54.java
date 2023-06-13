package com.edu.M54;

import java.util.ArrayList;
import java.util.List;

/**
 * @author riven
 * @date 2023/6/13 0013 22:18
 * @description 54. 螺旋矩阵
 */
public class M54 {
    public static void main(String[] args) {
        int[][] arr = new int[][]{{6}, {9}, {7}};
        int[][] arr2 = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        // System.out.println(spiralOrder(arr));
        System.out.println(spiralOrder(arr2));
    }

    public static List<Integer> spiralOrder(int[][] matrix) {
        int start = 0;
        int i, j;
        int rows = matrix.length;
        int columns = matrix[0].length;
        int loop = Math.min(rows, columns) / 2;
        int mid = loop;
        int offset = 1;
        ArrayList<Integer> list = new ArrayList<>();
        while (loop > 0) {
            // 上侧，从左到右
            for (j = start; j < columns - offset; j++) {
                list.add(matrix[start][j]);
            }
            // 右侧，从上到下
            for (i = start; i < rows - offset; i++) {
                list.add(matrix[i][j]);
            }
            // 下侧，从右到左
            for (; j > offset - 1; j--) {
                list.add(matrix[i][j]);
            }
            // 左侧，从下到上
            for (; i > offset - 1; i--) {
                list.add(matrix[i][j]);
            }
            start++;
            loop--;
            offset++;
        }
        // 处理特殊情况：1、单行单列 2、奇数矩阵取中间元素
        if (Math.min(columns, rows) % 2 != 0) {
            if (rows > columns) {
                for (i = mid; i <= mid + rows - columns; i++) {
                    list.add(matrix[i][mid]);
                }
            } else {
                for (j = mid; j <= mid + columns - rows; j++) {
                    list.add(matrix[mid][j]);
                }
            }
        }
        return list;
    }
}
