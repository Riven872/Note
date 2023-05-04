package com.automannn.springPractice.metadata;

import org.springframework.asm.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.core.type.classreading.AnnotationMetadataReadingVisitor;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.management.MXBean;

/**
 * @author chenkh
 * @time 2021/8/21 18:17
 */
public class AnnotationMedataTest {
    public static void main(String[] args) {
        StandardAnnotationMetadata metadata = new StandardAnnotationMetadata(MyBean.class);
        metadata.getIntrospectedClass();
        metadata.getAnnotations();
        metadata.getAnnotationTypes();
        metadata.getInterfaceNames();

        System.out.println(metadata.getAnnotatedMethods("org.springframework.beans.factory.annotation.Autowired"));
        metadata.getEnclosingClassName();
        metadata.getMemberClassNames();
        metadata.getMetaAnnotationTypes("RequestMapping");

    }

    @MXBean
    static class MyBean{
        private String nameA;
        private String nameB;

        @Autowired
        public String getNameA() {
            return nameA;
        }

        @RequestMapping
        public void setNameA(String nameA) {
            this.nameA = nameA;
        }

        public String getNameB() {
            return nameB;
        }

        public void setNameB(String nameB) {
            this.nameB = nameB;
        }
    }
}
