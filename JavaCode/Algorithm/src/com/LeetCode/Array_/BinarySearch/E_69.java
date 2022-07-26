package com.LeetCode.Array_.BinarySearch;

@SuppressWarnings({"all"})
public class E_69 {

    public static void main(String[] args) {
        E_69 test = new E_69();

        System.out.println(test.mySqrt(26));
        ;
    }

    public int mySqrt(int x) {
        if (x == 0) {
            return 0;
        }
        if (x == 1) {
            return 1;
        }
        int low = 0;
        int high = x;
        int res = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (x / mid < mid) {
                high = mid - 1;
            } else {
                low = mid + 1;
                res = mid;
            }
        }
        return res;
    }
}
