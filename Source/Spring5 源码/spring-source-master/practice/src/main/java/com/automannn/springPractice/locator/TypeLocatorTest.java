package com.automannn.springPractice.locator;

import org.springframework.expression.TypeLocator;
import org.springframework.expression.spel.support.StandardTypeLocator;

/**
 * @author chenkh
 * @time 2021/9/24 15:39
 */
public class TypeLocatorTest {
    public static void main(String[] args) {
        TypeLocator typeLocator = new StandardTypeLocator();

        System.out.println(typeLocator.findType("String"));
    }
}
