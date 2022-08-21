package com.instagram.global.interceptor;


import com.instagram.global.error.BasicException;
import com.instagram.global.error.BasicResponse;
import com.instagram.global.error.BasicResponseStatus;
import com.instagram.global.util.Security.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

        //PathVariable 변수들 유효성 검사
        CheckPathVariableValid(pathVariables);

        //PathVariable 변수들에서 userIdx or (senderIdx, followerReqIdx, followerIdx) 추출
        Long userIdx = getUserIdxByPathVariable(pathVariables);

        //jwt를 통한 사용자 인가 절차 구현
        Long userIdByAccessToken = jwtService.validCheckAccessToken();  //클라이언트에서 받아온 토큰에서 Id 추출

        if(userIdx != userIdByAccessToken){               //AccessToken 안의 userId와 직접 입력받은 userId가 같지 않다면
            throw new BasicException(ERROR_INVALID_USER_ACCESS_TOKEN);  //권한이 없는 유저의 접근
        }

        return true;   //true이면 다음 인터셉터 혹은 컨트롤러 호출
    }





    //PathVariable 변수들에서 userIdx(or senderIdx, followerReqIdx, followerIdx) 추출
    public Long getUserIdxByPathVariable(Map<String, String> pathVariables) {
        List<String> pathVariableList = new ArrayList<>(Arrays.asList("userIdx",
                "senderIdx",
                "followerReqIdx",
                "followerIdx"
        ));

        String valueByPathVariable = null;
        Long userIdx = null;

        for(int i=0; i<pathVariableList.size(); i++){
            if((valueByPathVariable = pathVariables.get(pathVariableList.get(i))) != null) {
                userIdx = Long.valueOf(valueByPathVariable);
            }
        }
        return userIdx;
    }



    //PathVariable 변수들 유효성 검사 (미입력과 타입 오류)
    public void CheckPathVariableValid(Map<String, String> pathVariables){

        List<String> pathVariableList = new ArrayList<>(Arrays.asList("userIdx",
                "senderIdx",
                "followerReqIdx",
                "followerIdx",
                "receiverIdx",
                "postIdx",
                "commentIdx",
                "followReqIdx",
                "followIdx",
                "followeeIdx"
        ));

        String valueByPathVariable = null;
        BasicResponseStatus status = null;
        for(int i=0; i<pathVariableList.size(); i++){
            if((valueByPathVariable = pathVariables.get(pathVariableList.get(i))) != null) {
                try {
                    Long.valueOf(valueByPathVariable);
                } catch (Exception exception){
                    status.REQ_ERROR_INVALID_IDX.setStatus("FAIL");
                    status.REQ_ERROR_INVALID_IDX.setCode("REQ_ERROR_INVALID_"+pathVariableList.get(i).toUpperCase());
                    status.REQ_ERROR_INVALID_IDX.setMessage(pathVariableList.get(i)+" 형식 오류");
                    throw new BasicException(status.REQ_ERROR_INVALID_IDX);  //receiverIdx 형식 오류"
                }
            }
        }

    }


}

