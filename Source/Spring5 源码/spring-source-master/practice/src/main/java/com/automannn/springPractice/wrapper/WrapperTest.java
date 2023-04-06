package com.automannn.springPractice.wrapper;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyValue;

/**
 * @author chenkh
 * @time 2021/9/18 15:08
 */
public class WrapperTest {
    public static void main(String[] args) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl();
        beanWrapper.setAutoGrowCollectionLimit(10);
//        beanWrapper.setBeanInstance(new MyBeanTest());
        /*xxx: 可以通过PropertyValue的方式，设置值*/
        PropertyValue propertyValue = new PropertyValue("param1","Aaaa");
        PropertyValue propertyValue1 = new PropertyValue("page","12");
//        PropertyValue propertyValue1 = new PropertyValue("param2","Aaaa");
        beanWrapper.setPropertyValue(propertyValue);
        beanWrapper.setPropertyValue(propertyValue1);

        System.out.println(beanWrapper.getWrappedInstance());

    }

    public static class MyBeanTest{
        private String param1;

        private int page;

        public String getParam1() {
            return param1;
        }

        public void setParam1(String param1) {
            this.param1 = param1;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        @Override
        public String toString() {
            return "MyBeanTest{" +
                    "param1='" + param1 + '\'' +
                    ", page=" + page +
                    '}';
        }
    }
}
