package com.instagram.global.error;


import com.instagram.global.interceptor.LoginCheckInterceptor;
import com.instagram.global.util.Security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.charset.StandardCharsets;

import static com.instagram.global.error.BasicResponseStatus.*;


@RestControllerAdvice(basePackages = "com.instagram")
public class InterceptorExceptionHandler {

    //BasicException 에러 처리
    @ExceptionHandler(BasicException.class)
    public BasicResponse BasicExceptionHandler(BasicException e){
        return new BasicResponse(e.getStatus());
    }

    //HttpMessageNotReadableException 에러 처리  (JSON 파싱 오류)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BasicResponse BasicExceptionHandler(HttpMessageNotReadableException e){

        if(e.getMessage().contains("privacyPolicyStatus")){   //개인정보 처리방침 입력시 에러 처리
            return new BasicResponse(REQ_ERROR_INVALID_PRIVACY_POLICY_STATUS);
        }
        if(e.getMessage().contains("birthDay")){  //생일 입력시 에러 처리
            return new BasicResponse(REQ_ERROR_INVALID_BIRTHDAY);
        }

        return new BasicResponse(e.getMessage());
    }




}
