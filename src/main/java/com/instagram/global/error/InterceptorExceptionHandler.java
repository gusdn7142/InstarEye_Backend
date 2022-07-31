package com.instagram.global.error;


import com.instagram.global.interceptor.LoginCheckInterceptor;
import com.instagram.global.util.Security.JwtService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.instagram")
public class InterceptorExceptionHandler {

    //BasicException 에러 처리
    @ExceptionHandler(BasicException.class)
    public BasicResponse BasicExceptionHandler(BasicException e){
        return new BasicResponse(e.getStatus());
    }


}
