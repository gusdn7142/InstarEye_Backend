package com.instagram.global.config;

import lombok.Getter;




@Getter
public enum BasicResponseStatus {

    /**
     * 요청 성공
     */
    SUCCESS("SUCCESS", "200", "요청 성공"),

    /**
     * DB 오류
     */
    DATABASE_ERROR("FAIL", "DATABASE_ERROR", "DB에서 데이터 조회 실패");





    private String status;
    private String code;
    private String message;



    private BasicResponseStatus(String status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }



}