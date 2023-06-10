package com.edu.E69;

/**
 * 69. x 的平方根
 */
public class E69 {
    public static void main(String[] args) {
        System.out.println(mySqrt(5));
    }

    public static int mySqrt(int x) {
        int low = 1;
        int high = x;
        if (x == 0)
            return 0;
        while (low <= high) {
            int mid = (high - low) / 2 + low;
            if (mid == x / mid) {
                return mid;
            } else if (mid > x / mid) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return high;
    }
}
