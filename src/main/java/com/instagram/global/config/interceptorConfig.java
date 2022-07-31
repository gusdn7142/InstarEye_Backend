package com.instagram.global.config;


import com.instagram.global.interceptor.LoginCheckInterceptor;
import com.instagram.global.util.Security.JwtService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class interceptorConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginCheckInterceptor(new JwtService()))
                .order(1)
                .addPathPatterns("/**")  //패턴 설정
                .excludePathPatterns("/users", "/users/login");  //제외 패턴 (화이트 리스트)
    }






}
