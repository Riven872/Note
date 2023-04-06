package com.automannn.springPractice.namespaceHandlerAndSchema;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.w3c.dom.Element;

/**
 * @author chenkh
 * @time 2021/9/9 17:17
 */
public class NameSpaceHandlerTest {

    public static void main(String[] args) {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext("classpath:atmXmlApplication.xml");
        context.close();
    }


    public static class AtmNameSpaceHandler extends NamespaceHandlerSupport {

        public void init() {
            /*xxx: 这里只需要处理，最顶级的节点即可，嵌套的元素节点，需要再内层处理*/
            registerBeanDefinitionParser("atmBean",new AtmBeanHandler());
            registerBeanDefinitionParser("refer",new ReferBeanHandler());
        }
    }

    public static class AtmBeanHandler implements BeanDefinitionParser {

        public BeanDefinition parse(Element element, ParserContext parserContext) {
            System.out.println("处理atmBean节点，可以获取到相应的属性，进行处理，主要是转化为 beanDefinition");
            System.out.println(element.getAttributes());
            System.out.println("转换后的 beanDefinition,自然会放到容器中. 完成无缝对接");
            System.out.println("内层元素需要继续进行解析");
            GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
            genericBeanDefinition.setBeanClass(BusinessBean.class);
            return genericBeanDefinition;
        }
    }

    public static class ReferBeanHandler implements BeanDefinitionParser {

        public BeanDefinition parse(Element element, ParserContext parserContext) {
            System.out.println("处理refer节点，同样是注册 beanDefinition");
            System.out.println(element.getAttributes());
            System.out.println("转换后的 beanDefinition,自然会放到容器中. 完成无缝对接");
            return null;
        }
    }

    public static class BusinessBean{

    }
}
