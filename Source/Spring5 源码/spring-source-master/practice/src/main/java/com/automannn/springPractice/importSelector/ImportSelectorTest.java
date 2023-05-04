package com.automannn.springPractice.importSelector;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chenkh
 * @time 2021/9/29 14:30
 */
@Import(ImportSelectorTest.MyDeferredImportSelector.class)
public class ImportSelectorTest {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(context.getDefaultListableBeanFactory());
        scanner.addIncludeFilter(new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                if (metadataReader.getClassMetadata().getClassName().equals(ImportSelectorTest.class.getName())){
                    return true;
                }
                return false;
            }
        });
        scanner.scan(ImportSelectorTest.class.getPackage().getName());

        context.refresh();

        System.out.println(Arrays.asList(context.getBeanDefinitionNames()));
    }




    public static class MySelector implements ImportSelector{

        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return new String[]{MyConfiguration.class.getName()};
        }
    }

    public static class MyDeferredImportSelector implements DeferredImportSelector{

        @Override
        public Class<? extends Group> getImportGroup() {
            return MyGroup.class;
        }

        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return new String[0];
        }

        public static class MyGroup implements Group{

            private final List<Entry> imports = new ArrayList<>();

            @Override
            public void process(AnnotationMetadata metadata, DeferredImportSelector selector) {
                for (String importClassName : selector.selectImports(metadata)) {
                    this.imports.add(new Entry(metadata, importClassName));
                }
            }

            @Override
            public Iterable<Entry> selectImports() {
                return this.imports;
            }
        }
    }


    public static class MyConfiguration{
        @Bean
        public Object myCandidateBean(){
            return new Object();
        }
    }
}
