package com.edu.E367;

/**
 * 367. 有效的完全平方数
 */
public class E367 {
    public static void main(String[] args) {
        System.out.println(isPerfectSquare(1));
    }

    public static boolean isPerfectSquare(int num) {
        int low = 1;
        int high = num;
        if (num == 0)
            return true;
        int mid = 0;
        while (low <= high) {
            mid = (high - low) / 2 + low;
            if (mid == num / mid)
                break;
            else if (mid < num / mid)
                low = mid + 1;
            else
                high = mid - 1;
        }
        return mid * mid == num;
    }
}
