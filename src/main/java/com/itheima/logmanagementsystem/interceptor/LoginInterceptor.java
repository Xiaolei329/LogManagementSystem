package com.itheima.logmanagementsystem.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.logmanagementsystem.common.Result;
import com.itheima.logmanagementsystem.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 从请求头获取 token
        String token = request.getHeader("Authorization");
        
        try {
            if (token != null && !token.trim().isEmpty()) {
                JwtUtils.parseToken(token);
                return true;
            }
        } catch (Exception e) {
            System.err.println("JWT 解析失败或已过期：" + e.getMessage());
        }

        // 解析失败证明没登录或过期，拦截并且返回 401
        response.setStatus(401);
        response.setContentType("application/json;charset=utf-8");
        Result<String> errorResult = Result.error(401, "您还未登录或登录已过期，请重新登录");
        String json = new ObjectMapper().writeValueAsString(errorResult);
        response.getWriter().write(json);
        
        return false;
    }
}
