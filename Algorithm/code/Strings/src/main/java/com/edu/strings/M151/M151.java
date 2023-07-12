package com.edu.strings.M151;

/**
 * @author riven
 * @date 2023/7/7 0007 13:25
 * @description 151. 反转字符串中的单词
 */
public class M151 {
    public static void main(String[] args) {
        System.out.println(new M151().reverseWords("the sky is blue"));
    }

    public String reverseWords(String s) {
        // 去掉前导尾随的空格，并格式化空格为 1
        StringBuilder stringBuilder = eraseSpace(s);
        // 反转一整个字符串
        reverseStr(stringBuilder, 0, stringBuilder.length() - 1);
        // 反转单词
        reversAll(stringBuilder);
        // 返回值
        return String.valueOf(stringBuilder);
    }

    /**
     * 反转指定范围内的字符串
     *
     * @param stringBuilder 格式化后的新字符串
     * @param begin         开始索引
     * @param end           结束索引
     */
    private void reverseStr(StringBuilder stringBuilder, int begin, int end) {
        while (begin < end) {
            char temp = stringBuilder.charAt(begin);
            stringBuilder.setCharAt(begin, stringBuilder.charAt(end));
            stringBuilder.setCharAt(end, temp);
            begin++;
            end--;
        }
    }

    /**
     * 去除字符串中前导尾随的空格，并格式化连续空格个数为 1
     *
     * @param s 原始字符串
     * @return 拼接后的字符串
     */
    private StringBuilder eraseSpace(String s) {
        int begin = 0;
        int length = s.length();
        while (begin < length && s.charAt(begin) == ' ')
            begin++;
        while (begin < length && s.charAt(length - 1) == ' ')
            length--;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = begin; i < length; i++) {
            if (s.charAt(i) != ' ' || stringBuilder.charAt(stringBuilder.length() - 1) != ' ') {
                stringBuilder.append(s.charAt(i));
            }
        }
        return stringBuilder;
    }

    /**
     * 反转所有的单词
     *
     * @param stringBuilder
     */
    private void reversAll(StringBuilder stringBuilder) {
        int slowIndex = 0;
        while (slowIndex < stringBuilder.length()) {
            int fastIndex = slowIndex;
            // 同样也要防止快指针越界
            while (fastIndex < stringBuilder.length() && stringBuilder.charAt(fastIndex) != ' ')
                fastIndex++;
            reverseStr(stringBuilder, slowIndex, fastIndex - 1);
            slowIndex = ++fastIndex;
        }
    }
}
