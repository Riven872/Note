package com.automannn.test;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Scanner;

/**
 * @author chenkh
 * @time 2022/1/9
 */
public class Test {
    public static void main(String[] args) throws IOException {
        System.out.println(ClassLoader.getSystemResource("D:/mytest/myFile.txt"));
    }
}
