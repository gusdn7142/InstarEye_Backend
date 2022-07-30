package com.instagram.global.util.Security;


import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;




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








}