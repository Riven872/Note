package com.edu.arrays.M59;

import java.util.Arrays;

/**
 * 59. 螺旋矩阵 II
 * 2023年6月13日16:52:51
 */
public class M59 {
    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(generateMatrix(3)));
    }

    public static int[][] generateMatrix(int n) {
        int loop = 0;// 循环圈数
        int i, j;// i 行，j 列
        int start = 0;// 起始位置
        int count = 1;// 存入的数值
        int[][] matrix = new int[n][n];
        while (loop++ < n / 2) {
            // 上侧，从左到右
            for (j = start; j < n - loop; j++) {
                matrix[start][j] = count++;
            }
            // 右侧，从上到下
            for (i = start; i < n - loop; i++) {
                matrix[i][j] = count++;
            }
            // 下侧，从右到左
            for (; j > loop - 1; j--) {
                matrix[i][j] = count++;
            }
            // 左侧，从下到上
            for (; i > loop - 1; i--) {
                matrix[i][j] = count++;
            }
            start++;
        }
        // 如果为奇数，中间的数字需要单独填充
        if (n % 2 != 0)
            matrix[start][start] = count;
        return matrix;
    }
}
