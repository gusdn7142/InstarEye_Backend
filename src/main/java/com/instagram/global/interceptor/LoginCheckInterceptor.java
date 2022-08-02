package com.instagram.global.interceptor;


import com.instagram.global.error.BasicException;
import com.instagram.global.error.BasicResponse;
import com.instagram.global.util.Security.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

import static com.instagram.global.error.BasicResponseStatus.*;


@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private final JwtService jwtService;

    public LoginCheckInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }



    //컨트롤러 호출 전 호출
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);  //final
        log.info("pathVariables : {}", pathVariables);


        Long userIdx = null;
        if(pathVariables.get("userIdx") != null) {
            userIdx = Long.valueOf(pathVariables.get("userIdx"));
        }
        else if(pathVariables.get("senderIdx") != null){   //userIdx가 없으면 sender_idx를 찾아본다..
            userIdx = Long.valueOf(pathVariables.get("senderIdx"));
        }



        //유효성 검사 필요!!!!!!!!
        //log.info("userIdx : {}", userIdx);  //userIdx 출력

        Long userIdByAccessToken = jwtService.validCheckAccessToken();  //클라이언트에서 받아온 토큰에서 Id 추출

        if(userIdx != userIdByAccessToken){               //AccessToken 안의 userId와 직접 입력받은 userId가 같지 않다면
            throw new BasicException(ERROR_INVALID_USER_ACCESS_TOKEN);  //권한이 없는 유저의 접근
        }


        return true;   //true이면 다음 인터셉터 혹은 컨트롤러 호출
    }




}
