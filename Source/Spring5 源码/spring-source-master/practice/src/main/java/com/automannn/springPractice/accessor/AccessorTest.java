package com.automannn.springPractice.accessor;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.expression.BeanFactoryAccessor;
import org.springframework.context.expression.EnvironmentAccessor;
import org.springframework.context.expression.MapAccessor;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenkh
 * @time 2021/9/24 15:04
 */
public class AccessorTest {
    public static void main(String[] args) {
        BeanFactoryAccessor beanFactoryAccessor = new BeanFactoryAccessor();
        EnvironmentAccessor environmentAccessor = new EnvironmentAccessor();
        MapAccessor mapAccessor = new MapAccessor();

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerSingleton("myBean",new Object());

        EvaluationContext evaluationContext = new StandardEvaluationContext();

        try {
            System.out.println(beanFactoryAccessor.canRead(evaluationContext,beanFactory,"myBean"));
            System.out.println(beanFactoryAccessor.read(evaluationContext,beanFactory,"myBean"));
            System.out.println(beanFactoryAccessor.canWrite(evaluationContext,beanFactory,"myBean"));
            beanFactoryAccessor.write(evaluationContext,beanFactory,"myBean",new Object());
        } catch (AccessException e) {
            e.printStackTrace();
        }

        Environment environment = new StandardEnvironment();
        try {
            System.out.println(environmentAccessor.canRead(evaluationContext,environment,"java.version"));
            System.out.println(environmentAccessor.read(evaluationContext,environment,"java.version"));
            System.out.println(environmentAccessor.canWrite(evaluationContext,environment,"java.version"));
            environmentAccessor.write(evaluationContext,environment,"java.version",18.0);
        } catch (AccessException e) {
            e.printStackTrace();
        }

        Map map = new HashMap();
        map.put("name","automannn");
        try {
            System.out.println(mapAccessor.canRead(evaluationContext,map,"name"));
            System.out.println(mapAccessor.read(evaluationContext,map,"name"));
            System.out.println(mapAccessor.canWrite(evaluationContext,map,"name"));
            mapAccessor.write(evaluationContext,map,"name","testname");
            System.out.println(mapAccessor.read(evaluationContext,map,"name"));
        } catch (AccessException e) {
            e.printStackTrace();
        }


    }
}
