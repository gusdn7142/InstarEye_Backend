package com.instagram.global.util.login;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instagram.global.error.BasicException;
import com.instagram.global.error.BasicResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.instagram.global.error.BasicResponseStatus.ERROR_EMPTY_ACCESS_TOKEN;
import static com.instagram.global.error.BasicResponseStatus.ERROR_INVALID_ACCESS_TOKEN;
import static com.instagram.global.util.Security.Secret.KAKAO_SERVER_URL;


@Service
public class KakaoService {




    /*
    * accessToken을 통해 카카오 계정 정보를 조회
    * @return KakaoUserProfile
    */
    public KakaoUserProfile findProfile() throws BasicException {  //access 토큰 생성


        /* 토큰으로 카카오 서버에 사용자 자원 요청*/
        RestTemplate rt = new RestTemplate();

        //Header에서 이름("accessToken" )으로 토큰 값 추출
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String accessToken = request.getHeader("kakaoAccessToken");  //access Token 값 가져오기

        if(accessToken == null || accessToken.length() == 0) {    //토큰 유효성 검사
            throw new BasicException(ERROR_EMPTY_ACCESS_TOKEN);  //"accessToken 미입력 오류"
        }

        //Header 객체 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+ accessToken) ;                             //토큰 입력
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8") ;  //전송한 데이터가 key, value 형태인것을 알려줌

        //Header를 엔티티에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);  //header값과 body 데이터를 가진 엔티티가 된다

        //Http 요청
        ResponseEntity<String> response = null;

        try {
            response = rt.exchange(
                    KAKAO_SERVER_URL,      //요청할 주소
                    HttpMethod.POST,             //post 형식
                    kakaoProfileRequest,          //header값
                    String.class
            );
        }
        catch (Exception exception){
            throw new BasicException(ERROR_INVALID_ACCESS_TOKEN);
        }
        //System.out.println(response.getBody());


        //카카오 서버에서 가져온 정보 객체에 저장
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoUserProfile kakaoUserProfile = null;

        try{
            kakaoUserProfile = objectMapper.readValue(response.getBody(), KakaoUserProfile.class);
        } catch (JsonMappingException e) {
            throw new BasicException(ERROR_INVALID_ACCESS_TOKEN);
        } catch (JsonProcessingException e) {
            throw new BasicException(ERROR_INVALID_ACCESS_TOKEN);
        }
        //System.out.println(kakaoUserProfile.getId());

        return kakaoUserProfile;


    }









}
