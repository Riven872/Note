package com.edu.provider;

import com.edu.protocol.http.HttpServer;
import com.edu.protocol.register.LocalRegister;
import com.edu.provider.api.HelloService;

/**
 * 服务提供者接收网络的请求进行跨服务的沟通
 */
public class Provider {
    public static void main(String[] args) {
        // 将需要暴露的服务进行本地注册
        LocalRegister.registry(HelloService.class.getName(), HelloService.class);

        // 服务提供者启动服务器进行请求的处理
        HttpServer httpServer = new HttpServer();
        httpServer.start("localhost", 8080);
    }
}
