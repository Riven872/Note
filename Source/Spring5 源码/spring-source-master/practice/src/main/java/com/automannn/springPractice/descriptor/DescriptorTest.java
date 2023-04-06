package com.automannn.springPractice.descriptor;

import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.Property;
import org.springframework.core.convert.TypeDescriptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author chenkh
 * @time 2021/9/22 11:00
 */
public class DescriptorTest {
    public static void main(String[] args) {
        /*xxx: typeDescriptor，可以用来描述 属性，也可用于描述 方法，当然也可用于描述 类*/
//        Method[] methods= MyDescriptorBean.class.getDeclaredMethods();
//        MethodParameter methodParameter= new MethodParameter(methods[0],-1);
//        Field[] fields = MyDescriptorBean.class.getDeclaredFields();

//        Property property = new Property(MyDescriptorBean.class,methods[0],null);
        ResolvableType resolvableType = ResolvableType.forClass(MyDescriptorBean.class);
        TypeDescriptor typeDescriptor = new TypeDescriptor(resolvableType,null,null);
        System.out.println(typeDescriptor.getType());
        System.out.println(typeDescriptor.getName());

    }

    static final class MyDescriptorBean{

        private MyDescriptorBean nestedBean;
        private String param1;

        public String sayHi(){
            System.out.println("hello,world!");
            return "";
        }

        public String getParam1() {
            return param1;
        }

        public void setParam1(String param1) {
            this.param1 = param1;
        }

        public MyDescriptorBean getNestedBean() {
            return nestedBean;
        }

        public void setNestedBean(MyDescriptorBean nestedBean) {
            this.nestedBean = nestedBean;
        }
    }
}
