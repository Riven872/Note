package com.automannn.springPractice.applicationContext;

import org.apache.tiles.web.util.ServletContextAdapter;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author chenkh
 * @time 2021/9/2 13:24
 */
public class GenericWebApplicationContextTest {
    public static void main(String[] args) {
        GenericWebApplicationContext context = new GenericWebApplicationContext();
        /*xxx: web应用，servletConfig 必须首先被初始化 */
//        context.setServletContext(new ServletContextAdapter(new DispatcherServlet()));
        context.refresh();
        System.out.println("web应用上下文初始化完成");
    }
}
