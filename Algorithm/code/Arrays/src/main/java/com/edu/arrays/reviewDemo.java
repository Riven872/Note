package com.edu.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author riven
 * @date 2023/6/14 0014 13:23
 * @description 二刷及以上 coding
 */
public class reviewDemo {
    public static void main(String[] args) {
        System.out.println(spiralOrder(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}}));
    }

    public static List<Integer> spiralOrder(int[][] matrix) {
        int i, j;
        int start = 0;
        int offset = 1;
        int m = matrix.length;
        int n = matrix[0].length;
        int loop = Math.min(m, n) / 2;
        int mid = loop;
        ArrayList<Integer> list = new ArrayList<>();
        while (loop > 0) {
            for (j = start; j < n - offset; j++) {
                list.add(matrix[start][j]);
            }
            for (i = start; i < m - offset; i++) {
                list.add(matrix[i][j]);
            }
            for (; j > offset - 1; j--) {
                list.add(matrix[i][j]);
            }
            for (; i > offset - 1; i--) {
                list.add(matrix[i][j]);
            }
            offset++;
            loop--;
            start++;
        }
        if (Math.min(m, n) % 2 != 0) {
            if (m > n) {
                for (int k = start; k < m - mid; k++) {
                    list.add(matrix[k][start]);
                }
            } else {
                for (int k = start; k < n - mid; k++) {
                    list.add(matrix[start][k]);
                }
            }
        }
        return list;
    }
}
