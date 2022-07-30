package com.instagram.global.error;

import lombok.Getter;




@Getter
public enum BasicResponseStatus {

    /**
     * 요청 성공
     */
    SUCCESS("SUCCESS", "NOT_ERROR", "요청 성공"),


    /**
     * user 도메인
     */

    REQ_ERROR_INVALID_PHONE("FAIL","REQ_ERROR_INVALID_PHONE","전화번호 형식 오류"),
    REQ_ERROR_INVALID_NAME("FAIL","REQ_ERROR_INVALID_NAME","이름 형식 오류"),
    REQ_ERROR_INVALID_PASSWORD("FAIL","REQ_ERROR_INVALID_PASSWORD","비밀번호 형식 오류"),
    REQ_ERROR_INVALID_BIRTHDAY("FAIL","REQ_ERROR_INVALID_BIRTHDAY","생일 형식 오류"),
    REQ_ERROR_INVALID_PRIVACY_POLICY_STATUS("FAIL","REQ_ERROR_INVALID_PRIVACY_POLICY_STATUS","개인정보 처리방침 미동의"),
    REQ_ERROR_INVALID_NICK_NAME("FAIL","REQ_ERROR_INVALID_NICK_NAME","닉네임 형식 오류"),



    RES_ERROR_EXISTS_PHONE("FAIL","RES_ERROR_EXISTS_PHONE","전화번호 중복 오류"),
    RES_ERROR_EXISTS_NICK_NAME("FAIL","RES_ERROR_EXISTS_NICK_NAME","닉네임 중복 오류"),
    RES_ERROR_JOIN_CHECK("FAIL","RES_ERROR_JOIN_CHECK","가입되지 않은 유저"),
    RES_ERROR_MATCH_FAIL_PASSWORD("FAIL","RES_ERROR_MATCH_FAIL_PASSWORD_","잘못된 비밀번호"),


    ERROR_FAIL_ISSUE_ACCESS_TOKEN("FAIL","ERROR_FAIL_ISSUE_ACCESS_TOKEN","Access Token 발급 실패"),



    DATABASE_ERROR_CREATE_USER("FAIL", "DATABASE_ERROR_CREATE_USER", "DB에 사용자 등록 실패"),
    RES_ERROR_LOGIN_USER("FAIL", "RES_ERROR_LOGIN_USER", "로그인 실패"),







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