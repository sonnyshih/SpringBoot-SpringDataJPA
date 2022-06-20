package com.example.interceptorExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * "不要" 在 @Configuration 下面加 @EnableWebMvc，因為Interceptor 預設 static下的js, css... 資料會被攔截
 * */
@Configuration
public class InterceptorWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private MessageInterceptor messageInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(messageInterceptor)    // 註冊攔截器
                .addPathPatterns("/test/message/**")   // 新增攔截路徑 (多個時 .addPathPatterns("/information/**", "/updatePassword/**"))
                .excludePathPatterns("/login/**", "/register/**");   // 新增不攔截路徑
    }

}