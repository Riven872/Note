package com.automannn.springPractice.configuration;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author chenkh
 * @time 2021/9/27 9:47
 */
/*xxx: 主要用于测试六个关键的注解处理器:
   @Configuration   ---> ConfigurationClassPostProcessor
   @Autowired   --->  AutowiredAnnotationBeanPostProcessor
   @ManagedBean,@Repository等   ---> CommonAnnotationBeanPostProcessor
   @EventListener  ---> EventListenerMethodProcessor */
public class ConfigurationTest {
    public static void main(String[] args) {
        //跟应用上下文一起使用
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(applicationContext.getDefaultListableBeanFactory());
        scanner.addIncludeFilter(new TypeFilter() {
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                return metadataReader.getClassMetadata().getClassName().equals(ConfigurerBean.class.getName());
            }
        });
        scanner.scan("com.automannn.springPractice.configuration");
        applicationContext.refresh();
        System.out.println((applicationContext.getDefaultListableBeanFactory().getBeanDefinitionCount()));
        System.out.println(Arrays.asList(applicationContext.getDefaultListableBeanFactory().getBeanDefinitionNames()));

    }
}
