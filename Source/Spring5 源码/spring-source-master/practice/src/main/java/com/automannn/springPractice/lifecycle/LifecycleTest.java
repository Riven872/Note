package com.automannn.springPractice.lifecycle;

import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.Lifecycle;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author chenkh
 * @time 2021/9/29 17:06
 */
public class LifecycleTest {
    public static void main(String[] args) {
        GenericApplicationContext applicationContext =new GenericApplicationContext();
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(MyLifecycleBean.class);
        applicationContext.registerBeanDefinition("myBean1",beanDefinition);

        GenericBeanDefinition beanDefinition1 = new GenericBeanDefinition();
        beanDefinition1.setBeanClass(MySmartLifecycleBean.class);
        applicationContext.registerBeanDefinition("myBean",beanDefinition1);

        /*xxx: 在上下文刷新完成后，会自动触发生命周期组件的启动方法。  在 finishStart方法中*/
        /*xxx: 但是需要注意，只会触发 SmartLifecycle 并且 设置了 autoStart为真(默认值)的生命周期bean*/
        applicationContext.refresh();
        System.out.println(applicationContext.getBean(MyLifecycleBean.class));
//        applicationContext.start();
//        applicationContext.stop();
    }

    public static class MyLifecycleBean implements Lifecycle {
        private boolean isRunning;

        @Override
        public void start() {
            this.isRunning = true;
            System.out.println("stated method run");
        }

        @Override
        public void stop() {
            this.isRunning=false;
            System.out.println("stopped method run");

        }

        @Override
        public boolean isRunning() {
            return this.isRunning;
        }
    }

    public static class MySmartLifecycleBean implements SmartLifecycle{

        private boolean isRunning;

        @Override
        public void start() {
            this.isRunning = true;
            System.out.println("smart stated method run");
        }

        @Override
        public void stop() {
            this.isRunning=false;
            System.out.println("smart stopped method run");

        }

        @Override
        public boolean isRunning() {
            return this.isRunning;
        }
    }
}
