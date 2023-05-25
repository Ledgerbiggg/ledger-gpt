package com.ledger.config;

import com.ledger.intercepter.ECInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ledger
 * @version 1.0
 **/

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ECInterceptor())
                .addPathPatterns("/**") // 设置拦截器作用的URL路径
                .excludePathPatterns("/login").excludePathPatterns("/oss/**"); // 设置排除的URL路径，不需要拦截的路径
    }
}

