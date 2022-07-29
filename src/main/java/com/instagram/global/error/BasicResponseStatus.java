package com.instagram.global.error;

import lombok.Getter;




@Getter
public enum BasicResponseStatus {

    /**
     * 요청 성공
     */
    SUCCESS("SUCCESS", "NOT_ERROR", "요청 성공"),

    /**
     * DB 오류, 서버 오류
     */
    DATABASE_ERROR("FAIL", "DATABASE_ERROR", "DB에서 데이터 조회 실패"),
    SERVER_ERRER("FAIL", "SERVER_ERROR", "서버에서 오류 발생");




    private String status;
    private String code;
    private String message;



    private BasicResponseStatus(String status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }



}