package com.edu.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    /**
     * 配置了一个id为foo_id的路由规则
     * 当访问地址http://localhost:9527/872时，会自动转发到地址http://www.baidu.com
     * @param builder
     * @return
     */
    @SuppressWarnings("all")
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routes = builder.routes();
        routes.route("foo_id", r ->
            r.path("/bar").uri("http://www.baidu.com")).build();

        return routes.build();
    }
}
