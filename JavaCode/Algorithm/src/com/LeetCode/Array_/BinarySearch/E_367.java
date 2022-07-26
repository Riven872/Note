package com.LeetCode.Array_.BinarySearch;

@SuppressWarnings({"all"})
public class E_367 {
    public static void main(String[] args) {
        E_367 test = new E_367();

        System.out.println(test.isPerfectSquare(9));
    }

    public boolean isPerfectSquare(int num) {
        if (num == 0 || num == 1) {
            return true;
        }
        int left = 0;
        int right = num;
        int res = 0;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (num / mid < mid) {
                right = mid - 1;
            } else{
                //相当于找最左边界的值
                left = mid + 1;
                res = mid;
            }
        }
        //如果最左边界的值平方等于num，则说明找到了正确值，如果不等于说明找到了开方值的整数部分
        if (res * res == num) {
            return true;
        }
        return false;
    }
}
