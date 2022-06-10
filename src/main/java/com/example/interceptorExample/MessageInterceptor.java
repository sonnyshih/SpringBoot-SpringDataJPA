package com.example.interceptorExample;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 參考
 * 1. https://matthung0807.blogspot.com/2019/08/spring-boot-interceptor.html
 * 2. https://ithelp.ithome.com.tw/articles/10278220
 * */
@Component
public class MessageInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        System.out.println("MessageInterceptor.preHandle(): 開始");
//        response.sendRedirect("https://www.google.com");  // redirect to another page.
//        return false;     // true: go continue, false: interrupt (not go to postHandle)
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {

        System.out.println("MessageInterceptor.postHandle():執行中...");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {

        System.out.println("MessageInterceptor.afterCompletion: 執行結束");
    }
}
