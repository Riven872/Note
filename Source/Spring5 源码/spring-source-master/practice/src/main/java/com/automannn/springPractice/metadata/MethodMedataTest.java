package com.automannn.springPractice.metadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.type.StandardMethodMetadata;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chenkh
 * @time 2021/8/21 18:57
 */
public class MethodMedataTest {
    public static void main(String[] args) {
        StandardMethodMetadata methodMetadata = new StandardMethodMetadata(MyBean.class.getDeclaredMethods()[0]);
        System.out.println(methodMetadata.getAnnotations());
        System.out.println(methodMetadata.getDeclaringClassName());
        System.out.println(methodMetadata.getReturnTypeName());
        System.out.println(methodMetadata.getAllAnnotationAttributes(Autowired.class.getName()));
        System.out.println(methodMetadata.getAnnotationAttributes(Autowired.class.getName()));
        System.out.println(methodMetadata.getAnnotationAttributes(RequestMapping.class.getName()));
    }

    static class MyBean{
        private String name;

        @Autowired
        @RequestMapping
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
