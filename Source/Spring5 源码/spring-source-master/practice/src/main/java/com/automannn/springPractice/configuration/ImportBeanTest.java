package com.automannn.springPractice.configuration;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author chenkh
 * @time 2021/9/27 14:29
 */
public class ImportBeanTest {

    public static class MyImportSelector implements ImportSelector{

        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            //获取声明该注解的配置信息，并根据配置信息，返回需要装载的bean名称
            Map<String,Object> attributes= importingClassMetadata.getAnnotationAttributes(ImportStrategy.class.getName());
            if (attributes!=null){
              Boolean route= (Boolean) attributes.get("highPerformance");
              if (route){
                  return new String[]{StrategyBean.HighConfiger.class.getName()};
              }else{
                  return new String[]{StrategyBean.LowConfiger.class.getName()};
              }
            }
            return new String[0];
        }
    }

    public static class MyBeanDefintionRegistrar implements ImportBeanDefinitionRegistrar{
        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            String clazzName= importingClassMetadata.getClassName();
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClassName(clazzName);
            registry.registerBeanDefinition(importingClassMetadata.getClassName(),beanDefinition);

        }
    }
}
