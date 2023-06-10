package com.edu;

import com.edu.protocol.http.HttpClient;
import com.edu.provider.api.HelloService;

public class Consumer {
    public static void main(String[] args) {
        HttpClient httpClient = new HttpClient();

        Invocation invocation = new Invocation();
        invocation.setInterfaceName(HelloService.class.getName());
        invocation.setMethodName("sayHello");
        invocation.setParamType(new Class[]{String.class});
        invocation.setParams(new Object[]{"dubbo"});

        httpClient.send("localhost", 8080, invocation);
    }
}
