package com.automannn.springPractice.resource;

import org.eclipse.jetty.server.handler.ContextHandler;
import org.springframework.core.io.*;
import org.springframework.web.context.support.ServletContextResource;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author chenkh
 * @time 2021/8/18 13:02
 */
public class ResourceTest {
    public static void main(String[] args) throws IOException {
        Resource resource;
        //step1: 测试 context特性的资源句柄
//        resource= new ClassPathResource("com/automannn/springPractice/factory/DefaultListableBeanFactoryTest.class");
//        resource = new UrlResource("file","D:\\dev\\springSource\\readme.md");
//        ServletContext context = new ContextHandler.StaticContext();//以web应用路径为基准
//        resource = new ServletContextResource(context,"/abc");
//        System.out.println(resource.getDescription());
//        System.out.println(resource.getFilename());
//        System.out.println(resource.getFile());
//        System.out.println(resource.getURI());
//        System.out.println(resource.getURL());
        //step2:  测试简单的path资源句柄
//        resource = new PathResource("/abc/mytest.txt");
        resource = new FileUrlResource("/abc/mytest.txt");
//        resource = new FileSystemResource("/abc/mytest.txt");
        System.out.println(resource.getDescription());
        System.out.println(resource.getFilename());
        System.out.println(resource.getFile());
        System.out.println(resource.getURI());
        System.out.println(resource.getURL());
//        ((PathResource)resource).writableChannel().write(ByteBuffer.wrap("abc".getBytes()));

    }
}
