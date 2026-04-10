package com.itheima.logmanagementsystem.config;

import com.itheima.logmanagementsystem.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，拦截所有 api 路径 
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/user/login",
                        "/api/user/register",
                        "/api/user/captcha",
                        "/api/article/list",       // 主页列表
                        "/api/article/detail/*",   // 文章详情
                        "/api/region/**",          // 省市区字典
                        "/api/comment/list/*",     // 评论列表
                        "/api/article/hot"         // 后续要写的热搜榜
                );
    }
}
