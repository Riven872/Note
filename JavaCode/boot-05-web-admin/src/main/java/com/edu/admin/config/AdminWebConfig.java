package com.edu.admin.config;

import com.edu.admin.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 1、编写一个拦截器实现HandlerInterceptor接口
 * 2、拦截器注册到容器中（实现WebMvcConfiguration的addInterceptor）
 * 3、指定拦截规则（如果是拦截所有，静态资源也会被拦截）
 */
@Configuration
public class AdminWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")//所有的请求都被拦截，包括静态资源
                .excludePathPatterns("/", "/login", "/css/**", "/js/**", "/fonts/**", "images/**");
        //放行的资源，不可以直接/static/**，因为在地址栏访问资源时，是localhost:8080/css/xxx等，没有static的前缀
    }
}
