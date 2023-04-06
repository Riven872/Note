package com.automannn.springPractice.applicationContext;

import org.apache.tiles.web.util.ServletContextAdapter;
import org.springframework.web.context.support.StaticWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * @author chenkh
 * @time 2021/9/2 10:53
 */
public class StaticWebApplicationContextTest {
    public static void main(String[] args) {
        StaticWebApplicationContext context   = new StaticWebApplicationContext();
//        context.setConfigLocation("/resources/myweb");//静态上下文不支持动态配置
        context.setNamespace("automan");
        /*xxx: servletConfig需要先进行初始化，同事，它也是servletContext的前置条件  */
//        context.setServletConfig(new DispatcherServlet());
//        context.setServletContext(new ServletContextAdapter(new DispatcherServlet()));
        context.registerBean(MyWebBeanOne.class);
        context.registerBean(MyWebBeanTwo.class);

        context.refresh();
        context.getBean(MyWebBeanTwo.class);
        context.getBean(MyWebBeanOne.class);
    }

    static class MyWebBeanOne{ }
    static class MyWebBeanTwo{ }
}
