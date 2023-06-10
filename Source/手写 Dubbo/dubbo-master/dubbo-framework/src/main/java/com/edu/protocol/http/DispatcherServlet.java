package com.edu.protocol.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherServlet extends HttpServlet {
    /**
     * 转发到特定的 handler 进行请求的处理
     *
     * @param req  tomcat 封装请求为 HttpServletRequest
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 指定请求处理器
        new HttpServerHandler().handler(req, resp);
        // 以下也可以扩展其他的请求处理器进行请求的指定处理
        // ...
    }
}
