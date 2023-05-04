package com.automannn.springPractice.componentScan;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

/**
 * @author chenkh
 * @time 2021/9/24 15:49
 */
public class ScannerTest {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanFactory);
        scanner.addExcludeFilter(new TypeFilter() {
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
               Set types= metadataReader.getAnnotationMetadata().getAnnotationTypes();
               if (types.contains(IgnoreBean.class.getName())){
                   return true;
               }else {
                   return false;
               }
            }
        });

        scanner.addIncludeFilter(new TypeFilter() {
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
               String clazzName =  metadataReader.getClassMetadata().getClassName();

                try {
                    if (AnnotationUtils.getAnnotation(ClassUtils.forName(clazzName,getClass().getClassLoader()),AutomannBean.class)!=null){
                        return true;
                    }else {
                        return false;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        scanner.scan("com.automannn.springPractice.componentScan");
        System.out.println(beanFactory.getBeanDefinitionCount());
        System.out.println(Arrays.asList(beanFactory.getBeanDefinitionNames()));
    }
}
