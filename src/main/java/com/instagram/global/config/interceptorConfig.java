package com.instagram.global.config;


import com.instagram.global.interceptor.LoginCheckInterceptor;
import com.instagram.global.util.Security.JwtService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Configuration
public class interceptorConfig implements WebMvcConfigurer {

    private List<String> whiteList = new ArrayList<>(Arrays.asList( "/users",
                                                                    "/users/login",
                                                                    "/users/kakao",
                                                                    "/users/kakao-login",
                                                                    "/users/**/privacy-policy-reagree"
    ));


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginCheckInterceptor(new JwtService()))
                .order(1)
                .addPathPatterns("/**")  //패턴 설정
                .excludePathPatterns(whiteList);     //제외 패턴 (화이트 리스트)
                 //.excludePathPatterns("/users", "/users/login", "/users/kakao", "/users/kakao-login");  //제외 패턴 (화이트 리스트)
    }






}
