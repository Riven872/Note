package com.edu.protocol.http;

import com.edu.Invocation;
import com.edu.protocol.register.LocalRegister;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;

/**
 * 请求处理器
 */
public class HttpServerHandler {
    /**
     * 处理请求
     *
     * @param req
     * @param resp
     */
    public void handler(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Invocation invocation = (Invocation) new ObjectInputStream(req.getInputStream()).readObject();

            // 根据接口名获得其实现类
            Class clazz = LocalRegister.get(invocation.getInterfaceName());
            // 根据消费者实际调用的方法和传参得到确切的方法
            Method method = clazz.getMethod(invocation.getMethodName(), invocation.getParamType());
            // 得到确切的方法后可以调用实际的方法，并得到请求消费者请求服务的方法的结果
            Object res = method.invoke(clazz.newInstance(), invocation.getParams());
            // 将结果写入到响应中
            IOUtils.write((byte[]) res, resp.getOutputStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
