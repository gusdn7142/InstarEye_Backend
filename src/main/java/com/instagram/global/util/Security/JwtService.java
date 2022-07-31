package com.instagram.global.util.Security;


import com.instagram.global.error.BasicException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import static com.instagram.global.error.BasicResponseStatus.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;



@Slf4j
@Service
public class JwtService {

    /*
    로그인시 JWT (AccessToken) 발급
    @param userIdx
    @return String
     */
    public String createAccessToken(Long userIdx) {  //access 토큰 생성
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //헤더 타입 지정
                .setIssuer(Secret.JWT_ISSUER)  //토큰 발급자 지정
                .setIssuedAt(now)    //토큰 발급 시간 (페이로드)
                .setExpiration(new Date(now.getTime() + (1000 * 60 * 60 * 3)))  //마감 기한 (하루 : 3시간 * 60분 * 60초 * 1000밀리세컨드)
                .claim("userIdx", userIdx)      //페이로드 (유저의 idx (primary 키)값)
                .signWith(SignatureAlgorithm.HS256, Secret.ACCESS_TOKEN_KEY)                 //서명 (헤더의 alg인 HS256 알고리즘 사용, 비밀키로 JWT_SECRET_KEY 사용)
                .compact();
    }



    /*
    Access Token의 유효성과 만료여부 확인 (+JWT에서 userIdx 추출)
    @return Long
    @throws BasicException
     */
    public Long validCheckAccessToken() throws BasicException{

        //Header에서 이름("accessToken" )으로 토큰 값 추출
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String accessToken = request.getHeader("accessToken");  //access Token 값 가져오기

        //log.error("accessToken : {}", accessToken);  //accessToken 출력

        //accessToken 값의 null 체크
        if(accessToken == null || accessToken.length() == 0){
            throw new BasicException(ERROR_EMPTY_ACCESS_TOKEN);  //"accessToken 미입력 오류"
        }

        // JWT 파싱
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()                //유효한 토큰인지 확인,  즉 로그인시 부여한 jwt 토큰인지 확인
                    .setSigningKey(Secret.ACCESS_TOKEN_KEY)   //서명키 입력
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BasicException(ERROR_INVALID_ACCESS_TOKEN);  //"accessToken 변조 혹은 만료"
        }

        //  userIdx 추출  (위의 과정에서 문제가 없다면 수행)
        return claims.getBody().get("userIdx", Long.class);

    }











}